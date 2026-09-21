package com.acme.platform.service.impl;

import com.acme.platform.domain.Firm;
import com.acme.platform.domain.Role;
import com.acme.platform.domain.Unit;
import com.acme.platform.repository.FirmRepository;
import com.acme.platform.repository.UnitRepository;
import com.acme.platform.security.CurrentUserService;
import com.acme.platform.service.AuditService;
import com.acme.platform.service.UnitService;
import com.acme.platform.service.support.AbstractTenantService;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class UnitServiceImpl extends AbstractTenantService implements UnitService {

    private final UnitRepository unitRepository;
    private final FirmRepository firmRepository;
    private final AuditService auditService;

    public UnitServiceImpl(CurrentUserService currentUserService,
                           UnitRepository unitRepository,
                           FirmRepository firmRepository,
                           AuditService auditService) {
        super(currentUserService);
        this.unitRepository = unitRepository;
        this.firmRepository = firmRepository;
        this.auditService = auditService;
    }

    @Override
    public List<Unit> listForFirm(Long firmId) {
        return unitRepository.findByFirmId(firmId);
    }

    @Override
    public Unit create(Long firmId, Unit unit) {
        requireRole(Role.UNIT_MANAGER);
        Firm firm = firmRepository.findById(firmId).orElseThrow();
        unit.setFirm(firm);
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
