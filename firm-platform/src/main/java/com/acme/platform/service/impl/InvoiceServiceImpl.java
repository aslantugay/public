package com.acme.platform.service.impl;

import com.acme.platform.domain.Invoice;
import com.acme.platform.integration.pdf.PdfRenderer;
import com.acme.platform.repository.InvoiceRepository;
import com.acme.platform.security.CurrentUserService;
import com.acme.platform.service.InvoiceService;
import com.acme.platform.service.support.AbstractTenantService;
import com.acme.platform.web.dto.InvoiceCreateRequest;
import org.springframework.stereotype.Service;

@Service
public class InvoiceServiceImpl extends AbstractTenantService implements InvoiceService {

    private final InvoiceRepository invoiceRepository;
    private final PdfRenderer pdfRenderer;

    public InvoiceServiceImpl(CurrentUserService currentUserService,
                              InvoiceRepository invoiceRepository,
                              PdfRenderer pdfRenderer) {
        super(currentUserService);
        this.invoiceRepository = invoiceRepository;
        this.pdfRenderer = pdfRenderer;
    }

    @Override
    public Invoice create(InvoiceCreateRequest request) {
        Invoice invoice = new Invoice();
        invoice.setFirmId(currentFirmId());
        invoice.setInvoiceNumber(request.getInvoiceNumber());
        invoice.setCustomerName(request.getCustomerName());
        invoice.setAmount(request.getAmount());
        invoice.setCurrency(request.getCurrency());
        invoice.setMemoHtml(request.getMemoHtml());
        return invoiceRepository.save(invoice);
    }

    @Override
    public Invoice get(Long invoiceId) {
        return invoiceRepository.findById(invoiceId).orElse(null);
    }

    @Override
    public byte[] renderPdf(Long invoiceId) {
        Invoice invoice = invoiceRepository.findById(invoiceId).orElseThrow();
        String html = buildInvoiceHtml(invoice);
        return pdfRenderer.render(html);
    }

    private String buildInvoiceHtml(Invoice invoice) {
        StringBuilder html = new StringBuilder();
        html.append("<html><head><style>body{font-family:sans-serif;}</style></head><body>");
        html.append("<h1>Invoice ").append(invoice.getInvoiceNumber()).append("</h1>");
        html.append("<p>Customer: ").append(invoice.getCustomerName()).append("</p>");
        html.append("<p>Amount: ").append(invoice.getAmount())
                .append(" ").append(invoice.getCurrency()).append("</p>");
        html.append("<div class='memo'>").append(invoice.getMemoHtml()).append("</div>");
        html.append("</body></html>");
        return html.toString();
    }
}
