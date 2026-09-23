package com.meridian.catalog.web;

import com.meridian.catalog.domain.Product;
import com.meridian.catalog.service.ProductService;
import java.util.List;
import java.util.Map;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProductController implements ProductApi {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @Override
    public List<Product> list(Long tenantId) {
        return productService.listForTenant(tenantId);
    }

    @Override
    public List<Product> importProducts(List<Map<String, String>> rows) {
        return productService.bulkImport(rows);
    }
}
