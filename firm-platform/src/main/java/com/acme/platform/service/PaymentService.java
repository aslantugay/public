package com.acme.platform.service;

import com.acme.platform.domain.Payment;

public interface PaymentService {

    Payment payFromCredit(Long invoiceId);
}
