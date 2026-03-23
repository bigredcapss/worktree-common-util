package com.yourcompany.common;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * 日期与字符串互转工具（默认时区 {@value #DEFAULT_TIMEZONE}，日期时间格式 {@value #DATETIME_FORMAT}，日期格式 {@value #DATE_FORMAT}）。
 */
public final class DateUtils {

    public static final String DEFAULT_TIMEZONE = "Asia/Shanghai";
    public static final String DATETIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
    public static final String DATE_FORMAT = "yyyy-MM-dd";

    private static final DateTimeFormatter DATETIME_FORMATTER =
            DateTimeFormatter.ofPattern(DATETIME_FORMAT);
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern(DATE_FORMAT);
    private static final ZoneId DEFAULT_ZONE = ZoneId.of(DEFAULT_TIMEZONE);

    private DateUtils() {}

    public static String formatDateTime(LocalDateTime dateTime) {
        if (dateTime == null) {
            return null;
        }
        return dateTime.format(DATETIME_FORMATTER);
    }

    public static String formatDate(LocalDate date) {
        if (date == null) {
            return null;
        }
        return date.format(DATE_FORMATTER);
    }

    public static LocalDateTime parseDateTime(String dateTimeStr) {
        if (dateTimeStr == null || dateTimeStr.isEmpty()) {
            return null;
        }
        return LocalDateTime.parse(dateTimeStr, DATETIME_FORMATTER);
    }

    public static LocalDate parseDate(String dateStr) {
        if (dateStr == null || dateStr.isEmpty()) {
            return null;
        }
        return LocalDate.parse(dateStr, DATE_FORMATTER);
    }

    /** {@link Date} → 日期时间字符串，按默认时区解释瞬时时刻 */
    public static String formatDateTime(Date date) {
        if (date == null) {
            return null;
        }
        return formatDateTime(LocalDateTime.ofInstant(date.toInstant(), DEFAULT_ZONE));
    }

    /**
     * {@link Date} → 日期字符串，按默认时区取日历日。
     * <p>与 {@link #formatDate(LocalDate)} 区分，避免 {@code null} 参数重载歧义。</p>
     */
    public static String formatUtilDate(Date date) {
        if (date == null) {
            return null;
        }
        return formatDate(date.toInstant().atZone(DEFAULT_ZONE).toLocalDate());
    }

    /** 日期时间字符串 → {@link Date}，按默认时区对齐到该本地时间 */
    public static Date parseToUtilDateTime(String dateTimeStr) {
        LocalDateTime ldt = parseDateTime(dateTimeStr);
        if (ldt == null) {
            return null;
        }
        return Date.from(ldt.atZone(DEFAULT_ZONE).toInstant());
    }

    /** 日期字符串 → {@link Date}（当日 00:00:00，默认时区） */
    public static Date parseToUtilDate(String dateStr) {
        LocalDate ld = parseDate(dateStr);
        if (ld == null) {
            return null;
        }
        return Date.from(ld.atStartOfDay(DEFAULT_ZONE).toInstant());
    }

    /** 获取某日的开始时间（00:00:00） */
    public static LocalDateTime startOfDay(LocalDate date) {
        return date.atStartOfDay();
    }

    /** 获取某日的结束时间（23:59:59） */
    public static LocalDateTime endOfDay(LocalDate date) {
        return date.atTime(23, 59, 59);
    }
}
