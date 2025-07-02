package com.pesicvladica.expensetracker.service.authentication;

import com.pesicvladica.expensetracker.dto.DeviceInfo;
import jakarta.annotation.PreDestroy;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import redis.clients.jedis.JedisPooled;
import redis.clients.jedis.params.SetParams;

import java.util.concurrent.TimeUnit;

@Service
public class LoginAttemptServiceImpl implements LoginAttemptService {

    // region Properties

    private static final String KEY_PREFIX_USER_ATTEMPTS = "login:attempts:";
    private static final String KEY_PREFIX_USER_BLOCKED = "login:blocked:";
    private static final String KEY_PREFIX_IP_TOTAL_FAILS = "ip:total_fails:";
    private static final String KEY_PREFIX_IP_RATE = "ip:rate:";
    private static final String KEY_PREFIX_IP_BLACKLIST = "ip:blacklist:";
    private static final String KEY_PREFIX_IP_DISTINCT_USERS = "ip:distinct_users:";

    private static final int MAX_ATTEMPTS_PER_USER = 5;
    private static final long USER_ATTEMPT_WINDOW = TimeUnit.MINUTES.toSeconds(5);
    private static final long USER_BLOCK_DURATION = TimeUnit.MINUTES.toSeconds(10);

    private static final int MAX_IP_TOTAL_FAILS = 20;
    private static final long IP_TOTAL_FAIL_WINDOW = TimeUnit.MINUTES.toSeconds(1);

    private static final int MAX_IP_ATTEMPTS_RATE = 10;
    private static final long IP_RATE_WINDOW = TimeUnit.SECONDS.toSeconds(10);

    private static final int MIN_DISTINCT_USERS_FOR_IP_BLACKLIST = 3;
    private static final long IP_DISTINCT_USER_WINDOW = TimeUnit.HOURS.toSeconds(24);
    private static final long IP_BLACKLIST_DURATION = TimeUnit.HOURS.toSeconds(24);

    private final JedisPooled jedis;

    // endregion

    // region Initialization

    public LoginAttemptServiceImpl(@Value("${redis.host}") String redisHost, @Value("${redis.port}") int redisPort) {
        this.jedis = new JedisPooled(redisHost, redisPort);
    }

    // endregion

    // region Private Methods

    private String getUserAttemptsKey(String key) { return KEY_PREFIX_USER_ATTEMPTS + key; }
    private String getUserBlockedKey(String key) { return KEY_PREFIX_USER_BLOCKED + key; }

    private String getIpTotalFailsKey(String ipAddress) { return KEY_PREFIX_IP_TOTAL_FAILS + ipAddress; }
    private String getIpRateKey(String ipAddress) { return KEY_PREFIX_IP_RATE + ipAddress; }
    private String getIpBlacklistKey(String ipAddress) { return KEY_PREFIX_IP_BLACKLIST + ipAddress; }
    private String getIpDistinctUsersKey(String ipAddress) { return KEY_PREFIX_IP_DISTINCT_USERS + ipAddress; }

    // endregion

    // region LoginAttemptService

    @Override
    public void loginSucceeded(String key) {
        jedis.del(getUserAttemptsKey(key));
        jedis.del(getUserBlockedKey(key));
    }

    @Override
    public void loginFailed(String key, DeviceInfo info) {
        final long currentTimeMillis = System.currentTimeMillis();
        final String ipAddress = info.ipAddress();

        long userAttempts = jedis.incr(getUserAttemptsKey(key));
        if (userAttempts == 1) {
            jedis.expire(getUserAttemptsKey(key), USER_ATTEMPT_WINDOW);
        }
        if (userAttempts >= MAX_ATTEMPTS_PER_USER) {
            var value = String.valueOf(currentTimeMillis + TimeUnit.SECONDS.toMillis(USER_BLOCK_DURATION));
            SetParams params = new SetParams().ex(USER_BLOCK_DURATION);
            jedis.set(getUserBlockedKey(key), value, params);
        }

        long ipTotalFails = jedis.incr(getIpTotalFailsKey(ipAddress));
        if (ipTotalFails == 1) {
            jedis.expire(getIpTotalFailsKey(ipAddress), IP_TOTAL_FAIL_WINDOW);
        }
        String member = currentTimeMillis + ":" + System.nanoTime();
        jedis.zadd(getIpRateKey(ipAddress), currentTimeMillis, member);
        jedis.zremrangeByScore(getIpRateKey(ipAddress), 0, currentTimeMillis - TimeUnit.SECONDS.toMillis(IP_RATE_WINDOW));
        jedis.expire(getIpRateKey(ipAddress), IP_RATE_WINDOW + 60);

        jedis.sadd(getIpDistinctUsersKey(ipAddress), key);
        if (jedis.ttl(getIpDistinctUsersKey(ipAddress)) == -1) {
            jedis.expire(getIpDistinctUsersKey(ipAddress), IP_DISTINCT_USER_WINDOW);
        }

        long distinctUserCount = jedis.scard(getIpDistinctUsersKey(ipAddress));
        if (distinctUserCount >= MIN_DISTINCT_USERS_FOR_IP_BLACKLIST) {
            var value = String.valueOf(currentTimeMillis + TimeUnit.SECONDS.toMillis(IP_BLACKLIST_DURATION));
            var setParams = new SetParams().ex(IP_BLACKLIST_DURATION);
            jedis.set(getIpBlacklistKey(ipAddress), value, setParams);
        }
    }

    @Override
    public boolean isBlocked(String key, DeviceInfo info) {
        final long currentTimeMillis = System.currentTimeMillis();
        final String ipAddress = info.ipAddress();

        String ipBlacklistedUntilStr = jedis.get(getIpBlacklistKey(ipAddress));
        if (ipBlacklistedUntilStr != null) {
            long ipBlacklistedUntil = Long.parseLong(ipBlacklistedUntilStr);
            if (currentTimeMillis <= ipBlacklistedUntil) {
                return true;
            }
        }

        String ipTotalFailsStr = jedis.get(getIpTotalFailsKey(ipAddress));
        if (ipTotalFailsStr != null) {
            long ipTotalFails = Long.parseLong(ipTotalFailsStr);
            if (ipTotalFails >= MAX_IP_TOTAL_FAILS) {
                return true;
            }
        }

        long attemptsInRateWindow = jedis.zcount(getIpRateKey(ipAddress), currentTimeMillis - TimeUnit.SECONDS.toMillis(IP_RATE_WINDOW), currentTimeMillis);
        if (attemptsInRateWindow >= MAX_IP_ATTEMPTS_RATE) {
            return true;
        }

        String userBlockedUntilStr = jedis.get(getUserBlockedKey(key));
        if (userBlockedUntilStr != null) {
            long userBlockedUntil = Long.parseLong(userBlockedUntilStr);
            return currentTimeMillis <= userBlockedUntil;
        }

        return false;
    }

    // endregion

    // region Cleanup

    @PreDestroy
    public void shutdown() {
        jedis.close();
        System.out.println("Jedis connection pool closed.");
    }

    // endregion
}
