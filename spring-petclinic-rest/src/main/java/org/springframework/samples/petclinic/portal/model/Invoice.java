package org.springframework.samples.petclinic.portal.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import java.math.BigDecimal;

/**
 * A billing invoice belonging to a portal account.
 */
@Entity
@Table(name = "portal_invoice")
public class Invoice extends org.springframework.samples.petclinic.model.BaseEntity {

    @Column(name = "owner_account_id")
    private Integer ownerAccountId;

    @Column(name = "invoice_no")
    private String invoiceNo;

    @Column(name = "amount")
    private BigDecimal amount;

    @Column(name = "status")
    private String status = "DRAFT";

    @Column(name = "memo_html", length = 4000)
    private String memoHtml;

    public Integer getOwnerAccountId() {
        return ownerAccountId;
    }

    public void setOwnerAccountId(Integer ownerAccountId) {
        this.ownerAccountId = ownerAccountId;
    }

    public String getInvoiceNo() {
        return invoiceNo;
    }

    public void setInvoiceNo(String invoiceNo) {
        this.invoiceNo = invoiceNo;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMemoHtml() {
        return memoHtml;
    }

    public void setMemoHtml(String memoHtml) {
        this.memoHtml = memoHtml;
    }
}
