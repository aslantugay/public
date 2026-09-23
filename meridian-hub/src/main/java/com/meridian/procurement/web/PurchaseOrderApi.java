package com.meridian.procurement.web;

import com.meridian.procurement.domain.PurchaseOrder;
import com.meridian.procurement.dto.PurchaseOrderCreateRequest;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

public interface PurchaseOrderApi {

    @PostMapping("/api/purchase-orders")
    PurchaseOrder create(@RequestBody PurchaseOrderCreateRequest request);

    @GetMapping("/api/tenants/{tenantId}/purchase-orders")
    List<PurchaseOrder> list(@PathVariable Long tenantId);

    @PostMapping("/api/purchase-orders/{poId}/approve")
    PurchaseOrder approve(@PathVariable Long poId);
}
