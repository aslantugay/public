package com.meridian.automation.service;

import com.meridian.automation.domain.InviteCode;
import com.meridian.iam.domain.Role;

public interface InviteService {

    InviteCode create(Role grantedRole);

    Role redeem(String code);
}
