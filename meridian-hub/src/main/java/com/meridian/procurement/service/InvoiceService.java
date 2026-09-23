package com.meridian.procurement.service;

import com.meridian.procurement.domain.Invoice;
import com.meridian.procurement.dto.InvoiceCreateRequest;

public interface InvoiceService {

    Invoice create(InvoiceCreateRequest request);

    Invoice get(Long invoiceId);

    byte[] statementPdf(Long invoiceId);

    String exportCsv(Long tenantId);
}
