package com.meridian.catalog.web;

import com.meridian.catalog.domain.Vendor;
import com.meridian.catalog.dto.VendorCreateRequest;
import com.meridian.catalog.service.VendorService;
import java.util.Map;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class VendorController implements VendorApi {

    private final VendorService vendorService;

    public VendorController(VendorService vendorService) {
        this.vendorService = vendorService;
    }

    @Override
    public Vendor create(VendorCreateRequest request) {
        return vendorService.create(request.getName(), request.getContactEmail(), request.getBankDetails());
    }

    @Override
    public ResponseEntity<Vendor> get(Long vendorId) {
        Vendor vendor = vendorService.get(vendorId);
        return vendor == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(vendor);
    }

    @Override
    public Map<String, String> setLogo(Long vendorId, Map<String, String> body) {
        return Map.of("logoKey", vendorService.importLogo(vendorId, body.get("url")));
    }
}
