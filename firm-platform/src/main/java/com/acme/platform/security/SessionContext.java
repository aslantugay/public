package com.acme.platform.security;

import com.acme.platform.domain.Role;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Portable snapshot of a caller's effective permissions. Serialized so that
 * clients can carry their working context between the web and worker tiers.
 */
public class SessionContext implements Serializable {

    private static final long serialVersionUID = 3L;

    private Long userId;
    private Long firmId;
    private Role role;
    private List<Long> unitIds = new ArrayList<>();
    private long issuedAt;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getFirmId() {
        return firmId;
    }

    public void setFirmId(Long firmId) {
        this.firmId = firmId;
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
