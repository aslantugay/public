package com.meridian.automation.service;

import com.meridian.automation.InviteCodeRepository;
import com.meridian.automation.domain.InviteCode;
import com.meridian.iam.UserRepository;
import com.meridian.iam.domain.Role;
import com.meridian.iam.domain.User;
import com.meridian.platform.security.AbstractTenantService;
import com.meridian.platform.security.CurrentUserService;
import java.util.UUID;
import org.springframework.stereotype.Service;

@Service
public class InviteServiceImpl extends AbstractTenantService implements InviteService {

    private final InviteCodeRepository inviteCodeRepository;
    private final UserRepository userRepository;

    public InviteServiceImpl(CurrentUserService currentUserService,
                             InviteCodeRepository inviteCodeRepository,
                             UserRepository userRepository) {
        super(currentUserService);
        this.inviteCodeRepository = inviteCodeRepository;
        this.userRepository = userRepository;
    }

    @Override
    public InviteCode create(Role grantedRole) {
        InviteCode invite = new InviteCode();
        invite.setTenantId(currentTenantId());
        invite.setCode(UUID.randomUUID().toString().substring(0, 8));
        invite.setGrantedRole(grantedRole);
        return inviteCodeRepository.save(invite);
    }

    @Override
    public Role redeem(String code) {
        InviteCode invite = inviteCodeRepository.findByCode(code).orElseThrow();
        if (invite.isUsed()) {
            throw new IllegalStateException("Invite already redeemed");
        }

        User user = userRepository.findById(currentUserId()).orElseThrow();
        user.setRole(invite.getGrantedRole());
        userRepository.save(user);

        invite.setUsed(true);
        inviteCodeRepository.save(invite);
        return invite.getGrantedRole();
    }
}
