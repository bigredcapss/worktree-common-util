package com.yourcompany.common;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.fasterxml.jackson.core.type.TypeReference;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

/**
 * {@link JsonUtils} 单元测试。
 */
class JsonUtilsTest {

    static class Sample {
        public String name;
        public int n;

        Sample() {
        }

        Sample(String name, int n) {
            this.name = name;
            this.n = n;
        }
    }

    @Nested
    @DisplayName("toJson / fromJson(Class)")
    class RoundTripClass {

        @Test
        @DisplayName("对象与 JSON 双向转换")
        void roundTrip() {
            Sample orig = new Sample("a", 1);
            String json = JsonUtils.toJson(orig);
            assertNotNull(json);
            assertTrue(json.contains("\"name\":\"a\""));
            Sample back = JsonUtils.fromJson(json, Sample.class);
            assertEquals("a", back.name);
            assertEquals(1, back.n);
        }

        @Test
        @DisplayName("toJson(null) 为 null")
        void toJsonNull() {
            assertNull(JsonUtils.toJson(null));
        }

        @Test
        @DisplayName("fromJson(null/空串) 为 null")
        void fromJsonBlank() {
            assertNull(JsonUtils.fromJson(null, Sample.class));
            assertNull(JsonUtils.fromJson("", Sample.class));
        }

        @Test
        @DisplayName("非法 JSON 抛出 JsonException")
        void invalidJson() {
            assertThrows(JsonUtils.JsonException.class, () -> JsonUtils.fromJson("{", Sample.class));
        }
    }

    @Nested
    @DisplayName("fromJson(TypeReference)")
    class TypeReferenceTests {

        @Test
        @DisplayName("泛型集合")
        void listOfString() {
            String json = "[\"x\",\"y\"]";
            List<String> list = JsonUtils.fromJson(json, new TypeReference<List<String>>() {});
            assertEquals(2, list.size());
            assertEquals("x", list.get(0));
        }

        @Test
        @DisplayName("Map")
        void map() {
            String json = "{\"k\":1}";
            Map<String, Integer> m = JsonUtils.fromJson(json, new TypeReference<Map<String, Integer>>() {});
            assertEquals(1, m.get("k"));
        }
    }

    @Nested
    @DisplayName("readTree")
    class ReadTreeTests {

        @Test
        @DisplayName("读取字段")
        void path() {
            com.fasterxml.jackson.databind.JsonNode node = JsonUtils.readTree("{\"a\":1}");
            assertEquals(1, node.get("a").asInt());
        }
    }

    @Nested
    @DisplayName("toPrettyJson")
    class PrettyTests {

        @Test
        @DisplayName("含换行缩进")
        void hasNewlines() {
            String pretty = JsonUtils.toPrettyJson(new Sample("b", 2));
            assertNotNull(pretty);
            assertTrue(pretty.contains(System.lineSeparator()) || pretty.contains("\n"));
        }
    }
}
