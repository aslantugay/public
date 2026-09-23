package com.meridian.tenancy.web;

import com.meridian.tenancy.domain.Tenant;
import com.meridian.tenancy.dto.TenantCreateRequest;
import com.meridian.tenancy.dto.TenantDetailView;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/api/tenants")
public interface TenantApi {

    @GetMapping("/{tenantId}")
    ResponseEntity<TenantDetailView> get(@PathVariable Long tenantId);

    @PostMapping
    ResponseEntity<Tenant> create(@RequestBody TenantCreateRequest request);
}
