package com.meridian.catalog.web;

import com.meridian.catalog.domain.Vendor;
import com.meridian.catalog.dto.VendorCreateRequest;
import java.util.Map;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/api/vendors")
public interface VendorApi {

    @PostMapping
    Vendor create(@RequestBody VendorCreateRequest request);

    @GetMapping("/{vendorId}")
    ResponseEntity<Vendor> get(@PathVariable Long vendorId);

    @PostMapping("/{vendorId}/logo")
    Map<String, String> setLogo(@PathVariable Long vendorId, @RequestBody Map<String, String> body);
}
