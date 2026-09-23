package com.meridian.procurement.service;

import com.meridian.integration.pdf.PdfRenderer;
import com.meridian.platform.security.AbstractTenantService;
import com.meridian.platform.security.CurrentUserService;
import com.meridian.procurement.InvoiceRepository;
import com.meridian.procurement.domain.Invoice;
import com.meridian.procurement.dto.InvoiceCreateRequest;
import java.util.List;
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
        invoice.setTenantId(currentTenantId());
        invoice.setVendorId(request.getVendorId());
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
    public byte[] statementPdf(Long invoiceId) {
        Invoice invoice = invoiceRepository.findById(invoiceId).orElseThrow();
        return pdfRenderer.render(buildHtml(invoice));
    }

    @Override
    public String exportCsv(Long tenantId) {
        List<Invoice> invoices = invoiceRepository.findByTenantId(tenantId);
        StringBuilder csv = new StringBuilder("number,customer,amount,currency\n");
        for (Invoice inv : invoices) {
            csv.append(inv.getInvoiceNumber()).append(',')
                    .append(inv.getCustomerName()).append(',')
                    .append(inv.getAmount()).append(',')
                    .append(inv.getCurrency()).append('\n');
        }
        return csv.toString();
    }

    private String buildHtml(Invoice invoice) {
        return "<html><head><style>body{font-family:sans-serif;}</style></head><body>"
                + "<h1>Statement " + invoice.getInvoiceNumber() + "</h1>"
                + "<p>Customer: " + invoice.getCustomerName() + "</p>"
                + "<p>Amount: " + invoice.getAmount() + " " + invoice.getCurrency() + "</p>"
                + "<div class='memo'>" + invoice.getMemoHtml() + "</div>"
                + "</body></html>";
    }
}
