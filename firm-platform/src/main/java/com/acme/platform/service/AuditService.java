package com.acme.platform.service;

public interface AuditService {

    void record(String action, String detail);
}
