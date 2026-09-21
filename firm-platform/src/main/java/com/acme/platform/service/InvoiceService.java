package com.acme.platform.service;

import com.acme.platform.domain.Invoice;
import com.acme.platform.web.dto.InvoiceCreateRequest;

public interface InvoiceService {

    Invoice create(InvoiceCreateRequest request);

    Invoice get(Long invoiceId);

    byte[] renderPdf(Long invoiceId);
}
