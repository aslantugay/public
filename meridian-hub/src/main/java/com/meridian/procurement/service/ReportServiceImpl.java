package com.meridian.procurement.service;

import com.meridian.integration.report.ReportShellGateway;
import org.springframework.stereotype.Service;

@Service
public class ReportServiceImpl implements ReportService {

    private final ReportShellGateway reportShellGateway;

    public ReportServiceImpl(ReportShellGateway reportShellGateway) {
        this.reportShellGateway = reportShellGateway;
    }

    @Override
    public String exportInvoice(Long invoiceId, String format, String targetName) {
        return reportShellGateway.export("invoice-" + invoiceId + ".html", format, targetName);
    }
}
