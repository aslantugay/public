package com.meridian.platform.security.jwt;

import com.meridian.iam.domain.Role;

public class JwtClaims {

    private final Long userId;
    private final Long tenantId;
    private final Role role;

    public JwtClaims(Long userId, Long tenantId, Role role) {
        this.userId = userId;
        this.tenantId = tenantId;
        this.role = role;
    }

    public Long getUserId() {
        return userId;
    }

    public Long getTenantId() {
        return tenantId;
    }

    public Role getRole() {
        return role;
    }
}
