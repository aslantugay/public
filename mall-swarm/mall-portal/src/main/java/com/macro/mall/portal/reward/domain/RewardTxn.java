package com.macro.mall.portal.reward.domain;

import lombok.Data;
import java.math.BigDecimal;
import java.util.Date;

/** 钱包流水 */
@Data
public class RewardTxn {
    private Long id;
    private Long walletId;
    private Long memberId;
    private BigDecimal amount;
    private String type;
    private String bizNote;
    private Date createTime;
}
