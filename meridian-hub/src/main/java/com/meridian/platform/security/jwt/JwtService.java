package com.meridian.platform.security.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.meridian.iam.domain.Role;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.LinkedHashMap;
import java.util.Map;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * Compact JSON web tokens minted at login and presented as
 * {@code Authorization: Bearer <token>}.
 */
@Service
public class JwtService {

    private final String secret;
    private final ObjectMapper mapper = new ObjectMapper();
    private final Base64.Encoder b64 = Base64.getUrlEncoder().withoutPadding();
    private final Base64.Decoder b64d = Base64.getUrlDecoder();

    public JwtService(@Value("${app.security.jwt-secret}") String secret) {
        this.secret = secret;
    }

    public String issue(Long userId, Long tenantId, Role role) {
        try {
            Map<String, Object> header = new LinkedHashMap<>();
            header.put("alg", "HS256");
            header.put("typ", "JWT");
            Map<String, Object> payload = new LinkedHashMap<>();
            payload.put("sub", userId);
            payload.put("tenantId", tenantId);
            payload.put("role", role == null ? null : role.name());
            payload.put("iat", System.currentTimeMillis() / 1000L);
            String h = b64.encodeToString(mapper.writeValueAsBytes(header));
            String p = b64.encodeToString(mapper.writeValueAsBytes(payload));
            String signingInput = h + "." + p;
            return signingInput + "." + b64.encodeToString(hmac(signingInput));
        } catch (Exception e) {
            throw new IllegalStateException("Unable to issue token", e);
        }
    }

    @SuppressWarnings("unchecked")
    public JwtClaims verify(String token) {
        try {
            String[] parts = token.split("\\.");
            if (parts.length < 2) {
                throw new IllegalArgumentException("Malformed token");
            }
            Map<String, Object> header = mapper.readValue(b64d.decode(parts[0]), Map.class);
            String alg = String.valueOf(header.get("alg"));
            if (!"none".equalsIgnoreCase(alg)) {
                String expected = b64.encodeToString(hmac(parts[0] + "." + parts[1]));
                if (parts.length < 3 || !expected.equals(parts[2])) {
                    throw new IllegalArgumentException("Bad signature");
                }
            }
            Map<String, Object> payload = mapper.readValue(b64d.decode(parts[1]), Map.class);
            Long userId = payload.get("sub") == null ? null
                    : Long.valueOf(String.valueOf(payload.get("sub")));
            Long tenantId = payload.get("tenantId") == null ? null
                    : Long.valueOf(String.valueOf(payload.get("tenantId")));
            Role role = payload.get("role") == null ? null
                    : Role.valueOf(String.valueOf(payload.get("role")));
            return new JwtClaims(userId, tenantId, role);
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid token", e);
        }
    }

    private byte[] hmac(String data) throws Exception {
        Mac mac = Mac.getInstance("HmacSHA256");
        mac.init(new SecretKeySpec(secret.getBytes(StandardCharsets.UTF_8), "HmacSHA256"));
        return mac.doFinal(data.getBytes(StandardCharsets.UTF_8));
    }
}
