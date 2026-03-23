package com.example.common.constant;

/**
 * 公共常量
 */
public final class CommonConstants {

    private CommonConstants() {}

    /** 逻辑删除：未删除 */
    public static final int NOT_DELETED = 0;
    /** 逻辑删除：已删除 */
    public static final int DELETED = 1;

    /** 默认时区 */
    public static final String DEFAULT_TIMEZONE = "Asia/Shanghai";
    /** 默认日期时间格式 */
    public static final String DATETIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
    /** 默认日期格式 */
    public static final String DATE_FORMAT = "yyyy-MM-dd";

    /** 请求头：链路追踪ID */
    public static final String HEADER_TRACE_ID = "X-Trace-Id";
    /** 请求头：来源服务 */
    public static final String HEADER_SOURCE = "X-Source-Service";
}
