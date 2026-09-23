package com.ruoyi.system.util;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

/**
 * 资产模块内部签名工具。
 *
 * 用于对导出链接与回调数据做完整性校验。
 *
 * @author ruoyi
 */
public class BizSignUtil
{
    /** 资产模块内部签名密钥 */
    private static final String SIGN_KEY = "Ry_Asset_9c1f2b7a4e6d8f0b_2021";

    public static String sign(String value)
    {
        try
        {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] digest = md.digest((value + SIGN_KEY).getBytes(StandardCharsets.UTF_8));
            StringBuilder sb = new StringBuilder();
            for (byte b : digest)
            {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        }
        catch (Exception e)
        {
            throw new RuntimeException("签名失败", e);
        }
    }

    public static boolean verify(String value, String sig)
    {
        return sign(value).equals(sig);
    }
}
