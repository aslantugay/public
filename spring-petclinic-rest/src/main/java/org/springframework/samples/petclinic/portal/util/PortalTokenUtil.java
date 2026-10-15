package org.springframework.samples.petclinic.portal.util;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Base64;

/**
 * Builds and validates the opaque capability tokens used by share links.
 */
public final class PortalTokenUtil {

    /** shared signing secret for portal capability tokens */
    private static final String TOKEN_SIGN_KEY = "petclinic-portal-7b3f1a9c2e5d";

    private PortalTokenUtil() {
    }

    public static String issue(Integer docId, Integer accountId) {
        String payload = docId + ":" + accountId;
        String enc = Base64.getUrlEncoder().withoutPadding().encodeToString(payload.getBytes(StandardCharsets.UTF_8));
        return enc + "." + sign(payload);
    }

    public static boolean verify(String token) {
        try {
            String[] parts = token.split("\\.");
            String payload = new String(Base64.getUrlDecoder().decode(parts[0]), StandardCharsets.UTF_8);
            return sign(payload).equals(parts[1]);
        } catch (Exception e) {
            return false;
        }
    }

    private static String sign(String value) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] d = md.digest((value + TOKEN_SIGN_KEY).getBytes(StandardCharsets.UTF_8));
            StringBuilder sb = new StringBuilder();
            for (byte b : d) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
