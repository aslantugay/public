package com.acme.platform.service.support;

import com.acme.platform.domain.Role;
import com.acme.platform.security.CurrentUserService;

/**
 * Common tenant-aware helpers shared by the domain services. Concrete services
 * inherit access to the caller's firm scope and role gates.
 */
public abstract class AbstractTenantService {

    protected final CurrentUserService currentUserService;

    protected AbstractTenantService(CurrentUserService currentUserService) {
        this.currentUserService = currentUserService;
    }

    protected Long currentFirmId() {
        return currentUserService.currentFirmId();
    }

    protected void requireRole(Role required) {
        if (!currentUserService.context().hasRole(required)) {
            throw new SecurityException("Insufficient role, requires " + required);
        }
    }
}
