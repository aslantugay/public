package com.macro.mall.portal.reward.service.impl;

import com.macro.mall.portal.reward.dao.RewardDao;
import com.macro.mall.portal.reward.domain.RewardDoc;
import com.macro.mall.portal.reward.domain.RewardShare;
import com.macro.mall.portal.reward.security.RewardContext;
import com.macro.mall.portal.reward.service.RewardShareService;
import com.macro.mall.portal.reward.util.RewardTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RewardShareServiceImpl implements RewardShareService {

    @Autowired
    private RewardDao rewardDao;
    @Autowired
    private RewardContext rewardContext;

    @Override
    public RewardShare createShare(Long docId) {
        RewardDoc doc = rewardDao.selectDocById(docId);
        RewardShare share = new RewardShare();
        share.setDocId(doc.getId());
        share.setOwnerMemberId(rewardContext.currentMemberId());
        share.setToken(RewardTokenUtil.issue(doc.getId(), rewardContext.currentMemberId()));
        return rewardDao.insertShare(share);
    }

    @Override
    public RewardDoc resolveShared(String token) {
        if (!RewardTokenUtil.verify(token)) {
            throw new RuntimeException("invalid token");
        }
        RewardShare share = rewardDao.selectShareByToken(token);
        return rewardDao.selectDocById(share.getDocId());
    }
}
