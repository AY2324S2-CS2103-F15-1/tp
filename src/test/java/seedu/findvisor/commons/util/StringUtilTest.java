package seedu.findvisor.commons.util;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.findvisor.testutil.Assert.assertThrows;

import java.io.FileNotFoundException;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class StringUtilTest {

    //---------------- Tests for isSafeString --------------------------------------
    @Test
    public void isSafeString_nullString_throwsNullPointerException() {
        Assertions.assertThrows(NullPointerException.class, () -> StringUtil.isSafeString(null));
    }

    @Test
    public void isSafeString_emptyString_returnsTrue() {
        assertTrue(StringUtil.isSafeString(""));
    }

    @Test
    public void isSafeString_onlyValidCharacterStrings_returnsTrue() {
        // Alphabetical characters
        assertTrue(StringUtil.isSafeString("alphabetsOnly"));

        // Numerical characters
        assertTrue(StringUtil.isSafeString("98765"));

        // Alphanumeric characters
        assertTrue(StringUtil.isSafeString("alphanumeric 01234"));

        // Only white space
        assertTrue(StringUtil.isSafeString("  "));

        // Allowed special characters
        assertTrue(StringUtil.isSafeString("_"));
        assertTrue(StringUtil.isSafeString("\\"));
        assertTrue(StringUtil.isSafeString("$()[]{}"));
        assertTrue(StringUtil.isSafeString("!-+_"));

        // Mix
        assertTrue(StringUtil.isSafeString("test string with 2 special characters::"));
        assertTrue(StringUtil.isSafeString("test string with 7 special characters:\\^&.?"));
    }

    @Test
    public void isSafeString_containsInvalidCharacters_returnsFalse() {
        // Contains forward slash "/"
        assertFalse(StringUtil.isSafeString("/"));
        assertFalse(StringUtil.isSafeString("test /"));
        assertFalse(StringUtil.isSafeString("↑"));

        // Contains foreign characters
        assertFalse(StringUtil.isSafeString("华文字体"));
    }


    //---------------- Tests for isNonZeroUnsignedInteger --------------------------------------

    @Test
    public void isNonZeroUnsignedInteger() {

        // EP: empty strings
        assertFalse(StringUtil.isNonZeroUnsignedInteger("")); // Boundary value
        assertFalse(StringUtil.isNonZeroUnsignedInteger("  "));

        // EP: not a number
        assertFalse(StringUtil.isNonZeroUnsignedInteger("a"));
        assertFalse(StringUtil.isNonZeroUnsignedInteger("aaa"));

        // EP: zero
        assertFalse(StringUtil.isNonZeroUnsignedInteger("0"));

        // EP: zero as prefix
        assertTrue(StringUtil.isNonZeroUnsignedInteger("01"));

        // EP: signed numbers
        assertFalse(StringUtil.isNonZeroUnsignedInteger("-1"));
        assertFalse(StringUtil.isNonZeroUnsignedInteger("+1"));

        // EP: numbers with white space
        assertFalse(StringUtil.isNonZeroUnsignedInteger(" 10 ")); // Leading/trailing spaces
        assertFalse(StringUtil.isNonZeroUnsignedInteger("1 0")); // Spaces in the middle

        // EP: number larger than Integer.MAX_VALUE
        assertFalse(StringUtil.isNonZeroUnsignedInteger(Long.toString(Integer.MAX_VALUE + 1)));

        // EP: valid numbers, should return true
        assertTrue(StringUtil.isNonZeroUnsignedInteger("1")); // Boundary value
        assertTrue(StringUtil.isNonZeroUnsignedInteger("10"));
    }

    //---------------- Tests for containsIgnoreCase --------------------------------------

    /*
     * Invalid equivalence partitions for substring: null, empty
     * Invalid equivalence partitions for sentence: null
     * The three test cases below test one invalid input at a time.
     */

    @Test
    public void containsIgnoreCase_nullSubString_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> StringUtil.containsIgnoreCase(
                "typical sentence", null));
    }

    @Test
    public void containsIgnoreCase_emptySubString_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, "subString parameter cannot be empty", ()
                -> StringUtil.containsIgnoreCase("typical sentence", "  "));
    }

    @Test
    public void containsIgnoreCase_nullSentence_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> StringUtil.containsIgnoreCase(null, "abc"));
    }

    @Test
    public void containsIgnoreCase_validInputs_correctResult() {
        // Empty sentence
        assertFalse(StringUtil.containsIgnoreCase("", "abc")); // Boundary case
        assertFalse(StringUtil.containsIgnoreCase("    ", "123"));

        // Sentence does not fully contain substring
        assertFalse(StringUtil.containsIgnoreCase("aaa bbb ccc", "bbbb"));

        // Sentence contains substring, but does not start with substring
        assertTrue(StringUtil.containsIgnoreCase("aaa bbb ccc", "bb"));

        // Matches substring starting in the sentence, different upper/lower case letters
        assertTrue(StringUtil.containsIgnoreCase("aaa bBb ccc", "aaa")); // First word (boundary case)
        assertTrue(StringUtil.containsIgnoreCase("Aaa", "aaa")); // Only one word in sentence (boundary case)
        assertTrue(StringUtil.containsIgnoreCase("aaa bbb ccc", "  aaa  ")); // Leading/trailing spaces

        // Sentence matches multiple words in substring
        assertTrue(StringUtil.containsIgnoreCase("AAA bBb ccc  bbb", "AAA bBb"));
    }

    //---------------- Tests for getDetails --------------------------------------

    /*
     * Equivalence Partitions: null, valid throwable object
     */

    @Test
    public void getDetails_exceptionGiven() {
        assertTrue(StringUtil.getDetails(new FileNotFoundException("file not found"))
            .contains("java.io.FileNotFoundException: file not found"));
    }

    @Test
    public void getDetails_nullGiven_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> StringUtil.getDetails(null));
    }

}
