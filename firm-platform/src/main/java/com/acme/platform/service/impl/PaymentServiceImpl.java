package com.acme.platform.service.impl;

import com.acme.platform.domain.Firm;
import com.acme.platform.domain.Invoice;
import com.acme.platform.domain.Payment;
import com.acme.platform.repository.FirmRepository;
import com.acme.platform.repository.InvoiceRepository;
import com.acme.platform.repository.PaymentRepository;
import com.acme.platform.service.PaymentService;
import java.math.BigDecimal;
import org.springframework.stereotype.Service;

@Service
public class PaymentServiceImpl implements PaymentService {

    private final InvoiceRepository invoiceRepository;
    private final FirmRepository firmRepository;
    private final PaymentRepository paymentRepository;

    public PaymentServiceImpl(InvoiceRepository invoiceRepository,
                              FirmRepository firmRepository,
                              PaymentRepository paymentRepository) {
        this.invoiceRepository = invoiceRepository;
        this.firmRepository = firmRepository;
        this.paymentRepository = paymentRepository;
    }

    @Override
    public Payment payFromCredit(Long invoiceId) {
        Invoice invoice = invoiceRepository.findById(invoiceId).orElseThrow();
        if ("PAID".equals(invoice.getStatus())) {
            throw new IllegalStateException("Invoice already settled");
        }

        Firm firm = firmRepository.findById(invoice.getFirmId()).orElseThrow();
        BigDecimal amount = invoice.getAmount();

        if (firm.getCreditBalance().compareTo(amount) < 0) {
            throw new IllegalStateException("Insufficient firm credit");
        }

        BigDecimal remaining = firm.getCreditBalance().subtract(amount);
        firm.setCreditBalance(remaining);
        firmRepository.save(firm);

        invoice.setStatus("PAID");
        invoiceRepository.save(invoice);

        return paymentRepository.save(new Payment(invoiceId, firm.getId(), amount));
    }
}
