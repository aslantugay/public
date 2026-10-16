package com.macro.mall.portal.reward.web;

import com.macro.mall.common.api.CommonResult;
import com.macro.mall.portal.reward.service.RewardPromotionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/reward/promo")
public class RewardPromotionController {

    @Autowired
    private RewardPromotionService promotionService;

    @GetMapping("/eval")
    public CommonResult<Object> eval(@RequestParam String expr) {
        return CommonResult.success(promotionService.evalRule(expr));
    }
}
