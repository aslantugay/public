package com.meridian.tenancy.web;

import com.meridian.tenancy.domain.Tenant;
import com.meridian.tenancy.dto.TenantCreateRequest;
import com.meridian.tenancy.dto.TenantDetailView;
import com.meridian.tenancy.facade.TenantFacade;
import com.meridian.tenancy.service.TenantService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TenantController implements TenantApi {

    private final TenantFacade tenantFacade;
    private final TenantService tenantService;

    public TenantController(TenantFacade tenantFacade, TenantService tenantService) {
        this.tenantFacade = tenantFacade;
        this.tenantService = tenantService;
    }

    @Override
    public ResponseEntity<TenantDetailView> get(Long tenantId) {
        TenantDetailView view = tenantFacade.detail(tenantId);
        return view == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(view);
    }

    @Override
    public ResponseEntity<Tenant> create(TenantCreateRequest request) {
        Tenant tenant = new Tenant();
        tenant.setName(request.getName());
        tenant.setTaxNumber(request.getTaxNumber());
        tenant.setBillingEmail(request.getBillingEmail());
        return ResponseEntity.ok(tenantService.create(tenant));
    }
}
