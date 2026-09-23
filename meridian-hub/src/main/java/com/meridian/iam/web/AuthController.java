package com.meridian.iam.web;

import com.meridian.iam.domain.User;
import com.meridian.iam.dto.LoginRequest;
import com.meridian.iam.service.AuthService;
import com.meridian.platform.security.jwt.JwtService;
import java.util.HashMap;
import java.util.Map;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController implements AuthApi {

    private final AuthService authService;
    private final JwtService jwtService;

    public AuthController(AuthService authService, JwtService jwtService) {
        this.authService = authService;
        this.jwtService = jwtService;
    }

    @Override
    public ResponseEntity<?> login(LoginRequest request) {
        User user = authService.login(request.getEmail(), request.getPassword());
        if (user == null) {
            return ResponseEntity.status(401).body(Map.of("error", "invalid_credentials"));
        }
        Map<String, Object> body = new HashMap<>();
        body.put("userId", user.getId());
        body.put("tenantId", user.getTenantId());
        body.put("role", user.getRole());
        body.put("token", jwtService.issue(user.getId(), user.getTenantId(), user.getRole()));
        return ResponseEntity.ok(body);
    }

    @Override
    public ResponseEntity<?> resetToken(String email) {
        return ResponseEntity.ok(Map.of("token", authService.issueResetToken(email)));
    }
}
