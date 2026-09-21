package com.acme.platform.security;

import com.acme.platform.security.jwt.JwtClaims;
import com.acme.platform.security.jwt.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

/**
 * When a bearer token is presented it takes precedence over the gateway
 * identity headers and its claims are copied into the {@link RequestContext}.
 */
@Component
@Order(2)
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final RequestContext requestContext;

    public JwtAuthenticationFilter(JwtService jwtService, RequestContext requestContext) {
        this.jwtService = jwtService;
        this.requestContext = requestContext;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        String header = request.getHeader("Authorization");
        if (header != null && header.startsWith("Bearer ")) {
            try {
                JwtClaims claims = jwtService.verify(header.substring(7).trim());
                if (claims.getUserId() != null) {
                    requestContext.setUserId(claims.getUserId());
                }
                if (claims.getFirmId() != null) {
                    requestContext.setFirmId(claims.getFirmId());
                }
                if (claims.getRole() != null) {
                    requestContext.setRole(claims.getRole());
                }
            } catch (RuntimeException ignored) {
                // fall through as an anonymous/gateway-identified caller
            }
        }
        filterChain.doFilter(request, response);
    }
}
