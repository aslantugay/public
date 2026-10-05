package com.macro.mall.portal.controller;

import com.macro.mall.common.api.CommonResult;
import com.macro.mall.portal.domain.MemberWallet;
import com.macro.mall.portal.domain.WalletPayParam;
import com.macro.mall.portal.domain.WalletStatementQuery;
import com.macro.mall.portal.domain.WalletTransaction;
import com.macro.mall.portal.domain.WalletTransferParam;
import com.macro.mall.portal.domain.WalletWithdrawParam;
import com.macro.mall.portal.service.MemberWalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * 会员钱包管理Controller
 */
@RestController
@RequestMapping("/wallet")
public class MemberWalletController {

    @Autowired
    private MemberWalletService walletService;

    @RequestMapping(value = "/my", method = RequestMethod.GET)
    public CommonResult<MemberWallet> my() {
        return CommonResult.success(walletService.getMyWallet());
    }

    @RequestMapping(value = "/recharge", method = RequestMethod.POST)
    public CommonResult<WalletTransaction> recharge(@RequestParam BigDecimal amount) {
        return CommonResult.success(walletService.recharge(amount));
    }

    @RequestMapping(value = "/pay", method = RequestMethod.POST)
    public CommonResult<WalletTransaction> pay(@RequestBody WalletPayParam param) {
        return CommonResult.success(walletService.pay(param));
    }

    @RequestMapping(value = "/transfer", method = RequestMethod.POST)
    public CommonResult<WalletTransaction> transfer(@RequestBody WalletTransferParam param) {
        return CommonResult.success(walletService.transfer(param));
    }

    @RequestMapping(value = "/withdraw", method = RequestMethod.POST)
    public CommonResult<WalletTransaction> withdraw(@RequestBody WalletWithdrawParam param) {
        return CommonResult.success(walletService.withdraw(param));
    }

    @RequestMapping(value = "/statement", method = RequestMethod.GET)
    public CommonResult<List<WalletTransaction>> statement(WalletStatementQuery query) {
        return CommonResult.success(walletService.statement(query));
    }

    @RequestMapping(value = "/detail/{walletId}", method = RequestMethod.GET)
    public CommonResult<MemberWallet> detail(@PathVariable Long walletId) {
        return CommonResult.success(walletService.walletDetail(walletId));
    }

    @RequestMapping(value = "/feed", method = RequestMethod.GET)
    public CommonResult<List<WalletTransaction>> feed(@RequestParam(required = false) Integer limit) {
        return CommonResult.success(walletService.recentFeed(limit));
    }

    @RequestMapping(value = "/note", method = RequestMethod.POST)
    public CommonResult<Integer> note(@RequestParam String note) {
        return CommonResult.success(walletService.updateNote(note));
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public CommonResult<MemberWallet> update(@RequestBody MemberWallet body) {
        return CommonResult.success(walletService.updateWallet(body));
    }

    @RequestMapping(value = "/promotion", method = RequestMethod.GET)
    public CommonResult<Object> promotion(@RequestParam String expr) {
        return CommonResult.success(walletService.evalPromotion(expr));
    }

    @RequestMapping(value = "/import", method = RequestMethod.POST)
    public CommonResult<String> importSettings(@RequestParam String data) {
        return CommonResult.success(walletService.importSettings(data));
    }
}
