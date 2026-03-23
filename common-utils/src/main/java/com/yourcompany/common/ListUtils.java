package com.yourcompany.common;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

/**
 * 列表常用处理工具（无第三方依赖）。
 */
public final class ListUtils {

    private ListUtils() {
    }

    /**
     * 按指定字段（由 {@code keyExtractor} 映射得到）去重，保留首次出现的元素顺序。
     * <p>
     * 键比较使用 {@link Map} 的语义：{@code null} 键合法；相同键只保留列表中第一次出现的元素。
     * </p>
     *
     * @param list          源列表，若为 {@code null} 或空则返回不可变的空列表
     * @param keyExtractor  从元素提取去重键，不可为 {@code null}
     * @param <T>           元素类型
     * @param <K>           键类型
     * @return 新列表，不为 {@code null}
     */
    public static <T, K> List<T> distinctBy(List<T> list, Function<? super T, ? extends K> keyExtractor) {
        if (list == null || list.isEmpty()) {
            return Collections.emptyList();
        }
        if (keyExtractor == null) {
            throw new IllegalArgumentException("keyExtractor must not be null");
        }
        Map<K, T> seen = new LinkedHashMap<>();
        for (T item : list) {
            K key = keyExtractor.apply(item);
            if (!seen.containsKey(key)) {
                seen.put(key, item);
            }
        }
        return new ArrayList<>(seen.values());
    }
}
