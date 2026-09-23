package com.meridian.iam.web;

import com.meridian.iam.domain.User;
import com.meridian.iam.dto.UserUpdateRequest;
import com.meridian.iam.service.UserService;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController implements UserApi {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Override
    public List<User> list(Long tenantId) {
        return userService.listForTenant(tenantId);
    }

    @Override
    public ResponseEntity<User> get(Long userId) {
        User user = userService.get(userId);
        return user == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(user);
    }

    @Override
    public ResponseEntity<User> update(Long userId, UserUpdateRequest request) {
        return ResponseEntity.ok(userService.update(userId, request));
    }
}
