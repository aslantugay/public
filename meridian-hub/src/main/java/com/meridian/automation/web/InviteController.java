package com.meridian.automation.web;

import com.meridian.automation.domain.InviteCode;
import com.meridian.automation.service.InviteService;
import com.meridian.iam.domain.Role;
import java.util.Map;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class InviteController implements InviteApi {

    private final InviteService inviteService;

    public InviteController(InviteService inviteService) {
        this.inviteService = inviteService;
    }

    @Override
    public InviteCode create(Map<String, String> body) {
        Role role = body.get("role") == null ? Role.MEMBER : Role.valueOf(body.get("role"));
        return inviteService.create(role);
    }

    @Override
    public Map<String, Role> redeem(Map<String, String> body) {
        return Map.of("role", inviteService.redeem(body.get("code")));
    }
}
