package com.TraceCircle.RolesManagementService.Constant;

public final class SecurityConstants {

    public static final String[] AUTH_WHITELIST = {
            "/api/v1/auth/onboarding",
            "/api/v1/auth/signup",
            "/api/v1/auth/login",
            "/api/v1/auth/refresh"
    };

    public static final String BEARER_PREFIX = "Bearer ";
    public static final String HEADER_AUTHORIZATION = "Authorization";

    public static final String PASSWORD_REGEX =
            "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[^A-Za-z0-9\\s])[\\S]{12,}$";
}
