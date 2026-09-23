package com.meridian.procurement.web;

import com.meridian.procurement.domain.Payment;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

public interface PaymentApi {

    @PostMapping("/api/invoices/{invoiceId}/pay")
    Payment pay(@PathVariable Long invoiceId);
}
