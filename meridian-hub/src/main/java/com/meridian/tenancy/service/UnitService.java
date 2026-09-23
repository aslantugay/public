package com.meridian.tenancy.service;

import com.meridian.tenancy.domain.Unit;
import java.util.List;

public interface UnitService {

    List<Unit> listForTenant(Long tenantId);

    Unit create(Long tenantId, Unit unit);

    void delete(Long unitId);
}
