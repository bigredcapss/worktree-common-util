package com.yourcompany.common;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.regex.Pattern;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class RegexUtilsTest {

    @Nested
    @DisplayName("matches")
    class MatchesTests {

        @Test
        void fullMatch() {
            assertTrue(RegexUtils.matches("123", "\\d+"));
            assertFalse(RegexUtils.matches("12a", "\\d+"));
        }

        @Test
        void nullArgs() {
            assertFalse(RegexUtils.matches(null, "\\d+"));
            assertFalse(RegexUtils.matches("1", (String) null));
            assertFalse(RegexUtils.matches("1", (Pattern) null));
        }

        @Test
        void compiledPattern() {
            Pattern p = Pattern.compile("\\d+");
            assertTrue(RegexUtils.matches("99", p));
            assertFalse(RegexUtils.matches("a", p));
        }
    }

    @Nested
    @DisplayName("contains")
    class ContainsTests {

        @Test
        void substringMatch() {
            assertTrue(RegexUtils.contains("ab12cd", "\\d+"));
            assertFalse(RegexUtils.contains("abc", "\\d+"));
        }

        @Test
        void nullSafe() {
            assertFalse(RegexUtils.contains(null, "x"));
            assertFalse(RegexUtils.contains("x", (String) null));
            assertFalse(RegexUtils.contains("x", (Pattern) null));
        }
    }

    @Nested
    @DisplayName("findFirst / findAll")
    class FindTests {

        @Test
        void findFirst() {
            assertEquals("12", RegexUtils.findFirst("ab12cd34", "\\d+"));
            assertNull(RegexUtils.findFirst("abc", "\\d+"));
            assertNull(RegexUtils.findFirst(null, "\\d+"));
        }

        @Test
        void findAll() {
            List<String> got = RegexUtils.findAll("a1b22c", "\\d+");
            assertEquals(Arrays.asList("1", "22"), got);
            assertEquals(Collections.emptyList(), RegexUtils.findAll(null, "\\d+"));
        }
    }

    @Nested
    @DisplayName("group")
    class GroupTests {

        @Test
        void captureGroup() {
            assertEquals("ab", RegexUtils.group("prefix-ab-suffix", "(\\w+)-(\\w+)-(\\w+)", 2));
            assertNull(RegexUtils.group("no-match", "(\\d+)", 1));
        }
    }

    @Nested
    @DisplayName("replace")
    class ReplaceTests {

        @Test
        void replaceAllAndFirst() {
            assertEquals("a-a", RegexUtils.replaceAll("a,a", ",", "-"));
            assertEquals("a-a", RegexUtils.replaceFirst("a,a", ",", "-"));
        }

        @Test
        void backReferenceInReplacement() {
            assertEquals("b-a", RegexUtils.replaceFirst("a-b", "(\\w+)-(\\w+)", "$2-$1"));
        }

        @Test
        void nullInput() {
            assertNull(RegexUtils.replaceAll(null, ",", "-"));
            assertNull(RegexUtils.replaceFirst(null, ",", "-"));
        }
    }

    @Nested
    @DisplayName("split")
    class SplitTests {

        @Test
        void basic() {
            assertArrayEquals(new String[] { "a", "b" }, RegexUtils.split("a,b", ","));
            assertNull(RegexUtils.split(null, ","));
        }
    }
}
