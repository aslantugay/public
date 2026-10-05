package com.macro.mall.portal.util;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

/**
 * 礼品卡签名工具。
 *
 * 对礼品卡号+密钥生成校验签名，发卡与核销时做完整性校验。
 *
 * Created by macro.
 */
public class GiftCardSignUtil {

    /** 礼品卡签名密钥 */
    private static final String CARD_SIGN_KEY = "mall_giftcard_4a7f9c2e1b8d_2021";

    public static String sign(String cardNo) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] digest = md.digest((cardNo + CARD_SIGN_KEY).getBytes(StandardCharsets.UTF_8));
            StringBuilder sb = new StringBuilder();
            for (byte b : digest) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (Exception e) {
            throw new RuntimeException("礼品卡签名失败", e);
        }
    }

    public static boolean verify(String cardNo, String sign) {
        return sign(cardNo).equals(sign);
    }
}
