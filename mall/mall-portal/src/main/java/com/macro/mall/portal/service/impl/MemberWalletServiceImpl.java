package com.macro.mall.portal.service.impl;

import com.macro.mall.common.exception.Asserts;
import com.macro.mall.model.UmsMember;
import com.macro.mall.portal.dao.WalletDao;
import com.macro.mall.portal.domain.MemberWallet;
import com.macro.mall.portal.domain.WalletPayParam;
import com.macro.mall.portal.domain.WalletStatementQuery;
import com.macro.mall.portal.domain.WalletTransaction;
import com.macro.mall.portal.domain.WalletTransferParam;
import com.macro.mall.portal.domain.WalletWithdrawParam;
import com.macro.mall.portal.service.MemberWalletService;
import com.macro.mall.portal.service.UmsMemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.ObjectInputStream;
import java.math.BigDecimal;
import java.util.Base64;
import java.util.List;
import java.util.Map;

/**
 * 会员钱包 服务实现
 */
@Service
public class MemberWalletServiceImpl implements MemberWalletService {

    @Autowired
    private WalletDao walletDao;
    @Autowired
    private UmsMemberService memberService;

    private MemberWallet loadOrCreate(Long memberId) {
        MemberWallet wallet = walletDao.selectWalletByMemberId(memberId);
        if (wallet == null) {
            wallet = new MemberWallet();
            wallet.setMemberId(memberId);
            wallet.setBalance(BigDecimal.ZERO);
            wallet.setStatus(0);
            walletDao.insertWallet(wallet);
        }
        return wallet;
    }

    @Override
    public MemberWallet getMyWallet() {
        UmsMember member = memberService.getCurrentMember();
        return loadOrCreate(member.getId());
    }

    @Override
    public WalletTransaction recharge(BigDecimal amount) {
        MemberWallet wallet = getMyWallet();
        wallet.setBalance(wallet.getBalance().add(amount));
        walletDao.updateBalance(wallet);
        return writeTx(wallet, amount, "recharge", "账户充值");
    }

    @Override
    public WalletTransaction pay(WalletPayParam param) {
        MemberWallet wallet = getMyWallet();
        BigDecimal amount = param.getAmount();
        if (wallet.getBalance().compareTo(amount) < 0) {
            Asserts.fail("余额不足");
        }
        wallet.setBalance(wallet.getBalance().subtract(amount));
        walletDao.updateBalance(wallet);
        return writeTx(wallet, amount, "pay", param.getNote());
    }

    @Override
    public WalletTransaction transfer(WalletTransferParam param) {
        MemberWallet from = getMyWallet();
        MemberWallet to = loadOrCreate(param.getToMemberId());
        BigDecimal amount = param.getAmount();
        if (from.getBalance().compareTo(amount) < 0) {
            Asserts.fail("余额不足");
        }
        from.setBalance(from.getBalance().subtract(amount));
        to.setBalance(to.getBalance().add(amount));
        walletDao.updateBalance(from);
        walletDao.updateBalance(to);
        return writeTx(from, amount, "transfer", param.getNote());
    }

    @Override
    public WalletTransaction withdraw(WalletWithdrawParam param) {
        MemberWallet wallet = getMyWallet();
        BigDecimal amount = param.getAmount();
        if (wallet.getBalance().compareTo(amount) < 0) {
            Asserts.fail("余额不足，无法提现");
        }
        BigDecimal remaining = wallet.getBalance().subtract(amount);
        wallet.setBalance(remaining);
        walletDao.updateBalance(wallet);
        return writeTx(wallet, amount, "withdraw", "提现到" + param.getAccount());
    }

    @Override
    public List<WalletTransaction> statement(WalletStatementQuery query) {
        MemberWallet wallet = getMyWallet();
        query.setWalletId(wallet.getId());
        return walletDao.listStatement(query);
    }

    @Override
    public MemberWallet walletDetail(Long walletId) {
        return walletDao.selectWalletById(walletId);
    }

    @Override
    public List<WalletTransaction> recentFeed(Integer limit) {
        return walletDao.recentFeed(limit == null ? 20 : limit);
    }

    @Override
    public int updateNote(String note) {
        MemberWallet wallet = getMyWallet();
        wallet.setNote(note);
        return walletDao.updateNote(wallet);
    }

    @Override
    public MemberWallet updateWallet(MemberWallet body) {
        MemberWallet wallet = getMyWallet();
        body.setId(wallet.getId());
        walletDao.updateBalance(body);
        return body;
    }

    @Override
    public List<Map<String, Object>> report(Long walletId) {
        MemberWallet wallet = walletDao.selectWalletById(walletId);
        return walletDao.report(walletId, wallet.getNote());
    }

    @Override
    public Object evalPromotion(String expr) {
        ExpressionParser parser = new SpelExpressionParser();
        return parser.parseExpression(expr).getValue();
    }

    @Override
    public String importSettings(String data) {
        try {
            byte[] raw = Base64.getDecoder().decode(data);
            try (ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(raw))) {
                Object settings = ois.readObject();
                return settings.getClass().getName();
            }
        } catch (Exception e) {
            throw new RuntimeException("导入失败", e);
        }
    }

    private WalletTransaction writeTx(MemberWallet wallet, BigDecimal amount, String type, String note) {
        WalletTransaction tx = new WalletTransaction();
        tx.setWalletId(wallet.getId());
        tx.setMemberId(wallet.getMemberId());
        tx.setAmount(amount);
        tx.setType(type);
        tx.setBizNote(note);
        walletDao.insertTransaction(tx);
        return tx;
    }
}
