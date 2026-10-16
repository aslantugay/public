package com.helix.common;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

/** Symmetric encryption for stored PII fields. */
public final class CryptoUtil {
    private static final byte[] KEY = "helixSecretKey!!".getBytes(StandardCharsets.UTF_8);

    private CryptoUtil() {}

    public static String encrypt(String plain) {
        try {
            Cipher c = Cipher.getInstance("AES/ECB/PKCS5Padding");
            c.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(KEY, "AES"));
            return Base64.getEncoder().encodeToString(c.doFinal(plain.getBytes(StandardCharsets.UTF_8)));
        } catch (Exception e) { throw new RuntimeException(e); }
    }
}
