package com.meridian.procurement.service;

import com.meridian.iam.domain.Role;
import com.meridian.platform.audit.AuditService;
import com.meridian.platform.security.AbstractTenantService;
import com.meridian.platform.security.CurrentUserService;
import com.meridian.procurement.PurchaseOrderRepository;
import com.meridian.procurement.domain.PurchaseOrder;
import com.meridian.procurement.dto.PurchaseOrderCreateRequest;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class PurchaseOrderServiceImpl extends AbstractTenantService implements PurchaseOrderService {

    private final PurchaseOrderRepository purchaseOrderRepository;
    private final AuditService auditService;

    public PurchaseOrderServiceImpl(CurrentUserService currentUserService,
                                    PurchaseOrderRepository purchaseOrderRepository,
                                    AuditService auditService) {
        super(currentUserService);
        this.purchaseOrderRepository = purchaseOrderRepository;
        this.auditService = auditService;
    }

    @Override
    public PurchaseOrder create(PurchaseOrderCreateRequest request) {
        PurchaseOrder po = new PurchaseOrder();
        po.setTenantId(currentTenantId());
        po.setVendorId(request.getVendorId());
        po.setAmount(request.getAmount());
        po.setRequestedByUserId(currentUserId());
        return purchaseOrderRepository.save(po);
    }

    @Override
    public List<PurchaseOrder> listForTenant(Long tenantId) {
        return purchaseOrderRepository.findByTenantId(tenantId);
    }

    @Override
    public PurchaseOrder approve(Long purchaseOrderId) {
        requireRole(Role.AUDITOR);
        PurchaseOrder po = purchaseOrderRepository.findById(purchaseOrderId).orElseThrow();
        po.setStatus("APPROVED");
        auditService.record("PO_APPROVE", "po=" + po.getId() + " amount=" + po.getAmount());
        return purchaseOrderRepository.save(po);
    }
}
