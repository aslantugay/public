package com.acme.platform.security;

import com.acme.platform.domain.Role;
import org.springframework.stereotype.Component;

/**
 * Per-request view of the caller as resolved by the edge gateway. Backed by a
 * thread-local so it is available to servlet filters and the service layer
 * alike. Populated once per request and cleared when the request completes.
 */
@Component
public class RequestContext {

    private static final ThreadLocal<Holder> HOLDER = ThreadLocal.withInitial(Holder::new);

    private static final class Holder {
        private Long userId;
        private Long firmId;
        private Role role = Role.MEMBER;
    }

    public Long getUserId() {
        return HOLDER.get().userId;
    }

    public void setUserId(Long userId) {
        HOLDER.get().userId = userId;
    }

    public Long getFirmId() {
        return HOLDER.get().firmId;
    }

    public void setFirmId(Long firmId) {
        HOLDER.get().firmId = firmId;
    }

    public Role getRole() {
        return HOLDER.get().role;
    }

    public void setRole(Role role) {
        HOLDER.get().role = role;
    }

    public boolean hasRole(Role required) {
        Role current = HOLDER.get().role;
        return current != null && current.ordinal() >= required.ordinal();
    }

    public void applyFrom(SessionContext ctx) {
        Holder holder = HOLDER.get();
        if (ctx.getUserId() != null) {
            holder.userId = ctx.getUserId();
        }
        if (ctx.getFirmId() != null) {
            holder.firmId = ctx.getFirmId();
        }
        if (ctx.getRole() != null) {
            holder.role = ctx.getRole();
        }
    }

    public void clear() {
        HOLDER.remove();
    }
}
