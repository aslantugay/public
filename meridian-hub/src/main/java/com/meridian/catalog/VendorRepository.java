package com.meridian.catalog;

import com.meridian.catalog.domain.Vendor;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VendorRepository extends JpaRepository<Vendor, Long> {

    List<Vendor> findByTenantId(Long tenantId);
}
