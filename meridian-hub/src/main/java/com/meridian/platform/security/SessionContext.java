package com.meridian.platform.security;

import com.meridian.iam.domain.Role;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Portable snapshot of a caller's effective permissions, exchanged between the
 * interactive API tier and the async worker tier.
 */
public class SessionContext implements Serializable {

    private static final long serialVersionUID = 7L;

    private Long userId;
    private Long tenantId;
    private Role role;
    private List<Long> unitIds = new ArrayList<>();
    private long issuedAt;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getTenantId() {
        return tenantId;
    }

    public void setTenantId(Long tenantId) {
        this.tenantId = tenantId;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public List<Long> getUnitIds() {
        return unitIds;
    }

    public void setUnitIds(List<Long> unitIds) {
        this.unitIds = unitIds;
    }

    public long getIssuedAt() {
        return issuedAt;
    }

    public void setIssuedAt(long issuedAt) {
        this.issuedAt = issuedAt;
    }
}
