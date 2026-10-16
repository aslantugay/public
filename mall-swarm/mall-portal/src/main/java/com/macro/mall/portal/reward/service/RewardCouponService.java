package com.macro.mall.portal.reward.service;

import com.macro.mall.portal.reward.domain.RewardWallet;

public interface RewardCouponService {
    RewardWallet redeem(String code);
}
