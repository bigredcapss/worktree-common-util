package com.yourcompany.common;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

/**
 * 金额精度与展示工具类。
 * <p>
 * 分转元使用 {@link BigDecimal} 避免浮点误差；金额展示使用固定英文区域千分位格式，符合常见账务展示习惯。
 * </p>
 *
 * @author yourcompany
 */
public final class AmountUtils {

    private static final BigDecimal ONE_HUNDRED = new BigDecimal("100");

    /**
     * 空分金额对应的元（保留两位小数）。
     */
    private static final BigDecimal ZERO_YUAN = BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP);

    /**
     * 格式化异常时的兜底展示（无千分位，避免再次触发格式化逻辑）。
     */
    private static final String FALLBACK_FORMATTED_ZERO = "0.00";

    private AmountUtils() {
    }

    /**
     * 将「分」转为「元」，保留 2 位小数，四舍五入（HALF_UP）。
     * <p>
     * {@code null} 视为 0 分，返回 {@code 0.00}。
     * </p>
     *
     * @param fen 以分为单位的金额，可为 {@code null}
     * @return 以元为单位的金额，标度为 2
     */
    public static BigDecimal fenToYuan(Long fen) {
        if (fen == null) {
            return ZERO_YUAN;
        }
        return BigDecimal.valueOf(fen)
                .divide(ONE_HUNDRED, 2, RoundingMode.HALF_UP);
    }

    /**
     * 将金额格式化为带千分位的字符串，固定保留 2 位小数（如 {@code 12345.67} → {@code "12,345.67"}）。
     * <p>
     * {@code null} 按 {@code 0.00} 处理。若格式化过程中发生 {@link NumberFormatException}（如扩展场景下数值/模式不合法），
     * 返回 {@code "0.00"}，避免向调用方抛出非受检异常。
     * </p>
     *
     * @param amount 金额，可为 {@code null}
     * @return 千分位格式字符串，始终包含两位小数
     */
    public static String formatAmount(BigDecimal amount) {
        BigDecimal safe = amount == null ? ZERO_YUAN : amount;
        try {
            DecimalFormatSymbols symbols = DecimalFormatSymbols.getInstance(Locale.US);
            DecimalFormat formatter = new DecimalFormat("#,##0.00", symbols);
            formatter.setRoundingMode(RoundingMode.HALF_UP);
            return formatter.format(safe);
        } catch (NumberFormatException e) {
            return FALLBACK_FORMATTED_ZERO;
        }
    }
}
