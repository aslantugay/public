package org.springframework.samples.petclinic.portal.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import java.math.BigDecimal;

/**
 * A one-time credit coupon that tops up an account's balance when redeemed.
 */
@Entity
@Table(name = "credit_coupon")
public class CreditCoupon extends org.springframework.samples.petclinic.model.BaseEntity {

    @Column(name = "code")
    private String code;

    @Column(name = "amount")
    private BigDecimal amount;

    @Column(name = "redeemed")
    private Boolean redeemed = false;

    @Column(name = "redeemed_by")
    private Integer redeemedBy;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Boolean getRedeemed() {
        return redeemed;
    }

    public void setRedeemed(Boolean redeemed) {
        this.redeemed = redeemed;
    }

    public Integer getRedeemedBy() {
        return redeemedBy;
    }

    public void setRedeemedBy(Integer redeemedBy) {
        this.redeemedBy = redeemedBy;
    }
}
