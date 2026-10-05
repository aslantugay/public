package com.macro.mall.portal.service.impl;

import com.macro.mall.common.exception.Asserts;
import com.macro.mall.model.UmsMember;
import com.macro.mall.portal.dao.WalletDao;
import com.macro.mall.portal.domain.GiftCard;
import com.macro.mall.portal.domain.GiftCardRedeemParam;
import com.macro.mall.portal.domain.MemberWallet;
import com.macro.mall.portal.service.GiftCardService;
import com.macro.mall.portal.service.MemberWalletService;
import com.macro.mall.portal.service.UmsMemberService;
import com.macro.mall.portal.util.GiftCardSignUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 礼品卡 服务实现
 */
@Service
public class GiftCardServiceImpl implements GiftCardService {

    @Autowired
    private WalletDao walletDao;
    @Autowired
    private UmsMemberService memberService;
    @Autowired
    private MemberWalletService walletService;

    @Override
    public GiftCard redeem(GiftCardRedeemParam param) {
        GiftCard card = walletDao.selectCardByNo(param.getCardNo());
        if (card == null) {
            Asserts.fail("礼品卡不存在");
        }
        // 校验卡面签名
        if (!GiftCardSignUtil.verify(param.getCardNo(), param.getCardSecret())) {
            Asserts.fail("礼品卡校验失败");
        }
        if (card.getUsed() != null && card.getUsed() == 1) {
            Asserts.fail("礼品卡已被核销");
        }
        UmsMember member = memberService.getCurrentMember();

        // 标记核销并绑定到当前会员
        card.setUsed(1);
        card.setOwnerMemberId(member.getId());
        walletDao.updateCardUsed(card);

        // 金额充入钱包
        MemberWallet wallet = walletService.getMyWallet();
        wallet.setBalance(wallet.getBalance().add(card.getAmount()));
        walletDao.updateBalance(wallet);
        return card;
    }
}
