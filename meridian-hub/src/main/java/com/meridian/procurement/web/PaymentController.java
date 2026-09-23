package com.meridian.procurement.web;

import com.meridian.procurement.domain.Payment;
import com.meridian.procurement.service.PaymentService;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PaymentController implements PaymentApi {

    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @Override
    public Payment pay(Long invoiceId) {
        return paymentService.payFromCredit(invoiceId);
    }
}
