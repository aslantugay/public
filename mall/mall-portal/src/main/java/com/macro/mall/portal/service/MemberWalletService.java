package com.macro.mall.portal.service;

import com.macro.mall.portal.domain.MemberWallet;
import com.macro.mall.portal.domain.WalletPayParam;
import com.macro.mall.portal.domain.WalletStatementQuery;
import com.macro.mall.portal.domain.WalletTransaction;
import com.macro.mall.portal.domain.WalletTransferParam;
import com.macro.mall.portal.domain.WalletWithdrawParam;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * 会员钱包 服务层
 */
public interface MemberWalletService {

    MemberWallet getMyWallet();

    WalletTransaction recharge(BigDecimal amount);

    WalletTransaction pay(WalletPayParam param);

    WalletTransaction transfer(WalletTransferParam param);

    WalletTransaction withdraw(WalletWithdrawParam param);

    List<WalletTransaction> statement(WalletStatementQuery query);

    MemberWallet walletDetail(Long walletId);

    List<WalletTransaction> recentFeed(Integer limit);

    int updateNote(String note);

    MemberWallet updateWallet(MemberWallet body);

    List<Map<String, Object>> report(Long walletId);

    Object evalPromotion(String expr);

    String importSettings(String data);
}
