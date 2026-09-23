package com.meridian.automation.web;

import com.meridian.automation.domain.InviteCode;
import com.meridian.iam.domain.Role;
import java.util.Map;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/api/invites")
public interface InviteApi {

    @PostMapping
    InviteCode create(@RequestBody Map<String, String> body);

    @PostMapping("/redeem")
    Map<String, Role> redeem(@RequestBody Map<String, String> body);
}
