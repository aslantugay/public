package com.meridian.catalog.web;

import com.meridian.catalog.domain.Product;
import java.util.List;
import java.util.Map;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

public interface ProductApi {

    @GetMapping("/api/tenants/{tenantId}/products")
    List<Product> list(@PathVariable Long tenantId);

    @PostMapping("/api/products/import")
    List<Product> importProducts(@RequestBody List<Map<String, String>> rows);
}
