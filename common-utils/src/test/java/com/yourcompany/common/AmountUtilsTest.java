package com.yourcompany.common;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

/**
 * {@link AmountUtils} 单元测试。
 */
class AmountUtilsTest {

    @Nested
    @DisplayName("fenToYuan")
    class FenToYuanTests {

        @Test
        @DisplayName("null 返回 0.00")
        void nullReturnsZeroYuan() {
            assertEquals(new BigDecimal("0.00"), AmountUtils.fenToYuan(null));
        }

        @Test
        @DisplayName("0 分 -> 0.00")
        void zeroFen() {
            assertEquals(new BigDecimal("0.00"), AmountUtils.fenToYuan(0L));
        }

        @Test
        @DisplayName("普通换算：12345 分 -> 123.45 元")
        void typicalConversion() {
            assertEquals(new BigDecimal("123.45"), AmountUtils.fenToYuan(12345L));
        }

        @Test
        @DisplayName("四舍五入到分：333 分 -> 3.33 元")
        void scaleTwo() {
            assertEquals(new BigDecimal("3.33"), AmountUtils.fenToYuan(333L));
        }

        @Test
        @DisplayName("负数分")
        void negativeFen() {
            assertEquals(new BigDecimal("-12.34"), AmountUtils.fenToYuan(-1234L));
        }
    }

    @Nested
    @DisplayName("formatAmount")
    class FormatAmountTests {

        @Test
        @DisplayName("null 按 0.00 展示")
        void nullAsZero() {
            assertEquals("0.00", AmountUtils.formatAmount(null));
        }

        @Test
        @DisplayName("千分位与小数：12345.67 -> 12,345.67")
        void thousandsSeparator() {
            assertEquals("12,345.67", AmountUtils.formatAmount(new BigDecimal("12345.67")));
        }

        @Test
        @DisplayName("整数元补全两位小数")
        void integerYuan() {
            assertEquals("1,000.00", AmountUtils.formatAmount(new BigDecimal("1000")));
        }

        @Test
        @DisplayName("零")
        void zero() {
            assertEquals("0.00", AmountUtils.formatAmount(BigDecimal.ZERO));
        }

        @Test
        @DisplayName("负数带千分位")
        void negative() {
            assertEquals("-1,234.50", AmountUtils.formatAmount(new BigDecimal("-1234.5")));
        }
    }
}
