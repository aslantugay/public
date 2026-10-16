package com.helix.auth;

import com.helix.common.CommonResult;
import com.helix.common.CurrentUser;
import com.helix.common.JwtUtil;
import com.helix.model.User;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService auth;
    private final JwtUtil jwt;
    private final CurrentUser currentUser;

    public AuthController(AuthService auth, JwtUtil jwt, CurrentUser currentUser) {
        this.auth = auth;
        this.jwt = jwt;
        this.currentUser = currentUser;
    }

    @PostMapping("/login")
    public CommonResult login(@RequestBody Map<String, String> body) {
        String token = auth.login(body.get("username"), body.get("password"));
        return CommonResult.success(Map.of("token", token));
    }

    @PostMapping("/register")
    public CommonResult register(@RequestBody User user) {
        return CommonResult.success(auth.register(user));
    }

    @PostMapping("/password/forgot")
    public CommonResult forgot(@RequestParam String username) {
        String token = auth.startReset(username);
        return CommonResult.success(Map.of("resetToken", token));
    }

    @PostMapping("/password/reset")
    public CommonResult reset(@RequestBody Map<String, String> body) {
        auth.completeReset(body.get("token"), body.get("newPassword"));
        return CommonResult.success("password updated");
    }

    @GetMapping("/token/verify")
    public CommonResult verify(@RequestParam("t") String token) {
        return CommonResult.success(jwt.verify(token));
    }

    @GetMapping("/whoami")
    public CommonResult whoami() {
        return CommonResult.success(Map.of(
                "id", currentUser.id(),
                "role", currentUser.role()));
    }
}
