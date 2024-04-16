package com.yourlife.your.life.utils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordUtil {

    private static final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    private static final String SALT = "yourLife";

    public static String encodePassword(String password) {
        return encoder.encode(password + SALT);
    }

    public static boolean matches(String rawPassword, String encodedPassword) {
        return encoder.matches(rawPassword + SALT, encodedPassword);
    }
}
