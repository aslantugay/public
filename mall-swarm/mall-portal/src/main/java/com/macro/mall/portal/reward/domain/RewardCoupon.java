package com.macro.mall.portal.reward.domain;

import lombok.Data;
import java.math.BigDecimal;

/** 一次性充值券 */
@Data
public class RewardCoupon {
    private Long id;
    private String code;
    private BigDecimal amount;
    /** 0->未使用；1->已使用 */
    private Integer used;
    private Long ownerMemberId;
}
