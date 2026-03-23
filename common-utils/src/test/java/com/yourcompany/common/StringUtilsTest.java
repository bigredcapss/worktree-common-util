package com.yourcompany.common;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class StringUtilsTest {

    @Nested
    @DisplayName("isBlank / isNotBlank")
    class BlankTests {

        @Test
        void nullAndEmpty() {
            assertTrue(StringUtils.isBlank(null));
            assertTrue(StringUtils.isBlank(""));
            assertTrue(StringUtils.isBlank("   \t\n"));
            assertFalse(StringUtils.isNotBlank(null));
        }

        @Test
        void hasContent() {
            assertFalse(StringUtils.isBlank("a"));
            assertTrue(StringUtils.isNotBlank(" x "));
        }
    }

    @Nested
    @DisplayName("trimToNull")
    class TrimToNullTests {

        @Test
        void trimsOrNull() {
            assertNull(StringUtils.trimToNull(null));
            assertNull(StringUtils.trimToNull("   "));
            assertEquals("ab", StringUtils.trimToNull("  ab  "));
        }
    }

    @Nested
    @DisplayName("defaultIfBlank / defaultIfEmpty")
    class DefaultTests {

        @Test
        void defaultIfBlank() {
            assertEquals("d", StringUtils.defaultIfBlank(null, "d"));
            assertEquals("d", StringUtils.defaultIfBlank("  ", "d"));
            assertEquals("x", StringUtils.defaultIfBlank("x", "d"));
        }

        @Test
        void defaultIfEmpty() {
            assertEquals("d", StringUtils.defaultIfEmpty(null, "d"));
            assertEquals("d", StringUtils.defaultIfEmpty("", "d"));
            assertEquals(" ", StringUtils.defaultIfEmpty(" ", "d"));
        }
    }

    @Nested
    @DisplayName("equals")
    class EqualsTests {

        @Test
        void nullSafe() {
            assertTrue(StringUtils.equals(null, null));
            assertFalse(StringUtils.equals(null, "a"));
            assertFalse(StringUtils.equals("a", null));
            assertTrue(StringUtils.equals("a", "a"));
        }

        @Test
        void ignoreCase() {
            assertTrue(StringUtils.equalsIgnoreCase("Ab", "ab"));
            assertTrue(StringUtils.equalsIgnoreCase(null, null));
            assertFalse(StringUtils.equalsIgnoreCase(null, "a"));
        }
    }

    @Nested
    @DisplayName("truncate")
    class TruncateTests {

        @Test
        void truncatePlain() {
            assertNull(StringUtils.truncate(null, 3));
            assertEquals("", StringUtils.truncate("abc", 0));
            assertEquals("ab", StringUtils.truncate("abcd", 2));
        }

        @Test
        void truncateEllipsis() {
            assertEquals("ab...", StringUtils.truncateWithEllipsis("abcdef", 5));
            assertEquals("..", StringUtils.truncateWithEllipsis("abcdef", 2));
        }
    }

    @Nested
    @DisplayName("join")
    class JoinTests {

        @Test
        void varargsSkipsNull() {
            assertEquals("a|b", StringUtils.join("|", "a", null, "b"));
        }

        @Test
        void iterable() {
            assertEquals("1,2", StringUtils.join(",", Arrays.asList(1, null, 2)));
            assertEquals("", StringUtils.join(",", Collections.emptyList()));
        }
    }
}
