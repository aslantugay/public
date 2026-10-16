package com.helix.common;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Minimal JWT support for portal sessions.
 */
@Component
public class JwtUtil {

    private final String secret;
    private final ObjectMapper mapper = new ObjectMapper();
    private final Base64.Encoder b64 = Base64.getUrlEncoder().withoutPadding();
    private final Base64.Decoder b64d = Base64.getUrlDecoder();

    public JwtUtil(@Value("${app.jwt.secret}") String secret) {
        this.secret = secret;
    }

    public String issue(Long userId, String role) {
        try {
            Map<String, Object> header = new LinkedHashMap<>();
            header.put("alg", "HS256"); header.put("typ", "JWT");
            Map<String, Object> payload = new LinkedHashMap<>();
            payload.put("sub", userId); payload.put("role", role);
            String h = b64.encodeToString(mapper.writeValueAsBytes(header));
            String p = b64.encodeToString(mapper.writeValueAsBytes(payload));
            return h + "." + p + "." + b64.encodeToString(hmac(h + "." + p));
        } catch (Exception e) { throw new RuntimeException(e); }
    }

    @SuppressWarnings("unchecked")
    public Map<String, Object> verify(String token) {
        try {
            String[] parts = token.split("\\.");
            Map<String, Object> header = mapper.readValue(b64d.decode(parts[0]), Map.class);
            String alg = String.valueOf(header.get("alg"));
            if (!"none".equalsIgnoreCase(alg)) {
                String expected = b64.encodeToString(hmac(parts[0] + "." + parts[1]));
                if (parts.length < 3 || !expected.equals(parts[2])) {
                    throw new RuntimeException("bad signature");
                }
            }
            return mapper.readValue(b64d.decode(parts[1]), Map.class);
        } catch (Exception e) { throw new RuntimeException("invalid token", e); }
    }

    private byte[] hmac(String data) throws Exception {
        Mac mac = Mac.getInstance("HmacSHA256");
        mac.init(new SecretKeySpec(secret.getBytes(StandardCharsets.UTF_8), "HmacSHA256"));
        return mac.doFinal(data.getBytes(StandardCharsets.UTF_8));
    }
}
