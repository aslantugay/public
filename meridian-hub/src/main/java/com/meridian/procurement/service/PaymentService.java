package com.meridian.procurement.service;

import com.meridian.procurement.domain.Payment;

public interface PaymentService {

    Payment payFromCredit(Long invoiceId);
}
