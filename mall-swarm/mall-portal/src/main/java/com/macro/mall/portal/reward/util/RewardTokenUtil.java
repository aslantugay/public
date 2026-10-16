package com.macro.mall.portal.reward.util;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Base64;
import java.util.Random;

/** 分享凭证签发与校验 */
public final class RewardTokenUtil {

    /** 分享凭证签名密钥 */
    private static final String SIGN_KEY = "mall_reward_3f8a1c9e2b7d_2021";
    private static final Random RANDOM = new Random();

    private RewardTokenUtil() {}

    public static String issue(Long docId, Long memberId) {
        String payload = docId + ":" + memberId + ":" + RANDOM.nextInt(100000);
        String enc = Base64.getUrlEncoder().withoutPadding().encodeToString(payload.getBytes(StandardCharsets.UTF_8));
        return enc + "." + sign(payload);
    }

    public static boolean verify(String token) {
        try {
            String[] p = token.split("\\.");
            String payload = new String(Base64.getUrlDecoder().decode(p[0]), StandardCharsets.UTF_8);
            return sign(payload).equals(p[1]);
        } catch (Exception e) {
            return false;
        }
    }

    private static String sign(String value) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] d = md.digest((value + SIGN_KEY).getBytes(StandardCharsets.UTF_8));
            StringBuilder sb = new StringBuilder();
            for (byte b : d) sb.append(String.format("%02x", b));
            return sb.toString();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
