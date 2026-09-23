package com.meridian.platform.security;

public interface PasswordHasher {

    String hash(String rawPassword);

    boolean matches(String rawPassword, String storedHash);

    String algorithm();
}
