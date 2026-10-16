package com.macro.mall.portal.reward.service;

import com.macro.mall.portal.reward.domain.RewardTxn;
import com.macro.mall.portal.reward.domain.RewardWallet;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public interface RewardWalletService {
    RewardWallet getMyWallet();
    RewardTxn pay(BigDecimal amount, String note);
    RewardTxn transfer(Long toMemberId, BigDecimal amount);
    RewardTxn withdraw(BigDecimal amount);
    List<RewardTxn> statement(String keyword, String orderBy);
    RewardWallet walletDetail(Long walletId);
    List<RewardTxn> recentFeed(Integer limit);
    int updateNote(String note);
    RewardWallet updateWallet(RewardWallet body);
    List<Map<String, Object>> report(Long walletId);
    Object adminOrderDetail(Long orderId);
}
