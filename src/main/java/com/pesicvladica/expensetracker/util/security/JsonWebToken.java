package com.pesicvladica.expensetracker.util.security;

import com.pesicvladica.expensetracker.model.AppUser;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Base64;
import java.util.Date;

@Component
public class JsonWebToken {

    // region Properties

    private final SecretKey jwtSecretKey;

    private final static long lifeSpan = 1000 * 60 * 15;
    private final static String issuer = "JsonWebTokenService";
    private final static String tokenVersion = "token_version";

    // endregion

    // region Initialization

    public JsonWebToken(@Value("${jwt.secret}") String secret) {
        this.jwtSecretKey = Keys.hmacShaKeyFor(Base64.getDecoder().decode(secret));
    }

    // endregion

    // region Private Methods

    private String generateJWT(AppUser user) {
        return Jwts.builder()
                .setIssuer(JsonWebToken.issuer)
                .setSubject(user.getUsername())
                .claim(JsonWebToken.tokenVersion, user.getTokenVersion())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + JsonWebToken.lifeSpan))
                .signWith(jwtSecretKey)
                .compact();
    }

    private Claims getJWTClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(jwtSecretKey)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    // endregion

    // region Public Methods

    public String generateAccessTokenFor(AppUser user) {
        return generateJWT(user);
    }

    public boolean isTokenValidForUser(String token, AppUser user) {
        try {
            Claims jwtClaims = getJWTClaims(token);

            String username = jwtClaims.getSubject();
            boolean isExpired = jwtClaims.getExpiration().before(new Date());
            Long tokenVersion = jwtClaims.get(JsonWebToken.tokenVersion, Long.class);

            return username.equals(user.getUsername()) && !isExpired && tokenVersion.equals(user.getTokenVersion());
        } catch (Exception e) {
            return false;
        }
    }

    public String getUsernameFor(String token) {
        return getJWTClaims(token).getSubject();
    }

    // endregion
}
