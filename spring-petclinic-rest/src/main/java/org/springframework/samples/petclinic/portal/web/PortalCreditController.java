package org.springframework.samples.petclinic.portal.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.portal.model.PortalAccount;
import org.springframework.samples.petclinic.portal.service.PortalCreditService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api/portal/credit")
public class PortalCreditController {

    @Autowired
    private PortalCreditService creditService;

    @PostMapping("/transfer")
    public PortalAccount transfer(@RequestParam Integer toAccountId, @RequestParam BigDecimal amount) {
        return creditService.transfer(toAccountId, amount);
    }

    @PostMapping("/redeem")
    public PortalAccount redeem(@RequestParam String code) {
        return creditService.redeemCoupon(code);
    }

    @PutMapping("/account")
    public PortalAccount updateAccount(@RequestBody PortalAccount body) {
        return creditService.updateAccount(body);
    }
}
