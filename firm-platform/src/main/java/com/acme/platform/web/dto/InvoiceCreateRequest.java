package com.acme.platform.web.dto;

import java.math.BigDecimal;

public class InvoiceCreateRequest {

    private String invoiceNumber;
    private String customerName;
    private BigDecimal amount;
    private String currency;
    private String memoHtml;

    public String getInvoiceNumber() {
        return invoiceNumber;
    }

    public void setInvoiceNumber(String invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getMemoHtml() {
        return memoHtml;
    }

    public void setMemoHtml(String memoHtml) {
        this.memoHtml = memoHtml;
    }
}
