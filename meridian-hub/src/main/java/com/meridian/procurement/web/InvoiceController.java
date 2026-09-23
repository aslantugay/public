package com.meridian.procurement.web;

import com.meridian.procurement.domain.Invoice;
import com.meridian.procurement.dto.InvoiceCreateRequest;
import com.meridian.procurement.service.InvoiceService;
import com.meridian.procurement.service.ReportService;
import java.util.Map;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class InvoiceController implements InvoiceApi {

    private final InvoiceService invoiceService;
    private final ReportService reportService;

    public InvoiceController(InvoiceService invoiceService, ReportService reportService) {
        this.invoiceService = invoiceService;
        this.reportService = reportService;
    }

    @Override
    public Invoice create(InvoiceCreateRequest request) {
        return invoiceService.create(request);
    }

    @Override
    public ResponseEntity<Invoice> get(Long invoiceId) {
        Invoice invoice = invoiceService.get(invoiceId);
        return invoice == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(invoice);
    }

    @Override
    public ResponseEntity<byte[]> pdf(Long invoiceId) {
        return ResponseEntity.ok(invoiceService.statementPdf(invoiceId));
    }

    @Override
    public Map<String, String> export(Long invoiceId, String format, String name) {
        return Map.of("result", reportService.exportInvoice(invoiceId, format, name));
    }

    @Override
    public String exportCsv(Long tenantId) {
        return invoiceService.exportCsv(tenantId);
    }
}
