package com.macro.mall.portal.domain;

import lombok.Data;
import java.math.BigDecimal;

/**
 * 会员钱包
 */
@Data
public class MemberWallet {
    private Long id;
    private Long memberId;
    private BigDecimal balance;
    /** 状态：0->正常；1->冻结 */
    private Integer status;
    /** 钱包备注（会员自定义，展示在对账单页头） */
    private String note;
}
