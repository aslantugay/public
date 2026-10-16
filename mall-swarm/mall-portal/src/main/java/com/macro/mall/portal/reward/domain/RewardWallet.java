package com.macro.mall.portal.reward.domain;

import lombok.Data;
import java.math.BigDecimal;

/** 会员积分钱包 */
@Data
public class RewardWallet {
    private Long id;
    private Long memberId;
    private BigDecimal balance;
    private Integer status;
    /** 钱包备注（对账单页头展示） */
    private String note;
}
