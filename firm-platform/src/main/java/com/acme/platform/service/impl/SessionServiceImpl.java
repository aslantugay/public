package com.acme.platform.service.impl;

import com.acme.platform.security.CurrentUserService;
import com.acme.platform.security.SessionContext;
import com.acme.platform.security.SessionContextCodec;
import com.acme.platform.service.SessionService;
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
        ctx.setUserId(currentUserService.currentUserId());
        ctx.setFirmId(currentUserService.currentFirmId());
        ctx.setRole(currentUserService.context().getRole());
        ctx.setIssuedAt(System.currentTimeMillis());
        return codec.encode(ctx);
    }

    @Override
    public void importContext(String token) {
        SessionContext ctx = codec.decode(token);
        currentUserService.context().applyFrom(ctx);
    }
}
