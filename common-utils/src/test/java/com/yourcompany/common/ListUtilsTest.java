package com.yourcompany.common;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * {@link ListUtils} 单元测试。
 */
class ListUtilsTest {

    static final class Person {
        private final String id;
        private final String name;

        Person(String id, String name) {
            this.id = id;
            this.name = name;
        }

        String getId() {
            return id;
        }

        String getName() {
            return name;
        }
    }

    @Test
    @DisplayName("按 id 去重，保留先出现者")
    void distinctByKeepsFirstOccurrence() {
        Person a = new Person("1", "A");
        Person b = new Person("1", "B");
        Person c = new Person("2", "C");
        List<Person> out = ListUtils.distinctBy(Arrays.asList(a, b, c), Person::getId);
        assertEquals(2, out.size());
        assertEquals("A", out.get(0).getName());
        assertEquals("C", out.get(1).getName());
    }

    @Test
    @DisplayName("null / 空列表返回空列表")
    void nullOrEmptyReturnsEmpty() {
        assertTrue(ListUtils.distinctBy(null, Person::getId).isEmpty());
        assertTrue(ListUtils.distinctBy(Collections.emptyList(), Person::getId).isEmpty());
    }

    @Test
    @DisplayName("键可为 null，多个 null 键只保留第一个")
    void nullKeyDeduped() {
        List<String> out = ListUtils.distinctBy(Arrays.asList("x", null, "y", null), s -> s);
        assertEquals(Arrays.asList("x", null, "y"), out);
    }

    @Test
    @DisplayName("keyExtractor 为 null 时抛 IllegalArgumentException")
    void nullExtractorThrows() {
        assertThrows(IllegalArgumentException.class, () -> ListUtils.distinctBy(Arrays.asList("a"), null));
    }
}
