package com.meridian.platform.audit;

public interface AuditService {

    void record(String action, String detail);
}
