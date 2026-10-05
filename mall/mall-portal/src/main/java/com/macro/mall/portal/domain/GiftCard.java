package com.macro.mall.portal.domain;

import lombok.Data;
import java.math.BigDecimal;

/**
 * 礼品卡
 */
@Data
public class GiftCard {
    private Long id;
    private String cardNo;
    /** 卡面签名，核销时校验 */
    private String cardSign;
    private BigDecimal amount;
    /** 绑定会员（为空表示未绑定） */
    private Long ownerMemberId;
    /** 是否已核销：0->未核销；1->已核销 */
    private Integer used;
    private String label;
}
