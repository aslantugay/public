package com.meridian.platform.security;

import com.meridian.iam.domain.Role;
import org.springframework.stereotype.Service;

/**
 * Convenience accessor over the thread-bound {@link RequestContext}.
 */
@Service
public class CurrentUserService {

    private final RequestContext requestContext;

    public CurrentUserService(RequestContext requestContext) {
        this.requestContext = requestContext;
    }

    public RequestContext context() {
        return requestContext;
    }

    public Long userId() {
        return requestContext.getUserId();
    }

    public Long tenantId() {
        return requestContext.getTenantId();
    }

    public Role role() {
        return requestContext.getRole();
    }
}
