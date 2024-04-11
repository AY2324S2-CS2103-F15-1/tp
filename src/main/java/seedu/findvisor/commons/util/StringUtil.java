package seedu.findvisor.commons.util;

import static java.util.Objects.requireNonNull;
import static seedu.findvisor.commons.util.AppUtil.checkArgument;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * Helper functions for handling strings.
 */
public class StringUtil {

    private static final String SAFE_STRING_REGEX = "[\\w\\s!@#$%^&*()+\\-{}\\[\\]:;'\"\\\\<>?.,|~`]*";

    /**
     * Returns true if the {@code sentence} contains the {@code subString}
     * Ignores case, but order of the words in {@code subString} matter.
     * @param sentence The string in which to search for the substring.
     * @param subString The substring to search within a {@code sentence}.
     */
    public static boolean containsIgnoreCase(String sentence, String subString) {
        requireNonNull(sentence);
        requireNonNull(subString);

        String preppedSubString = subString.trim().toLowerCase();
        checkArgument(!preppedSubString.isEmpty(), "subString parameter cannot be empty");
        return sentence.toLowerCase().contains(preppedSubString);
    }

    /**
     * Returns a detailed message of the t, including the stack trace.
     */
    public static String getDetails(Throwable t) {
        requireNonNull(t);
        StringWriter sw = new StringWriter();
        t.printStackTrace(new PrintWriter(sw));
        return t.getMessage() + "\n" + sw.toString();
    }

    /**
     * Returns true if {@code s} represents a non-zero unsigned integer
     * e.g. 1, 2, 3, ..., {@code Integer.MAX_VALUE} <br>
     * Will return false for any other non-null string input
     * e.g. empty string, "-1", "0", "+1", and " 2 " (untrimmed), "3 0" (contains whitespace), "1 a" (contains letters)
     * @throws NullPointerException if {@code s} is null.
     */
    public static boolean isNonZeroUnsignedInteger(String s) {
        requireNonNull(s);

        try {
            int value = Integer.parseInt(s);
            return value > 0 && !s.startsWith("+"); // "+1" is successfully parsed by Integer#parseInt(String)
        } catch (NumberFormatException nfe) {
            return false;
        }
    }

    /**
     * Returns true if {@code s} is a safe string
     * A safe string only consists of alphanumeric characters, whitespace
     * and the set of special characters: "!@#$%^&*()_+-{}[]:;'"<>?.,|~`\"
     * @throws NullPointerException if {@code s} is null.
     */
    public static boolean isSafeString(String s) {
        requireNonNull(s);

        return s.matches(SAFE_STRING_REGEX);
    }

}
