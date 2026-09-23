package com.meridian.procurement.service;

public interface ReportService {

    String exportInvoice(Long invoiceId, String format, String targetName);
}
