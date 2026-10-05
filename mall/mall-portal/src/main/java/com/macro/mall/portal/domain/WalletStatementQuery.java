package com.macro.mall.portal.domain;

import lombok.Data;

/**
 * 对账单查询条件（高级检索）。
 */
@Data
public class WalletStatementQuery {
    private Long walletId;
    private String type;
    private String keyword;
    /** 排序字段，默认 create_time desc */
    private String orderBy = "create_time desc";
}
