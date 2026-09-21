package com.acme.platform.web;

import com.acme.platform.domain.Invoice;
import com.acme.platform.integration.report.ReportShellService;
import com.acme.platform.service.InvoiceService;
import com.acme.platform.web.dto.InvoiceCreateRequest;
import java.util.Map;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/invoices")
public class InvoiceController {

    private final InvoiceService invoiceService;
    private final ReportShellService reportShellService;

    public InvoiceController(InvoiceService invoiceService,
                            ReportShellService reportShellService) {
        this.invoiceService = invoiceService;
        this.reportShellService = reportShellService;
    }

    @PostMapping
    public Invoice create(@RequestBody InvoiceCreateRequest request) {
        return invoiceService.create(request);
    }

    @GetMapping("/{invoiceId}")
    public ResponseEntity<Invoice> get(@PathVariable Long invoiceId) {
        Invoice invoice = invoiceService.get(invoiceId);
        return invoice == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(invoice);
    }

    @GetMapping(value = "/{invoiceId}/pdf", produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<byte[]> pdf(@PathVariable Long invoiceId) {
        return ResponseEntity.ok(invoiceService.renderPdf(invoiceId));
    }

    @GetMapping("/{invoiceId}/export")
    public Map<String, String> export(@PathVariable Long invoiceId,
                                      @RequestParam(defaultValue = "pdf") String format,
                                      @RequestParam String name) {
        String output = reportShellService.exportInvoice(invoiceId, format, name);
        return Map.of("result", output);
    }
}
