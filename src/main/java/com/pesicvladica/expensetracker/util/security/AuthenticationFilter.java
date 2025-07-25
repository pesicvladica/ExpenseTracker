package com.pesicvladica.expensetracker.util.security;

import com.pesicvladica.expensetracker.exception.CredentialsInvalidException;
import com.pesicvladica.expensetracker.service.authentication.security.AppUserDetails;
import com.pesicvladica.expensetracker.service.authentication.security.AppUserDetailsService;
import com.pesicvladica.expensetracker.util.security.token.JWTTokenService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class AuthenticationFilter extends OncePerRequestFilter {

    // region Properties

    private final JWTTokenService JWTTokenService;
    private final AppUserDetailsService appUserDetailsService;

    // endregion

    // region Initialization

    public AuthenticationFilter(JWTTokenService JWTTokenService,
                                AppUserDetailsService appUserDetailsService) {
        this.JWTTokenService = JWTTokenService;
        this.appUserDetailsService = appUserDetailsService;
    }

    // endregion

    // region Private Methods

    private String extractTokenFrom(HttpServletRequest request) {
        String authenticationHeader = request.getHeader("Authorization");
        String bearer = "Bearer ";
        if (authenticationHeader != null && authenticationHeader.startsWith(bearer)) {
            String token = authenticationHeader.substring(bearer.length());
            if (!token.isBlank()) {
                return token;
            }
        }
        throw new CredentialsInvalidException("Unauthorized access!");
    }

    private AppUserDetails userDetailsFrom(String token) {
        var username = JWTTokenService.getUsernameFor(token);
        var user = appUserDetailsService.loadUserByUsername(username).getAppUser();
        if (JWTTokenService.isTokenValidForUser(token, user)) {
            return new AppUserDetails(user);
        }
        throw new CredentialsInvalidException("Unauthorized access!");
    }

    private void authenticateRequest(HttpServletRequest request) {
        try {
            var accessToken = extractTokenFrom(request);
            var userDetails = userDetailsFrom(accessToken);
            var authToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authToken);
        } catch (Exception e) {
            throw new CredentialsInvalidException("Unauthorized access!");
        }
    }

    // endregion

    // region OncePerRequestFilter

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain) throws ServletException, IOException {

        if (SecurityUtil.shouldAuthorize(request.getRequestURI())) {
            authenticateRequest(request);
        }

        filterChain.doFilter(request, response);
    }

    // endregion
}
