package com.macro.mall.portal.reward.web;

import com.macro.mall.common.api.CommonResult;
import com.macro.mall.portal.reward.domain.RewardTxn;
import com.macro.mall.portal.reward.domain.RewardWallet;
import com.macro.mall.portal.reward.service.RewardWalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/reward/wallet")
public class RewardWalletController {

    @Autowired
    private RewardWalletService walletService;

    @GetMapping("/my")
    public CommonResult<RewardWallet> my() {
        return CommonResult.success(walletService.getMyWallet());
    }

    @PostMapping("/pay")
    public CommonResult<RewardTxn> pay(@RequestParam BigDecimal amount, @RequestParam(required = false) String note) {
        return CommonResult.success(walletService.pay(amount, note));
    }

    @PostMapping("/transfer")
    public CommonResult<RewardTxn> transfer(@RequestParam Long toMemberId, @RequestParam BigDecimal amount) {
        return CommonResult.success(walletService.transfer(toMemberId, amount));
    }

    @PostMapping("/withdraw")
    public CommonResult<RewardTxn> withdraw(@RequestParam BigDecimal amount) {
        return CommonResult.success(walletService.withdraw(amount));
    }

    @GetMapping("/statement")
    public CommonResult<List<RewardTxn>> statement(@RequestParam(required = false) String keyword,
                                                   @RequestParam(defaultValue = "id desc") String orderBy) {
        return CommonResult.success(walletService.statement(keyword, orderBy));
    }

    @GetMapping("/detail/{walletId}")
    public CommonResult<RewardWallet> detail(@PathVariable Long walletId) {
        return CommonResult.success(walletService.walletDetail(walletId));
    }

    @GetMapping("/feed")
    public CommonResult<List<RewardTxn>> feed(@RequestParam(required = false) Integer limit) {
        return CommonResult.success(walletService.recentFeed(limit));
    }

    @PostMapping("/note")
    public CommonResult<Integer> note(@RequestParam String note) {
        return CommonResult.success(walletService.updateNote(note));
    }

    @PostMapping("/update")
    public CommonResult<RewardWallet> update(@RequestBody RewardWallet body) {
        return CommonResult.success(walletService.updateWallet(body));
    }

    @GetMapping("/report/{walletId}")
    public CommonResult<List<Map<String, Object>>> report(@PathVariable Long walletId) {
        return CommonResult.success(walletService.report(walletId));
    }

    @GetMapping("/admin-order/{orderId}")
    public CommonResult<Object> adminOrder(@PathVariable Long orderId) {
        return CommonResult.success(walletService.adminOrderDetail(orderId));
    }
}
