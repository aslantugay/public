package com.meridian.platform.security;

import com.meridian.iam.domain.Role;
import org.springframework.stereotype.Component;

/**
 * Thread-bound view of the caller for the duration of a request. Populated by
 * the edge filters and read by the service layer to make tenant decisions.
 */
@Component
public class RequestContext {

    private static final ThreadLocal<Holder> HOLDER = ThreadLocal.withInitial(Holder::new);

    private static final class Holder {
        private Long userId;
        private Long tenantId;
        private Role role = Role.MEMBER;
    }

    public Long getUserId() {
        return HOLDER.get().userId;
    }

    public void setUserId(Long userId) {
        HOLDER.get().userId = userId;
    }

    public Long getTenantId() {
        return HOLDER.get().tenantId;
    }

    public void setTenantId(Long tenantId) {
        HOLDER.get().tenantId = tenantId;
    }

    public Role getRole() {
        return HOLDER.get().role;
    }

    public void setRole(Role role) {
        HOLDER.get().role = role;
    }

    public boolean atLeast(Role required) {
        Role current = HOLDER.get().role;
        return current != null && current.ordinal() >= required.ordinal();
    }

    public void applyFrom(SessionContext ctx) {
        Holder holder = HOLDER.get();
        if (ctx.getUserId() != null) {
            holder.userId = ctx.getUserId();
        }
        if (ctx.getTenantId() != null) {
            holder.tenantId = ctx.getTenantId();
        }
        if (ctx.getRole() != null) {
            holder.role = ctx.getRole();
        }
    }

    public void clear() {
        HOLDER.remove();
    }
}
