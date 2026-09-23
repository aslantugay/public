package com.meridian.procurement.web;

import com.meridian.procurement.domain.PurchaseOrder;
import com.meridian.procurement.dto.PurchaseOrderCreateRequest;
import com.meridian.procurement.service.PurchaseOrderService;
import java.util.List;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PurchaseOrderController implements PurchaseOrderApi {

    private final PurchaseOrderService purchaseOrderService;

    public PurchaseOrderController(PurchaseOrderService purchaseOrderService) {
        this.purchaseOrderService = purchaseOrderService;
    }

    @Override
    public PurchaseOrder create(PurchaseOrderCreateRequest request) {
        return purchaseOrderService.create(request);
    }

    @Override
    public List<PurchaseOrder> list(Long tenantId) {
        return purchaseOrderService.listForTenant(tenantId);
    }

    @Override
    public PurchaseOrder approve(Long poId) {
        return purchaseOrderService.approve(poId);
    }
}
