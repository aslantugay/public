package com.meridian.tenancy.web;

import com.meridian.tenancy.domain.Unit;
import com.meridian.tenancy.dto.UnitCreateRequest;
import com.meridian.tenancy.service.UnitService;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UnitController implements UnitApi {

    private final UnitService unitService;

    public UnitController(UnitService unitService) {
        this.unitService = unitService;
    }

    @Override
    public List<Unit> list(Long tenantId) {
        return unitService.listForTenant(tenantId);
    }

    @Override
    public Unit create(Long tenantId, UnitCreateRequest request) {
        Unit unit = new Unit();
        unit.setName(request.getName());
        unit.setCostCenter(request.getCostCenter());
        return unitService.create(tenantId, unit);
    }

    @Override
    public ResponseEntity<Void> delete(Long unitId) {
        unitService.delete(unitId);
        return ResponseEntity.noContent().build();
    }
}
