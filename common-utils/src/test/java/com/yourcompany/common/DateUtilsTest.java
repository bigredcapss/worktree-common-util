package com.yourcompany.common;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

/**
 * {@link DateUtils} 单元测试。
 */
class DateUtilsTest {

    private static final ZoneId ZONE = ZoneId.of(DateUtils.DEFAULT_TIMEZONE);

    @Nested
    @DisplayName("LocalDateTime / LocalDate ↔ 字符串")
    class JavaTimeTests {

        @Test
        @DisplayName("formatDateTime / parseDateTime 互逆")
        void dateTimeRoundTrip() {
            LocalDateTime ldt = LocalDateTime.of(2025, 3, 23, 14, 30, 0);
            String s = DateUtils.formatDateTime(ldt);
            assertEquals("2025-03-23 14:30:00", s);
            assertEquals(ldt, DateUtils.parseDateTime(s));
        }

        @Test
        @DisplayName("formatDate / parseDate 互逆")
        void dateRoundTrip() {
            LocalDate d = LocalDate.of(2025, 3, 23);
            String s = DateUtils.formatDate(d);
            assertEquals("2025-03-23", s);
            assertEquals(d, DateUtils.parseDate(s));
        }

        @Test
        @DisplayName("null / 空串 返回 null")
        void nullSafe() {
            assertNull(DateUtils.formatDateTime((LocalDateTime) null));
            assertNull(DateUtils.formatDate((LocalDate) null));
            assertNull(DateUtils.parseDateTime(null));
            assertNull(DateUtils.parseDateTime(""));
            assertNull(DateUtils.parseDate(null));
            assertNull(DateUtils.parseDate(""));
        }
    }

    @Nested
    @DisplayName("java.util.Date ↔ 字符串")
    class UtilDateTests {

        @Test
        @DisplayName("formatDateTime(Date) / parseToUtilDateTime 互逆")
        void dateTimeRoundTrip() {
            LocalDateTime ldt = LocalDateTime.of(2025, 3, 23, 14, 30, 0);
            Date date = Date.from(ldt.atZone(ZONE).toInstant());
            String s = DateUtils.formatDateTime(date);
            assertEquals("2025-03-23 14:30:00", s);
            Date back = DateUtils.parseToUtilDateTime(s);
            assertEquals(date.getTime(), back.getTime());
        }

        @Test
        @DisplayName("formatUtilDate / parseToUtilDate 互逆")
        void dateOnlyRoundTrip() {
            LocalDate ld = LocalDate.of(2025, 3, 23);
            Date date = Date.from(ld.atStartOfDay(ZONE).toInstant());
            String s = DateUtils.formatUtilDate(date);
            assertEquals("2025-03-23", s);
            Date back = DateUtils.parseToUtilDate(s);
            assertEquals(date.getTime(), back.getTime());
        }

        @Test
        @DisplayName("Date 入参为 null 时返回 null")
        void nullDate() {
            assertNull(DateUtils.formatDateTime((Date) null));
            assertNull(DateUtils.formatUtilDate(null));
        }
    }
}
