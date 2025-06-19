package com.pesicvladica.expensetracker.util.security;

import org.springframework.util.AntPathMatcher;

import java.util.Arrays;

public class SecurityUtil {

    // region Properties

    private final static String[] PUBLIC_PATHS = {
            "/api/auth/register",
            "/api/auth/login"
    };

    // endregion

    // region Public Methods

    public static boolean shouldAuthorize(String path) {
        return Arrays.stream(SecurityUtil.PUBLIC_PATHS).noneMatch(publicPath -> new AntPathMatcher().match(publicPath, path));
    }

    // endregion

}
