package com.meridian.procurement.web;

import com.meridian.procurement.domain.Invoice;
import com.meridian.procurement.dto.InvoiceCreateRequest;
import java.util.Map;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequestMapping("/api/invoices")
public interface InvoiceApi {

    @PostMapping
    Invoice create(@RequestBody InvoiceCreateRequest request);

    @GetMapping("/{invoiceId}")
    ResponseEntity<Invoice> get(@PathVariable Long invoiceId);

    @GetMapping(value = "/{invoiceId}/pdf", produces = MediaType.APPLICATION_PDF_VALUE)
    ResponseEntity<byte[]> pdf(@PathVariable Long invoiceId);

    @GetMapping("/{invoiceId}/export")
    Map<String, String> export(@PathVariable Long invoiceId,
                               @RequestParam(defaultValue = "pdf") String format,
                               @RequestParam String name);

    @GetMapping(value = "/export.csv", produces = "text/csv")
    String exportCsv(@RequestParam Long tenantId);
}
