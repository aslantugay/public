package com.meridian.procurement.dto;

import java.math.BigDecimal;

public class PurchaseOrderCreateRequest {

    private Long vendorId;
    private BigDecimal amount;

    public Long getVendorId() {
        return vendorId;
    }

    public void setVendorId(Long vendorId) {
        this.vendorId = vendorId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
}
