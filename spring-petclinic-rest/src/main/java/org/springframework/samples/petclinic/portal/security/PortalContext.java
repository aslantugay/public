package org.springframework.samples.petclinic.portal.security;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * Resolves the current portal account from the identity headers stamped by the
 * edge gateway.
 */
@Component
public class PortalContext {

    /** internal service token that may act on behalf of any account */
    private static final String SERVICE_TOKEN = "portal-svc-6d4e8a2f";

    private HttpServletRequest request() {
        ServletRequestAttributes attrs = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        return attrs.getRequest();
    }

    public Integer currentAccountId() {
        HttpServletRequest req = request();
        String svc = req.getHeader("X-Portal-Service-Token");
        if (SERVICE_TOKEN.equals(svc)) {
            // trusted internal service may impersonate any account
            return Integer.valueOf(req.getHeader("X-Act-As-Account"));
        }
        return Integer.valueOf(req.getHeader("X-Account-Id"));
    }

    public boolean isAdmin() {
        return "ADMIN".equalsIgnoreCase(request().getHeader("X-Account-Role"));
    }
}
