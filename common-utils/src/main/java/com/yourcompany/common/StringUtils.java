package com.yourcompany.common;

import java.util.Iterator;

/**
 * 字符串常用处理工具（无第三方依赖）。
 */
public final class StringUtils {

    private static final String DEFAULT_ELLIPSIS = "...";

    private StringUtils() {
    }

    /**
     * {@code null}、空串或仅空白字符时视为 blank。
     */
    public static boolean isBlank(String s) {
        if (s == null || s.isEmpty()) {
            return true;
        }
        for (int i = 0; i < s.length(); i++) {
            if (!Character.isWhitespace(s.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    public static boolean isNotBlank(String s) {
        return !isBlank(s);
    }

    /**
     * 去除首尾空白；若结果为空则返回 {@code null}。
     */
    public static String trimToNull(String s) {
        if (s == null) {
            return null;
        }
        String t = s.trim();
        return t.isEmpty() ? null : t;
    }

    /**
     * {@code null} 转为空串。
     */
    public static String nullToEmpty(String s) {
        return s == null ? "" : s;
    }

    /**
     * 若为 blank 则返回默认值，否则返回原串（不自动 trim）。
     */
    public static String defaultIfBlank(String s, String defaultValue) {
        return isBlank(s) ? defaultValue : s;
    }

    /**
     * 若为 {@code null} 或空串则返回默认值。
     */
    public static String defaultIfEmpty(String s, String defaultValue) {
        return s == null || s.isEmpty() ? defaultValue : s;
    }

    /**
     * 空安全相等：双方均为 {@code null} 时视为相等。
     */
    public static boolean equals(String a, String b) {
        if (a == null) {
            return b == null;
        }
        return a.equals(b);
    }

    /**
     * 空安全忽略大小写相等。
     */
    public static boolean equalsIgnoreCase(String a, String b) {
        if (a == null) {
            return b == null;
        }
        return a.equalsIgnoreCase(b);
    }

    /**
     * 按最大字符数截断（不追加省略号）。{@code maxLen &lt;= 0} 时返回空串。
     */
    public static String truncate(String s, int maxLen) {
        if (s == null) {
            return null;
        }
        if (maxLen <= 0) {
            return "";
        }
        return s.length() <= maxLen ? s : s.substring(0, maxLen);
    }

    /**
     * 截断并在末尾追加省略号（默认 {@code ...}）。总长不超过 {@code maxLen}（含省略号）。
     */
    public static String truncateWithEllipsis(String s, int maxLen) {
        return truncateWithEllipsis(s, maxLen, DEFAULT_ELLIPSIS);
    }

    public static String truncateWithEllipsis(String s, int maxLen, String ellipsis) {
        if (s == null) {
            return null;
        }
        if (maxLen <= 0) {
            return "";
        }
        if (ellipsis == null) {
            ellipsis = "";
        }
        if (s.length() <= maxLen) {
            return s;
        }
        int el = ellipsis.length();
        if (maxLen <= el) {
            return ellipsis.substring(0, maxLen);
        }
        return s.substring(0, maxLen - el) + ellipsis;
    }

    /**
     * 使用分隔符连接非 {@code null} 元素；跳过 {@code null}。
     */
    public static String join(String delimiter, Iterable<?> parts) {
        if (delimiter == null) {
            delimiter = "";
        }
        if (parts == null) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        Iterator<?> it = parts.iterator();
        while (it.hasNext()) {
            Object o = it.next();
            if (o == null) {
                continue;
            }
            if (sb.length() > 0) {
                sb.append(delimiter);
            }
            sb.append(o);
        }
        return sb.toString();
    }

    public static String join(String delimiter, String... parts) {
        if (delimiter == null) {
            delimiter = "";
        }
        if (parts == null || parts.length == 0) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        for (String p : parts) {
            if (p == null) {
                continue;
            }
            if (sb.length() > 0) {
                sb.append(delimiter);
            }
            sb.append(p);
        }
        return sb.toString();
    }
}
