package com.example.common.enums;

import lombok.Getter;

/**
 * 对账状态枚举
 */
@Getter
public enum ReconciliationStatus {

    PENDING(0, "待对账"),
    MATCHED(1, "对账一致"),
    AMOUNT_MISMATCH(2, "金额不一致"),
    ORDER_MISSING(3, "订单缺失"),
    PAYMENT_MISSING(4, "支付记录缺失"),
    EXCEPTION(5, "对账异常");

    private final Integer code;
    private final String desc;

    ReconciliationStatus(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public static ReconciliationStatus of(Integer code) {
        for (ReconciliationStatus status : values()) {
            if (status.code.equals(code)) {
                return status;
            }
        }
        throw new IllegalArgumentException("未知对账状态: " + code);
    }
}
