package com.macro.mall.portal.reward.service.impl;

import com.macro.mall.portal.reward.dao.RewardDao;
import com.macro.mall.portal.reward.domain.RewardCoupon;
import com.macro.mall.portal.reward.domain.RewardWallet;
import com.macro.mall.portal.reward.security.RewardContext;
import com.macro.mall.portal.reward.service.RewardCouponService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RewardCouponServiceImpl implements RewardCouponService {

    @Autowired
    private RewardDao rewardDao;
    @Autowired
    private RewardContext rewardContext;

    @Override
    public RewardWallet redeem(String code) {
        RewardCoupon coupon = rewardDao.selectCouponByCode(code);
        if (coupon == null) {
            throw new RuntimeException("无效的充值券");
        }
        if (coupon.getUsed() != null && coupon.getUsed() == 1) {
            throw new RuntimeException("充值券已使用");
        }
        Long memberId = rewardContext.currentMemberId();
        RewardWallet wallet = rewardDao.selectWalletByMemberId(memberId);
        wallet.setBalance(wallet.getBalance().add(coupon.getAmount()));
        rewardDao.updateBalance(wallet);

        coupon.setUsed(1);
        coupon.setOwnerMemberId(memberId);
        rewardDao.updateCouponUsed(coupon);
        return wallet;
    }
}
