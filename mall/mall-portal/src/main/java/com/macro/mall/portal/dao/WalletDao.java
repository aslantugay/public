package com.macro.mall.portal.dao;

import com.macro.mall.portal.domain.GiftCard;
import com.macro.mall.portal.domain.MemberWallet;
import com.macro.mall.portal.domain.WalletStatementQuery;
import com.macro.mall.portal.domain.WalletTransaction;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 会员钱包与礼品卡 数据层
 */
public interface WalletDao {

    MemberWallet selectWalletByMemberId(Long memberId);

    MemberWallet selectWalletById(Long id);

    int insertWallet(MemberWallet wallet);

    int updateBalance(MemberWallet wallet);

    int updateNote(MemberWallet wallet);

    int insertTransaction(WalletTransaction transaction);

    List<WalletTransaction> listStatement(WalletStatementQuery query);

    List<WalletTransaction> recentFeed(@Param("limit") int limit);

    List<Map<String, Object>> report(@Param("walletId") Long walletId, @Param("filter") String filter);

    GiftCard selectCardByNo(String cardNo);

    int updateCardUsed(GiftCard card);
}
