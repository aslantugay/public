package com.acme.platform.service;

import com.acme.platform.domain.Unit;
import java.util.List;

public interface UnitService {

    List<Unit> listForFirm(Long firmId);

    Unit create(Long firmId, Unit unit);

    void delete(Long unitId);
}
