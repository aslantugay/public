package com.meridian.tenancy;

import com.meridian.tenancy.domain.Unit;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UnitRepository extends JpaRepository<Unit, Long> {

    List<Unit> findByTenantId(Long tenantId);
}
