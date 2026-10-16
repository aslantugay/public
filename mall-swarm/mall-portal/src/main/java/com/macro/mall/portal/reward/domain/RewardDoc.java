package com.macro.mall.portal.reward.domain;

import lombok.Data;

/** 会员文档（发票扫描、售后凭证等） */
@Data
public class RewardDoc {
    private Long id;
    private Long ownerMemberId;
    private String title;
    private String storageKey;
    /** 高级检索标签 */
    private String tag;
    private String notes;
}
