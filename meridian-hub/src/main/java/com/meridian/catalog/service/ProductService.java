package com.meridian.catalog.service;

import com.meridian.catalog.domain.Product;
import java.util.List;
import java.util.Map;

public interface ProductService {

    List<Product> listForTenant(Long tenantId);

    List<Product> bulkImport(List<Map<String, String>> rows);
}
