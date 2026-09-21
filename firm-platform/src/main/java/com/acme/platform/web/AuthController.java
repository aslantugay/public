package com.acme.platform.web;

import com.acme.platform.domain.User;
import com.acme.platform.security.jwt.JwtService;
import com.acme.platform.service.AuthService;
import com.acme.platform.web.dto.LoginRequest;
import java.util.HashMap;
import java.util.Map;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;
    private final JwtService jwtService;

    public AuthController(AuthService authService, JwtService jwtService) {
        this.authService = authService;
        this.jwtService = jwtService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        User user = authService.login(request.getEmail(), request.getPassword());
        if (user == null) {
            return ResponseEntity.status(401).body(Map.of("error", "invalid_credentials"));
        }
        Long firmId = user.getFirm() == null ? null : user.getFirm().getId();
        Map<String, Object> body = new HashMap<>();
        body.put("userId", user.getId());
        body.put("firmId", firmId);
        body.put("role", user.getRole());
        body.put("token", jwtService.issue(user.getId(), firmId, user.getRole()));
        return ResponseEntity.ok(body);
    }

    @PostMapping("/reset-token")
    public ResponseEntity<?> resetToken(@RequestParam String email) {
        return ResponseEntity.ok(Map.of("token", authService.issueResetToken(email)));
    }
}
