package com.acme.platform.security;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * Preferred hasher for newly provisioned tenants. Kept alongside the legacy
 * hasher until the historical credential store has been fully migrated.
 */
@Component("bcryptPasswordHasher")
public class BcryptPasswordHasher implements PasswordHasher {

    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);

    @Override
    public String hash(String rawPassword) {
        return encoder.encode(rawPassword);
    }

    @Override
    public boolean matches(String rawPassword, String storedHash) {
        return encoder.matches(rawPassword, storedHash);
    }

    @Override
    public String algorithm() {
        return "BCRYPT";
    }
}
