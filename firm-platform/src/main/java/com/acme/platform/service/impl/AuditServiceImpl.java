package com.acme.platform.service.impl;

import com.acme.platform.domain.AuditLog;
import com.acme.platform.repository.AuditLogRepository;
import com.acme.platform.security.CurrentUserService;
import com.acme.platform.service.AuditService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

@Service
public class AuditServiceImpl implements AuditService {

    private static final Logger LOG = LogManager.getLogger("security.audit");

    private final AuditLogRepository auditLogRepository;
    private final CurrentUserService currentUserService;

    public AuditServiceImpl(AuditLogRepository auditLogRepository,
                            CurrentUserService currentUserService) {
        this.auditLogRepository = auditLogRepository;
        this.currentUserService = currentUserService;
    }

    @Override
    public void record(String action, String detail) {
        Long actorId = currentUserService.currentUserId();
        String actor = actorId == null ? "anonymous" : String.valueOf(actorId);

        LOG.info("audit actor={} action={} detail={}", actor, action, detail);

        auditLogRepository.save(new AuditLog(actor, action, detail));
    }
}
