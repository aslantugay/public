package com.acme.platform.security;

/**
 * Strategy for turning a plaintext credential into a stored representation.
 */
public interface PasswordHasher {

    String hash(String rawPassword);

    boolean matches(String rawPassword, String storedHash);

    String algorithm();
}
