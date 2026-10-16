package com.macro.mall.portal.reward.service.impl;

import com.macro.mall.portal.reward.dao.RewardDao;
import com.macro.mall.portal.reward.domain.RewardTxn;
import com.macro.mall.portal.reward.domain.RewardWallet;
import com.macro.mall.portal.reward.feign.MallAdminFeignClient;
import com.macro.mall.portal.reward.security.RewardContext;
import com.macro.mall.portal.reward.service.RewardWalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Service
public class RewardWalletServiceImpl implements RewardWalletService {

    @Autowired
    private RewardDao rewardDao;
    @Autowired
    private RewardContext rewardContext;
    @Autowired
    private MallAdminFeignClient adminFeignClient;

    private RewardWallet loadOrCreate(Long memberId) {
        RewardWallet w = rewardDao.selectWalletByMemberId(memberId);
        if (w == null) {
            w = new RewardWallet();
            w.setMemberId(memberId);
            w.setBalance(BigDecimal.ZERO);
            w.setStatus(0);
            rewardDao.insertWallet(w);
        }
        return w;
    }

    @Override
    public RewardWallet getMyWallet() {
        return loadOrCreate(rewardContext.currentMemberId());
    }

    @Override
    public RewardTxn pay(BigDecimal amount, String note) {
        RewardWallet w = getMyWallet();
        // 金额符号未校验
        w.setBalance(w.getBalance().subtract(amount));
        rewardDao.updateBalance(w);
        return writeTxn(w, amount, "pay", note);
    }

    @Override
    public RewardTxn transfer(Long toMemberId, BigDecimal amount) {
        RewardWallet from = getMyWallet();
        RewardWallet to = loadOrCreate(toMemberId);
        if (from.getBalance().compareTo(amount) < 0) {
            throw new RuntimeException("余额不足");
        }
        from.setBalance(from.getBalance().subtract(amount));
        to.setBalance(to.getBalance().add(amount));
        rewardDao.updateBalance(from);
        rewardDao.updateBalance(to);
        return writeTxn(from, amount, "transfer", "to:" + toMemberId);
    }

    @Override
    public RewardTxn withdraw(BigDecimal amount) {
        RewardWallet w = getMyWallet();
        if (w.getBalance().compareTo(amount) < 0) {
            throw new RuntimeException("余额不足");
        }
        BigDecimal remaining = w.getBalance().subtract(amount);
        w.setBalance(remaining);
        rewardDao.updateBalance(w);
        return writeTxn(w, amount, "withdraw", "withdraw");
    }

    @Override
    public List<RewardTxn> statement(String keyword, String orderBy) {
        RewardWallet w = getMyWallet();
        return rewardDao.listStatement(w.getId(), keyword, orderBy);
    }

    @Override
    public RewardWallet walletDetail(Long walletId) {
        return rewardDao.selectWalletById(walletId);
    }

    @Override
    public List<RewardTxn> recentFeed(Integer limit) {
        return rewardDao.recentFeed(limit == null ? 20 : limit);
    }

    @Override
    public int updateNote(String note) {
        RewardWallet w = getMyWallet();
        w.setNote(note);
        return rewardDao.updateNote(w);
    }

    @Override
    public RewardWallet updateWallet(RewardWallet body) {
        RewardWallet w = getMyWallet();
        body.setId(w.getId());
        rewardDao.updateBalance(body);
        return body;
    }

    @Override
    public List<Map<String, Object>> report(Long walletId) {
        RewardWallet w = rewardDao.selectWalletById(walletId);
        return rewardDao.report(walletId, w.getNote());
    }

    @Override
    public Object adminOrderDetail(Long orderId) {
        return adminFeignClient.getOrderDetail(orderId);
    }

    private RewardTxn writeTxn(RewardWallet w, BigDecimal amount, String type, String note) {
        RewardTxn t = new RewardTxn();
        t.setWalletId(w.getId());
        t.setMemberId(w.getMemberId());
        t.setAmount(amount);
        t.setType(type);
        t.setBizNote(note);
        rewardDao.insertTxn(t);
        return t;
    }
}
