package com.meridian.catalog.service;

import com.meridian.catalog.ProductRepository;
import com.meridian.catalog.domain.Product;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.stereotype.Service;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public List<Product> listForTenant(Long tenantId) {
        return productRepository.findByTenantId(tenantId);
    }

    @Override
    public List<Product> bulkImport(List<Map<String, String>> rows) {
        List<Product> saved = new ArrayList<>();
        for (Map<String, String> row : rows) {
            Product product = new Product();
            try {
                BeanUtils.populate(product, row);
            } catch (Exception e) {
                throw new IllegalArgumentException("Invalid product row", e);
            }
            saved.add(productRepository.save(product));
        }
        return saved;
    }
}
