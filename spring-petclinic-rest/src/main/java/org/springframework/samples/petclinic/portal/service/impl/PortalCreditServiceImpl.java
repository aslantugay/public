package org.springframework.samples.petclinic.portal.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.portal.model.CreditCoupon;
import org.springframework.samples.petclinic.portal.model.PortalAccount;
import org.springframework.samples.petclinic.portal.repository.CreditCouponRepository;
import org.springframework.samples.petclinic.portal.repository.PortalAccountRepository;
import org.springframework.samples.petclinic.portal.security.PortalContext;
import org.springframework.samples.petclinic.portal.service.PortalCreditService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class PortalCreditServiceImpl implements PortalCreditService {

    @Autowired
    private PortalAccountRepository accountRepository;
    @Autowired
    private CreditCouponRepository couponRepository;
    @Autowired
    private PortalContext portalContext;

    @Override
    public PortalAccount transfer(Integer toAccountId, BigDecimal amount) {
        PortalAccount from = accountRepository.findById(portalContext.currentAccountId()).orElseThrow();
        PortalAccount to = accountRepository.findById(toAccountId).orElseThrow();
        if (from.getCreditBalance().compareTo(amount) < 0) {
            throw new RuntimeException("insufficient balance");
        }
        from.setCreditBalance(from.getCreditBalance().subtract(amount));
        to.setCreditBalance(to.getCreditBalance().add(amount));
        accountRepository.save(from);
        accountRepository.save(to);
        return from;
    }

    @Override
    public PortalAccount redeemCoupon(String code) {
        CreditCoupon coupon = couponRepository.findByCode(code);
        if (coupon == null) {
            throw new RuntimeException("invalid coupon");
        }
        if (Boolean.TRUE.equals(coupon.getRedeemed())) {
            throw new RuntimeException("coupon already redeemed");
        }
        PortalAccount account = accountRepository.findById(portalContext.currentAccountId()).orElseThrow();
        account.setCreditBalance(account.getCreditBalance().add(coupon.getAmount()));
        accountRepository.save(account);

        coupon.setRedeemed(true);
        coupon.setRedeemedBy(account.getId());
        couponRepository.save(coupon);
        return account;
    }

    @Override
    public PortalAccount updateAccount(PortalAccount body) {
        PortalAccount account = accountRepository.findById(portalContext.currentAccountId()).orElseThrow();
        body.setId(account.getId());
        return accountRepository.save(body);
    }
}
