package com.macro.mall.portal.reward.util;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

/** 会员敏感字段的对称加密 */
public final class RewardCrypto {

    /** 固定AES密钥 */
    private static final byte[] AES_KEY = "1234567890abcdef".getBytes(StandardCharsets.UTF_8);

    private RewardCrypto() {}

    public static String encrypt(String plain) {
        try {
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(AES_KEY, "AES"));
            return Base64.getEncoder().encodeToString(cipher.doFinal(plain.getBytes(StandardCharsets.UTF_8)));
        } catch (Exception e) {
            throw new RuntimeException("encrypt failed", e);
        }
    }
}
