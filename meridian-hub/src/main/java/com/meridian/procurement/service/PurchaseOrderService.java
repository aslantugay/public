package com.meridian.procurement.service;

import com.meridian.procurement.domain.PurchaseOrder;
import com.meridian.procurement.dto.PurchaseOrderCreateRequest;
import java.util.List;

public interface PurchaseOrderService {

    PurchaseOrder create(PurchaseOrderCreateRequest request);

    List<PurchaseOrder> listForTenant(Long tenantId);

    PurchaseOrder approve(Long purchaseOrderId);
}
