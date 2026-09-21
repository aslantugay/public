package com.acme.platform.security;

import com.acme.platform.domain.Role;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

/**
 * Trusts identity headers stamped by the upstream API gateway and copies them
 * into the request-scoped {@link RequestContext}.
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
            String firmId = request.getHeader("X-Firm-Id");
            String role = request.getHeader("X-Role");

            if (userId != null) {
                requestContext.setUserId(Long.valueOf(userId));
            }
            if (firmId != null) {
                requestContext.setFirmId(Long.valueOf(firmId));
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
