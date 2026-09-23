package com.meridian.platform.audit;

import com.meridian.platform.security.RequestContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

@Service
public class AuditServiceImpl implements AuditService {

    private static final Logger LOG = LogManager.getLogger("meridian.audit");

    private final AuditLogRepository auditLogRepository;
    private final RequestContext requestContext;

    public AuditServiceImpl(AuditLogRepository auditLogRepository, RequestContext requestContext) {
        this.auditLogRepository = auditLogRepository;
        this.requestContext = requestContext;
    }

    @Override
    public void record(String action, String detail) {
        Long actorId = requestContext.getUserId();
        String actor = actorId == null ? "anonymous" : String.valueOf(actorId);

        LOG.info("audit actor={} action={} detail={}", actor, action, detail);

        auditLogRepository.save(new AuditLog(actor, action, detail));
    }
}
