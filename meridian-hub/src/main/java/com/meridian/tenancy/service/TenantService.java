package com.meridian.tenancy.service;

import com.meridian.tenancy.domain.Tenant;

public interface TenantService {

    Tenant create(Tenant tenant);

    Tenant save(Tenant tenant);

    Tenant getById(Long tenantId);
}
