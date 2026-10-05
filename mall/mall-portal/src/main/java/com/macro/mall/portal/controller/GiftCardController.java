package com.macro.mall.portal.controller;

import com.macro.mall.common.api.CommonResult;
import com.macro.mall.portal.domain.GiftCard;
import com.macro.mall.portal.domain.GiftCardRedeemParam;
import com.macro.mall.portal.service.GiftCardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * 礼品卡Controller
 */
@RestController
@RequestMapping("/giftcard")
public class GiftCardController {

    @Autowired
    private GiftCardService giftCardService;

    @RequestMapping(value = "/redeem", method = RequestMethod.POST)
    public CommonResult<GiftCard> redeem(@RequestBody GiftCardRedeemParam param) {
        return CommonResult.success(giftCardService.redeem(param));
    }
}
