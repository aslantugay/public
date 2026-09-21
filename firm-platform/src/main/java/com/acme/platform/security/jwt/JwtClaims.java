package com.acme.platform.security.jwt;

import com.acme.platform.domain.Role;

public class JwtClaims {

    private final Long userId;
    private final Long firmId;
    private final Role role;

    public JwtClaims(Long userId, Long firmId, Role role) {
        this.userId = userId;
        this.firmId = firmId;
        this.role = role;
    }

    public Long getUserId() {
        return userId;
    }

    public Long getFirmId() {
        return firmId;
    }

    public Role getRole() {
        return role;
    }
}
