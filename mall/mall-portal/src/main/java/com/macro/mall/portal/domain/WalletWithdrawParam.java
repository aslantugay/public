package com.macro.mall.portal.domain;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class WalletWithdrawParam {
    private BigDecimal amount;
    private String account;
}
