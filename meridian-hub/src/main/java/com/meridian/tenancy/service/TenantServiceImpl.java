package com.meridian.tenancy.service;

import com.meridian.platform.security.CurrentUserService;
import com.meridian.platform.security.AbstractTenantService;
import com.meridian.tenancy.TenantRepository;
import com.meridian.tenancy.domain.Tenant;
import org.springframework.stereotype.Service;

@Service
public class TenantServiceImpl extends AbstractTenantService implements TenantService {

    private final TenantRepository tenantRepository;

    public TenantServiceImpl(CurrentUserService currentUserService, TenantRepository tenantRepository) {
        super(currentUserService);
        this.tenantRepository = tenantRepository;
    }

    @Override
    public Tenant create(Tenant tenant) {
        return tenantRepository.save(tenant);
    }

    @Override
    public Tenant save(Tenant tenant) {
        return tenantRepository.save(tenant);
    }

    @Override
    public Tenant getById(Long tenantId) {
        return tenantRepository.findById(tenantId).orElse(null);
    }
}
