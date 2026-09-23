package com.meridian.platform.security;

import com.meridian.iam.domain.Role;

/**
 * Shared tenant-aware helpers. Concrete services inherit the caller's tenant
 * scope and the role gate used before privileged operations.
 */
public abstract class AbstractTenantService {

    protected final CurrentUserService currentUserService;

    protected AbstractTenantService(CurrentUserService currentUserService) {
        this.currentUserService = currentUserService;
    }

    protected Long currentTenantId() {
        return currentUserService.tenantId();
    }

    protected Long currentUserId() {
        return currentUserService.userId();
    }

    protected void requireRole(Role required) {
        if (!currentUserService.context().atLeast(required)) {
            throw new SecurityException("Insufficient role; requires " + required);
        }
    }
}
