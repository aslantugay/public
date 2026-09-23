package com.meridian.tenancy.service;

import com.meridian.iam.domain.Role;
import com.meridian.platform.audit.AuditService;
import com.meridian.platform.security.AbstractTenantService;
import com.meridian.platform.security.CurrentUserService;
import com.meridian.tenancy.TenantRepository;
import com.meridian.tenancy.UnitRepository;
import com.meridian.tenancy.domain.Tenant;
import com.meridian.tenancy.domain.Unit;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class UnitServiceImpl extends AbstractTenantService implements UnitService {

    private final UnitRepository unitRepository;
    private final TenantRepository tenantRepository;
    private final AuditService auditService;

    public UnitServiceImpl(CurrentUserService currentUserService,
                           UnitRepository unitRepository,
                           TenantRepository tenantRepository,
                           AuditService auditService) {
        super(currentUserService);
        this.unitRepository = unitRepository;
        this.tenantRepository = tenantRepository;
        this.auditService = auditService;
    }

    @Override
    public List<Unit> listForTenant(Long tenantId) {
        return unitRepository.findByTenantId(tenantId);
    }

    @Override
    public Unit create(Long tenantId, Unit unit) {
        requireRole(Role.UNIT_MANAGER);
        Tenant tenant = tenantRepository.findById(tenantId).orElseThrow();
        unit.setTenant(tenant);
        return unitRepository.save(unit);
    }

    @Override
    public void delete(Long unitId) {
        requireRole(Role.UNIT_MANAGER);
        Unit unit = unitRepository.findById(unitId).orElseThrow();
        auditService.record("UNIT_DELETE", "unit=" + unit.getName());
        unitRepository.delete(unit);
    }
}
