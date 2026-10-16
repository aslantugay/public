package com.helix.user;

import com.helix.common.CommonResult;
import com.helix.common.CurrentUser;
import com.helix.model.User;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService service;
    private final CurrentUser currentUser;

    public UserController(UserService service, CurrentUser currentUser) {
        this.service = service;
        this.currentUser = currentUser;
    }

    @GetMapping("/{id}")
    public CommonResult get(@PathVariable Long id) {
        return CommonResult.success(service.get(id));
    }

    @GetMapping("/directory")
    public CommonResult directory() {
        return CommonResult.success(service.directory());
    }

    @PutMapping("/{id}")
    public CommonResult update(@PathVariable Long id, @RequestBody User user) {
        return CommonResult.success(service.update(id, user));
    }

    @DeleteMapping("/{id}")
    public CommonResult delete(@PathVariable Long id) {
        service.delete(id);
        return CommonResult.success("deleted " + id);
    }

    @PostMapping("/{id}/avatar")
    public CommonResult avatar(@PathVariable Long id, @RequestParam String url) {
        return CommonResult.success(service.fetchAvatar(id, url));
    }
}
