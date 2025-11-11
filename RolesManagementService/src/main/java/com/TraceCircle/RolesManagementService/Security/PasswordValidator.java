package com.TraceCircle.RolesManagementService.Security;

import java.util.regex.Pattern;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class PasswordValidator {

    private static final Pattern PATTERN = Pattern.compile(
        "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[^A-Za-z0-9\\s]).{12,}$"
    );

    public static void validateOrThrow(String password) {
        if (password == null || password.isBlank())
            throw new IllegalArgumentException("Password cannot be blank");

        if (password.chars().anyMatch(Character::isWhitespace))
            throw new IllegalArgumentException("Password must not contain spaces");

        if (!PATTERN.matcher(password).matches())
            throw new IllegalArgumentException("Password must be ≥12 chars and include upper, lower, digit, special char");
    }

    public static boolean isStrong(String password) {
        try {
            validateOrThrow(password);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
