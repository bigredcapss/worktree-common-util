package com.example.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 基于正则的 HTTP(S) 网址匹配与提取。
 * <p>
 * 提供两类能力：整段字符串是否为网址（较严格），以及从任意文本中连续扫描提取网址（较宽松）。
 */
public final class UrlRegexUtils {

    /**
     * 整串匹配：常见域名、localhost、IPv4，可选端口与路径/query/fragment。
     * 不覆盖 IPv6、国际化域名 punycode 等边界场景。
     */
    private static final Pattern HTTP_URL_STRICT = Pattern.compile(
            "(?i)^https?://"
                    + "(?:"
                    + "(?:[a-z0-9](?:[a-z0-9\\-]{0,61}[a-z0-9])?\\.)+[a-z]{2,}"
                    + "|localhost"
                    + "|(?:\\d{1,3}\\.){3}\\d{1,3}"
                    + ")"
                    + "(?::\\d{1,5})?"
                    + "(?:/[\\w\\-./?#&=%+~:@]*)?"
                    + "$"
    );

    /**
     * 在文本中查找：从 http(s) 起至空白或明显非法字符前（宽松，便于高亮/替换）。
     */
    private static final Pattern HTTP_URL_IN_TEXT = Pattern.compile(
            "https?://[\\w\\-._~:/?#\\[\\]@!$&'()*+,;=%]+",
            Pattern.CASE_INSENSITIVE
    );

    private UrlRegexUtils() {
    }

    /**
     * @return 是否为非空且整串符合 HTTP(S) 网址形态
     */
    public static boolean isHttpUrl(String input) {
        if (input == null) {
            return false;
        }
        String s = input.trim();
        return !s.isEmpty() && HTTP_URL_STRICT.matcher(s).matches();
    }

    /**
     * @return 文本中是否至少包含一段 HTTP(S) 网址子串
     */
    public static boolean containsHttpUrl(String text) {
        if (text == null || text.isEmpty()) {
            return false;
        }
        return HTTP_URL_IN_TEXT.matcher(text).find();
    }

    /**
     * 从文本中提取所有匹配的 HTTP(S) 网址（按出现顺序，可能重叠边界由正则决定，一般不重叠）。
     */
    public static List<String> findAllHttpUrls(String text) {
        if (text == null || text.isEmpty()) {
            return Collections.emptyList();
        }
        Matcher m = HTTP_URL_IN_TEXT.matcher(text);
        List<String> out = new ArrayList<>();
        while (m.find()) {
            out.add(m.group());
        }
        return out;
    }

    /**
     * 返回宽松扫描用的 {@link Pattern}，便于调用方自定义 {@link Matcher}。
     */
    public static Pattern httpUrlInTextPattern() {
        return HTTP_URL_IN_TEXT;
    }

    /**
     * 返回整串校验用的 {@link Pattern}。
     */
    public static Pattern httpUrlStrictPattern() {
        return HTTP_URL_STRICT;
    }
}
