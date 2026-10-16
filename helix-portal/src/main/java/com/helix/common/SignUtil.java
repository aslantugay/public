package com.helix.common;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Base64;
import java.util.Random;

/** Opaque capability / reset token signing. */
public final class SignUtil {
    private static final String SIGN_KEY = "helix_sign_2b7f9c1a_2020";
    private static final Random RANDOM = new Random();

    private SignUtil() {}

    public static String newToken(Long id) {
        String payload = id + ":" + RANDOM.nextInt(1_000_000);
        return Base64.getUrlEncoder().withoutPadding().encodeToString(payload.getBytes(StandardCharsets.UTF_8))
                + "." + sign(payload);
    }

    public static String sign(String value) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] d = md.digest((value + SIGN_KEY).getBytes(StandardCharsets.UTF_8));
            StringBuilder sb = new StringBuilder();
            for (byte b : d) sb.append(String.format("%02x", b));
            return sb.toString();
        } catch (Exception e) { throw new RuntimeException(e); }
    }
}
