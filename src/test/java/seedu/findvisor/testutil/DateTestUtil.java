package seedu.findvisor.testutil;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * A utility class for converting LocalDate objects to Strings during testing.
 */
public class DateTestUtil {
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy");

    /**
     * Returns the String representation of the {@code LocalDate} object.
     */
    public static String formatDateToString(LocalDate date) {
        return date.format(DATE_FORMATTER);
    }

    /**
     * Returns the {@code LocalDate} object given a date String.
     */
    public static LocalDate parseDate(String dateString) {
        return LocalDate.parse(dateString, DATE_FORMATTER);
    }

}
