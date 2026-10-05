package com.macro.mall.portal.domain;

import lombok.Data;

@Data
public class GiftCardRedeemParam {
    private String cardNo;
    private String cardSecret;
}
