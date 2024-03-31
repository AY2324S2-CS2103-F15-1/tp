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


    //---------------- Tests for containsWordIgnoreCase --------------------------------------

    /*
     * Invalid equivalence partitions for word: null, empty, multiple words
     * Invalid equivalence partitions for sentence: null
     * The four test cases below test one invalid input at a time.
     */

    @Test
    public void containsWordIgnoreCase_nullWord_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> StringUtil.containsWordIgnoreCase("typical sentence", null));
    }

    @Test
    public void containsWordIgnoreCase_emptyWord_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, "Word parameter cannot be empty", ()
            -> StringUtil.containsWordIgnoreCase("typical sentence", "  "));
    }

    @Test
    public void containsWordIgnoreCase_multipleWords_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, "Word parameter should be a single word", ()
            -> StringUtil.containsWordIgnoreCase("typical sentence", "aaa BBB"));
    }

    @Test
    public void containsWordIgnoreCase_nullSentence_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> StringUtil.containsWordIgnoreCase(null, "abc"));
    }

    /*
     * Valid equivalence partitions for word:
     *   - any word
     *   - word containing symbols/numbers
     *   - word with leading/trailing spaces
     *
     * Valid equivalence partitions for sentence:
     *   - empty string
     *   - one word
     *   - multiple words
     *   - sentence with extra spaces
     *
     * Possible scenarios returning true:
     *   - matches first word in sentence
     *   - last word in sentence
     *   - middle word in sentence
     *   - matches multiple words
     *
     * Possible scenarios returning false:
     *   - query word matches part of a sentence word
     *   - sentence word matches part of the query word
     *
     * The test method below tries to verify all above with a reasonably low number of test cases.
     */

    @Test
    public void containsWordIgnoreCase_validInputs_correctResult() {

        // Empty sentence
        assertFalse(StringUtil.containsWordIgnoreCase("", "abc")); // Boundary case
        assertFalse(StringUtil.containsWordIgnoreCase("    ", "123"));

        // Matches a partial word only
        assertFalse(StringUtil.containsWordIgnoreCase("aaa bbb ccc", "bb")); // Sentence word bigger than query word
        assertFalse(StringUtil.containsWordIgnoreCase("aaa bbb ccc", "bbbb")); // Query word bigger than sentence word

        // Matches word in the sentence, different upper/lower case letters
        assertTrue(StringUtil.containsWordIgnoreCase("aaa bBb ccc", "Bbb")); // First word (boundary case)
        assertTrue(StringUtil.containsWordIgnoreCase("aaa bBb ccc@1", "CCc@1")); // Last word (boundary case)
        assertTrue(StringUtil.containsWordIgnoreCase("  AAA   bBb   ccc  ", "aaa")); // Sentence has extra spaces
        assertTrue(StringUtil.containsWordIgnoreCase("Aaa", "aaa")); // Only one word in sentence (boundary case)
        assertTrue(StringUtil.containsWordIgnoreCase("aaa bbb ccc", "  ccc  ")); // Leading/trailing spaces

        // Matches multiple words in sentence
        assertTrue(StringUtil.containsWordIgnoreCase("AAA bBb ccc  bbb", "bbB"));
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
