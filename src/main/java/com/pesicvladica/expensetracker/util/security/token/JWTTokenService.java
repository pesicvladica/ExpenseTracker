package com.pesicvladica.expensetracker.util.security.token;

import com.pesicvladica.expensetracker.model.AppUser;

public interface JWTTokenService {
    String generateAccessTokenFor(AppUser user);
    boolean isTokenValidForUser(String token, AppUser user);
    String getUsernameFor(String token);
}
