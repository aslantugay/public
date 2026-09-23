package com.meridian.iam.web;

import com.meridian.iam.dto.LoginRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequestMapping("/api/auth")
public interface AuthApi {

    @PostMapping("/login")
    ResponseEntity<?> login(@RequestBody LoginRequest request);

    @PostMapping("/reset-token")
    ResponseEntity<?> resetToken(@RequestParam String email);
}
