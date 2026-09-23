package com.meridian.iam.service;

import com.meridian.platform.security.CurrentUserService;
import com.meridian.platform.security.SessionContext;
import com.meridian.platform.security.SessionContextCodec;
import org.springframework.stereotype.Service;

@Service
public class SessionServiceImpl implements SessionService {

    private final CurrentUserService currentUserService;
    private final SessionContextCodec codec;

    public SessionServiceImpl(CurrentUserService currentUserService, SessionContextCodec codec) {
        this.currentUserService = currentUserService;
        this.codec = codec;
    }

    @Override
    public String exportContext() {
        SessionContext ctx = new SessionContext();
        ctx.setUserId(currentUserService.userId());
        ctx.setTenantId(currentUserService.tenantId());
        ctx.setRole(currentUserService.role());
        ctx.setIssuedAt(System.currentTimeMillis());
        return codec.encode(ctx);
    }

    @Override
    public void importContext(String token) {
        SessionContext ctx = codec.decode(token);
        currentUserService.context().applyFrom(ctx);
    }
}
