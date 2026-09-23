package com.meridian.catalog.service;

import com.meridian.catalog.domain.Vendor;
import java.util.List;

public interface VendorService {

    Vendor create(String name, String contactEmail, String bankDetails);

    Vendor get(Long vendorId);

    List<Vendor> listForTenant(Long tenantId);

    String importLogo(Long vendorId, String url);
}
