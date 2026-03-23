package com.yourcompany.common;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

/**
 * JSON 字符串与 Java 对象互转（基于 Jackson）。
 */
public final class JsonUtils {

    private static final ObjectMapper MAPPER = new ObjectMapper();

    static {
        MAPPER.registerModule(new JavaTimeModule());
        MAPPER.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        MAPPER.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
    }

    private JsonUtils() {
    }

    /**
     * 返回内部使用的 {@link ObjectMapper}，便于在需要时统一注册模块或调整特性。
     */
    public static ObjectMapper mapper() {
        return MAPPER;
    }

    /**
     * 将对象序列化为 JSON 字符串。
     * <p>
     * {@code value == null} 时返回 {@code null}（不输出字面量 {@code "null"}）。
     */
    public static String toJson(Object value) {
        if (value == null) {
            return null;
        }
        try {
            return MAPPER.writeValueAsString(value);
        } catch (JsonProcessingException e) {
            throw new JsonException("序列化为 JSON 失败", e);
        }
    }

    /**
     * 将对象序列化为带缩进的 JSON 字符串，便于阅读。
     */
    public static String toPrettyJson(Object value) {
        if (value == null) {
            return null;
        }
        try {
            return MAPPER.writerWithDefaultPrettyPrinter().writeValueAsString(value);
        } catch (JsonProcessingException e) {
            throw new JsonException("序列化为 JSON 失败", e);
        }
    }

    /**
     * 将 JSON 反序列化为指定类型。
     * <p>
     * {@code json == null} 或空串时返回 {@code null}。
     */
    public static <T> T fromJson(String json, Class<T> type) {
        if (json == null || json.isEmpty()) {
            return null;
        }
        try {
            return MAPPER.readValue(json, type);
        } catch (JsonProcessingException e) {
            throw new JsonException("从 JSON 反序列化失败: " + type.getName(), e);
        }
    }

    /**
     * 将 JSON 反序列化为泛型类型（如 {@code List<String>}、{@code Map<String, Object>}）。
     * <p>
     * 用法示例：{@code fromJson(s, new TypeReference<List<String>>() {})}
     */
    public static <T> T fromJson(String json, TypeReference<T> typeRef) {
        if (json == null || json.isEmpty()) {
            return null;
        }
        try {
            return MAPPER.readValue(json, typeRef);
        } catch (JsonProcessingException e) {
            throw new JsonException("从 JSON 反序列化失败", e);
        }
    }

    /**
     * 解析为树模型，便于按路径读取而无需绑定类型。
     */
    public static JsonNode readTree(String json) {
        if (json == null || json.isEmpty()) {
            return null;
        }
        try {
            return MAPPER.readTree(json);
        } catch (JsonProcessingException e) {
            throw new JsonException("解析 JSON 树失败", e);
        }
    }

    /**
     * JSON 处理失败时抛出，包装 Jackson 的受检异常。
     */
    public static final class JsonException extends RuntimeException {

        public JsonException(String message, Throwable cause) {
            super(message, cause);
        }
    }
}
