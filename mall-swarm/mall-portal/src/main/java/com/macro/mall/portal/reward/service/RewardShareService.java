package com.macro.mall.portal.reward.service;

import com.macro.mall.portal.reward.domain.RewardDoc;
import com.macro.mall.portal.reward.domain.RewardShare;

public interface RewardShareService {
    RewardShare createShare(Long docId);
    RewardDoc resolveShared(String token);
}
