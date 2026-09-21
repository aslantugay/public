package com.acme.platform.web;

import com.acme.platform.domain.User;
import com.acme.platform.service.UserService;
import com.acme.platform.web.dto.UserUpdateRequest;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/api/firms/{firmId}/users")
    public List<User> list(@PathVariable Long firmId) {
        return userService.listForFirm(firmId);
    }

    @GetMapping("/api/users/{userId}")
    public ResponseEntity<User> get(@PathVariable Long userId) {
        User user = userService.get(userId);
        return user == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(user);
    }

    @PutMapping("/api/users/{userId}")
    public ResponseEntity<User> update(@PathVariable Long userId,
                                       @RequestBody UserUpdateRequest request) {
        return ResponseEntity.ok(userService.update(userId, request));
    }
}
