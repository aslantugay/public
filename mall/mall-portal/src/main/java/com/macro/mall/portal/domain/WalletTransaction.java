package com.macro.mall.portal.domain;

import lombok.Data;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 钱包流水
 */
@Data
public class WalletTransaction {
    private Long id;
    private Long walletId;
    private Long memberId;
    private BigDecimal amount;
    /** 类型：recharge/pay/transfer/withdraw/redeem */
    private String type;
    private String bizNote;
    private Date createTime;
}
