package com.macro.mall.portal.reward.web;

import com.macro.mall.common.api.CommonResult;
import com.macro.mall.portal.reward.domain.RewardWallet;
import com.macro.mall.portal.reward.service.RewardCouponService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/reward/coupon")
public class RewardCouponController {

    @Autowired
    private RewardCouponService couponService;

    @PostMapping("/redeem")
    public CommonResult<RewardWallet> redeem(@RequestParam String code) {
        return CommonResult.success(couponService.redeem(code));
    }
}
