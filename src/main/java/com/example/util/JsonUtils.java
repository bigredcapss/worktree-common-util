package com.example.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.io.IOException;
import java.io.UncheckedIOException;

/**
 * JSON 与 Java 对象互转工具类，基于 Jackson {@link ObjectMapper}。
 */
public final class JsonUtils {

    private static final ObjectMapper MAPPER = new ObjectMapper()
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
            .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

    private JsonUtils() {
    }

    /**
     * 将对象序列化为 JSON 字符串。
     */
    public static String toJson(Object value) {
        try {
            return MAPPER.writeValueAsString(value);
        } catch (JsonProcessingException e) {
            throw new UncheckedIOException(e);
        }
    }

    /**
     * 将对象序列化为格式化的 JSON 字符串（便于阅读）。
     */
    public static String toPrettyJson(Object value) {
        try {
            return MAPPER.writerWithDefaultPrettyPrinter().writeValueAsString(value);
        } catch (JsonProcessingException e) {
            throw new UncheckedIOException(e);
        }
    }

    /**
     * 将 JSON 字符串反序列化为指定类型的对象。
     */
    public static <T> T toObject(String json, Class<T> type) {
        try {
            return MAPPER.readValue(json, type);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    /**
     * 将 JSON 字符串反序列化为泛型类型（如 {@code List<User>}、{@code Map<String, Object>}）。
     * <pre>
     * List&lt;User&gt; list = JsonUtils.toObject(json, new TypeReference&lt;List&lt;User&gt;&gt;() {});
     * </pre>
     */
    public static <T> T toObject(String json, TypeReference<T> typeReference) {
        try {
            return MAPPER.readValue(json, typeReference);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    /**
     * 返回内部使用的 {@link ObjectMapper} 副本，便于在需要自定义配置的场景中基于同一基准继续配置。
     */
    public static ObjectMapper copyMapper() {
        return MAPPER.copy();
    }
}
