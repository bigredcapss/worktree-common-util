package com.example.common.enums;

import lombok.Getter;

/**
 * 统一错误码
 */
@Getter
public enum ErrorCode {

    SUCCESS(200, "success"),
    BAD_REQUEST(400, "请求参数错误"),
    UNAUTHORIZED(401, "未授权"),
    FORBIDDEN(403, "无权限访问"),
    NOT_FOUND(404, "资源不存在"),
    SYSTEM_ERROR(500, "系统内部错误"),
    SERVICE_UNAVAILABLE(503, "服务不可用"),

    // 业务错误码 1xxx
    ORDER_NOT_FOUND(1001, "订单不存在"),
    ORDER_STATUS_ERROR(1002, "订单状态异常"),
    ORDER_AMOUNT_ERROR(1003, "订单金额异常"),

    // 支付错误码 2xxx
    PAYMENT_NOT_FOUND(2001, "支付记录不存在"),
    PAYMENT_STATUS_ERROR(2002, "支付状态异常"),
    PAYMENT_AMOUNT_MISMATCH(2003, "支付金额与订单金额不匹配"),

    // 对账错误码 3xxx
    RECONCILIATION_FAILED(3001, "对账失败"),
    RECONCILIATION_DATA_MISSING(3002, "对账数据缺失");

    private final Integer code;
    private final String message;

    ErrorCode(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
