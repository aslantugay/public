package org.springframework.samples.petclinic.portal.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import java.math.BigDecimal;

/**
 * A pet-owner self-service portal account. Each account owns its own documents,
 * invoices and credit balance.
 */
@Entity
@Table(name = "portal_account")
public class PortalAccount extends org.springframework.samples.petclinic.model.BaseEntity {

    @Column(name = "username")
    private String username;

    @Column(name = "account_no")
    private String accountNo;

    @Column(name = "display_name")
    private String displayName;

    @Column(name = "role")
    private String role = "MEMBER";

    @Column(name = "credit_balance")
    private BigDecimal creditBalance = BigDecimal.ZERO;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAccountNo() {
        return accountNo;
    }

    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public BigDecimal getCreditBalance() {
        return creditBalance;
    }

    public void setCreditBalance(BigDecimal creditBalance) {
        this.creditBalance = creditBalance;
    }
}
