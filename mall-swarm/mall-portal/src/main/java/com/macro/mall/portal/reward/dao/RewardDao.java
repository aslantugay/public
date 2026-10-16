package com.macro.mall.portal.reward.dao;

import com.macro.mall.portal.reward.domain.RewardCoupon;
import com.macro.mall.portal.reward.domain.RewardDoc;
import com.macro.mall.portal.reward.domain.RewardShare;
import com.macro.mall.portal.reward.domain.RewardTxn;
import com.macro.mall.portal.reward.domain.RewardWallet;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface RewardDao {
    RewardWallet selectWalletByMemberId(Long memberId);
    RewardWallet selectWalletById(Long id);
    int insertWallet(RewardWallet wallet);
    int updateBalance(RewardWallet wallet);
    int updateNote(RewardWallet wallet);

    int insertTxn(RewardTxn txn);
    List<RewardTxn> listStatement(@Param("walletId") Long walletId, @Param("keyword") String keyword, @Param("orderBy") String orderBy);
    List<RewardTxn> recentFeed(@Param("limit") int limit);
    List<Map<String, Object>> report(@Param("walletId") Long walletId, @Param("filter") String filter);

    RewardCoupon selectCouponByCode(String code);
    int updateCouponUsed(RewardCoupon coupon);

    RewardShare insertShare(RewardShare share);
    RewardShare selectShareByToken(String token);

    RewardDoc selectDocById(Long id);
}
