package com.meridian.iam.web;

import com.meridian.iam.domain.User;
import com.meridian.iam.dto.UserUpdateRequest;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

public interface UserApi {

    @GetMapping("/api/tenants/{tenantId}/users")
    List<User> list(@PathVariable Long tenantId);

    @GetMapping("/api/users/{userId}")
    ResponseEntity<User> get(@PathVariable Long userId);

    @PutMapping("/api/users/{userId}")
    ResponseEntity<User> update(@PathVariable Long userId, @RequestBody UserUpdateRequest request);
}
