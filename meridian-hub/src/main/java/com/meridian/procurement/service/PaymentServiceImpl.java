package com.meridian.procurement.service;

import com.meridian.procurement.CreditAccountRepository;
import com.meridian.procurement.InvoiceRepository;
import com.meridian.procurement.PaymentRepository;
import com.meridian.procurement.domain.CreditAccount;
import com.meridian.procurement.domain.Invoice;
import com.meridian.procurement.domain.Payment;
import java.math.BigDecimal;
import org.springframework.stereotype.Service;

@Service
public class PaymentServiceImpl implements PaymentService {

    private final InvoiceRepository invoiceRepository;
    private final CreditAccountRepository creditAccountRepository;
    private final PaymentRepository paymentRepository;

    public PaymentServiceImpl(InvoiceRepository invoiceRepository,
                              CreditAccountRepository creditAccountRepository,
                              PaymentRepository paymentRepository) {
        this.invoiceRepository = invoiceRepository;
        this.creditAccountRepository = creditAccountRepository;
        this.paymentRepository = paymentRepository;
    }

    @Override
    public Payment payFromCredit(Long invoiceId) {
        Invoice invoice = invoiceRepository.findById(invoiceId).orElseThrow();
        if ("PAID".equals(invoice.getStatus())) {
            throw new IllegalStateException("Invoice already settled");
        }

        CreditAccount account = creditAccountRepository.findByTenantId(invoice.getTenantId())
                .orElseThrow();
        BigDecimal amount = invoice.getAmount();

        if (account.getBalance().compareTo(amount) < 0) {
            throw new IllegalStateException("Insufficient credit");
        }

        account.setBalance(account.getBalance().subtract(amount));
        creditAccountRepository.save(account);

        invoice.setStatus("PAID");
        invoiceRepository.save(invoice);

        return paymentRepository.save(new Payment(invoiceId, invoice.getTenantId(), amount));
    }
}
