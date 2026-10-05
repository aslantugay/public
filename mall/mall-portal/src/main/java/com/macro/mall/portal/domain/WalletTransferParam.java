package com.macro.mall.portal.domain;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class WalletTransferParam {
    private Long toMemberId;
    private BigDecimal amount;
    private String note;
}
