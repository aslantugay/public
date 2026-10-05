package com.macro.mall.portal.service;

import com.macro.mall.portal.domain.GiftCard;
import com.macro.mall.portal.domain.GiftCardRedeemParam;

public interface GiftCardService {

    /** 核销礼品卡，金额充入当前会员钱包 */
    GiftCard redeem(GiftCardRedeemParam param);
}
