package com.macro.mall.portal.reward.domain;

import lombok.Data;

/** 文档分享凭证 */
@Data
public class RewardShare {
    private Long id;
    private Long docId;
    private String token;
    private Long ownerMemberId;
}
