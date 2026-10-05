package com.macro.mall.portal.domain;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class WalletPayParam {
    private BigDecimal amount;
    private Long orderId;
    private String note;
}
