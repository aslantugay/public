package org.springframework.samples.petclinic.portal.service;

import org.springframework.samples.petclinic.portal.model.PortalAccount;

import java.math.BigDecimal;

public interface PortalCreditService {

    PortalAccount transfer(Integer toAccountId, BigDecimal amount);

    PortalAccount redeemCoupon(String code);

    PortalAccount updateAccount(PortalAccount body);
}
