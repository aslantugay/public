package com.meridian.platform.security;

import com.meridian.iam.domain.Role;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

/**
 * Copies the identity headers stamped by the upstream API gateway into the
 * thread-bound {@link RequestContext}.
 */
@Component
@Order(1)
public class GatewayContextFilter extends OncePerRequestFilter {

    private final RequestContext requestContext;

    public GatewayContextFilter(RequestContext requestContext) {
        this.requestContext = requestContext;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        try {
            String userId = request.getHeader("X-User-Id");
            String tenantId = request.getHeader("X-Tenant-Id");
            String role = request.getHeader("X-Role");
            if (userId != null) {
                requestContext.setUserId(Long.valueOf(userId));
            }
            if (tenantId != null) {
                requestContext.setTenantId(Long.valueOf(tenantId));
            }
            if (role != null) {
                requestContext.setRole(Role.valueOf(role));
            }
            filterChain.doFilter(request, response);
        } finally {
            requestContext.clear();
        }
    }
}
