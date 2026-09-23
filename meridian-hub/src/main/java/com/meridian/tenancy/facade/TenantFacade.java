package com.meridian.tenancy.facade;

import com.meridian.tenancy.dto.TenantDetailView;

public interface TenantFacade {

    TenantDetailView detail(Long tenantId);
}
