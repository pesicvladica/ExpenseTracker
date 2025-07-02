package com.pesicvladica.expensetracker.service.authentication;

import com.pesicvladica.expensetracker.dto.DeviceInfo;

public interface LoginAttemptService {
    void loginSucceeded(String key);
    void loginFailed(String key, DeviceInfo info);
    boolean isBlocked(String key, DeviceInfo info);
}
