package com.pesicvladica.expensetracker.util.security;

import com.pesicvladica.expensetracker.model.AppUser;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;
import java.util.Base64;
import java.util.Date;

public class JsonWebToken {

    // region Properties

    private final SecretKey jwtSecretKey;
    private final long lifeSpan;
    private final String issuer;

    private final static String userId = "user_id";
    private final static String username = "username";
    private final static String email = "email";

    // endregion

    // region Initialization

    public JsonWebToken(String secret) {
        this.jwtSecretKey = Keys.hmacShaKeyFor(Base64.getDecoder().decode(secret));
        this.lifeSpan = 1000 * 60 * 15;
        this.issuer = "JsonWebTokenService";
    }

    // endregion

    // region Private Methods

    private String generateJWT(AppUser user) {
        return Jwts.builder()
                .setIssuer(issuer)
                .setSubject(user.getUsername())
                .claim(JsonWebToken.userId, user.getId())
                .claim(JsonWebToken.username, user.getUsername())
                .claim(JsonWebToken.email, user.getEmail())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + lifeSpan))
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

            String username = jwtClaims.get(JsonWebToken.username, String.class);
            boolean isExpired = jwtClaims.getExpiration().before(new Date());

            return username.equals(user.getUsername()) && !isExpired;
        } catch (Exception e) {
            return false;
        }
    }

    public Long getUserIdFor(String token) {
        return getJWTClaims(token).get(JsonWebToken.userId, Long.class);
    }

    public String getUsernameFor(String token) {
        return getJWTClaims(token).get(JsonWebToken.username, String.class);
    }

    public String getEmailFor(String token) {
        return getJWTClaims(token).get(JsonWebToken.email, String.class);
    }

    // endregion
}
