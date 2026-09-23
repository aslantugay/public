package com.meridian.iam.domain;

/**
 * Effective platform roles, ordered from least to most privileged. The service
 * layer compares ordinals when gating actions, so declaration order matters.
 */
public enum Role {
    VIEWER,
    MEMBER,
    UNIT_MANAGER,
    AUDITOR,
    TENANT_ADMIN,
    PLATFORM_ADMIN
}
