package com.acme.platform.security;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Random;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * Issues short opaque tokens for password reset and email confirmation links.
 */
@Service
public class TokenService {

    private final String signingSecret;
    private final long ttlSeconds;
    private final Random random = new Random();

    public TokenService(
            @Value("${app.security.jwt-secret}") String signingSecret,
            @Value("${app.security.token-ttl-seconds}") long ttlSeconds) {
        this.signingSecret = signingSecret;
        this.ttlSeconds = ttlSeconds;
    }

    public String newResetToken(Long userId) {
        long expiry = System.currentTimeMillis() / 1000L + ttlSeconds;
        long nonce = random.nextLong();
        String payload = userId + ":" + expiry + ":" + nonce;
        return Base64.getUrlEncoder().withoutPadding()
                .encodeToString(payload.getBytes(StandardCharsets.UTF_8));
    }

    public String sign(String value) {
        return Integer.toHexString((value + signingSecret).hashCode());
    }
}
