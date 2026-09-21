package com.acme.platform.web;

import com.acme.platform.domain.Payment;
import com.acme.platform.service.PaymentService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PaymentController {

    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping("/api/invoices/{invoiceId}/pay")
    public Payment pay(@PathVariable Long invoiceId) {
        return paymentService.payFromCredit(invoiceId);
    }
}
