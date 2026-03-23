package com.yourcompany.common;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 正则匹配与替换工具（基于 {@link java.util.regex}，无第三方依赖）。
 */
public final class RegexUtils {

    private RegexUtils() {
    }

    /**
     * 整段文本是否完全匹配该正则（等价于 {@link Pattern#matches(String, CharSequence)}）。
     */
    public static boolean matches(String input, String regex) {
        if (input == null || regex == null) {
            return false;
        }
        return Pattern.matches(regex, input);
    }

    /**
     * 整段文本是否完全匹配已编译的 {@link Pattern}。
     */
    public static boolean matches(String input, Pattern pattern) {
        if (input == null || pattern == null) {
            return false;
        }
        return pattern.matcher(input).matches();
    }

    /**
     * 文本中是否包含与正则匹配的子串。
     */
    public static boolean contains(String input, String regex) {
        if (input == null || regex == null) {
            return false;
        }
        return Pattern.compile(regex).matcher(input).find();
    }

    /**
     * 文本中是否包含与已编译模式匹配的子串。
     */
    public static boolean contains(String input, Pattern pattern) {
        if (input == null || pattern == null) {
            return false;
        }
        return pattern.matcher(input).find();
    }

    /**
     * 返回第一个匹配的子串；无匹配或入参为 {@code null} 时返回 {@code null}。
     */
    public static String findFirst(String input, String regex) {
        if (input == null || regex == null) {
            return null;
        }
        Matcher m = Pattern.compile(regex).matcher(input);
        return m.find() ? m.group() : null;
    }

    /**
     * 返回第一个匹配的子串（使用已编译 {@link Pattern}）。
     */
    public static String findFirst(String input, Pattern pattern) {
        if (input == null || pattern == null) {
            return null;
        }
        Matcher m = pattern.matcher(input);
        return m.find() ? m.group() : null;
    }

    /**
     * 返回所有非重叠匹配子串；{@code input} 或 {@code regex} 为 {@code null} 时返回空列表。
     */
    public static List<String> findAll(String input, String regex) {
        if (input == null || regex == null) {
            return Collections.emptyList();
        }
        List<String> out = new ArrayList<>();
        Matcher m = Pattern.compile(regex).matcher(input);
        while (m.find()) {
            out.add(m.group());
        }
        return out;
    }

    /**
     * 返回所有非重叠匹配子串（使用已编译 {@link Pattern}）。
     */
    public static List<String> findAll(String input, Pattern pattern) {
        if (input == null || pattern == null) {
            return Collections.emptyList();
        }
        List<String> out = new ArrayList<>();
        Matcher m = pattern.matcher(input);
        while (m.find()) {
            out.add(m.group());
        }
        return out;
    }

    /**
     * 取首次匹配中指定捕获组的文本；组号非法、无匹配或入参为 {@code null} 时返回 {@code null}。
     */
    public static String group(String input, String regex, int group) {
        if (input == null || regex == null || group < 0) {
            return null;
        }
        Matcher m = Pattern.compile(regex).matcher(input);
        if (!m.find()) {
            return null;
        }
        try {
            return m.group(group);
        } catch (IndexOutOfBoundsException e) {
            return null;
        }
    }

    /**
     * 替换所有匹配项；{@code input} 为 {@code null} 时返回 {@code null}。
     */
    public static String replaceAll(String input, String regex, String replacement) {
        if (input == null) {
            return null;
        }
        if (regex == null) {
            return input;
        }
        if (replacement == null) {
            replacement = "";
        }
        return input.replaceAll(regex, replacement);
    }

    /**
     * 替换所有匹配项（使用已编译 {@link Pattern}，支持 {@code $n} 等替换引用）。
     */
    public static String replaceAll(String input, Pattern pattern, String replacement) {
        if (input == null) {
            return null;
        }
        if (pattern == null) {
            return input;
        }
        if (replacement == null) {
            replacement = "";
        }
        return pattern.matcher(input).replaceAll(replacement);
    }

    /**
     * 仅替换首个匹配；{@code input} 为 {@code null} 时返回 {@code null}。
     */
    public static String replaceFirst(String input, String regex, String replacement) {
        if (input == null) {
            return null;
        }
        if (regex == null) {
            return input;
        }
        if (replacement == null) {
            replacement = "";
        }
        return input.replaceFirst(regex, replacement);
    }

    /**
     * 仅替换首个匹配（使用已编译 {@link Pattern}）。
     */
    public static String replaceFirst(String input, Pattern pattern, String replacement) {
        if (input == null) {
            return null;
        }
        if (pattern == null) {
            return input;
        }
        if (replacement == null) {
            replacement = "";
        }
        return pattern.matcher(input).replaceFirst(replacement);
    }

    /**
     * 按正则拆分；{@code input} 为 {@code null} 时返回 {@code null}。
     */
    public static String[] split(String input, String regex) {
        if (input == null) {
            return null;
        }
        if (regex == null) {
            return new String[] { input };
        }
        return input.split(regex);
    }

    /**
     * 按正则拆分并限制段数（语义同 {@link String#split(String, int)}）。
     */
    public static String[] split(String input, String regex, int limit) {
        if (input == null) {
            return null;
        }
        if (regex == null) {
            return new String[] { input };
        }
        return input.split(regex, limit);
    }
}
