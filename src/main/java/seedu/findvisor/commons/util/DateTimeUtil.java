package seedu.findvisor.commons.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;

/**
 * Handles DateTime parsing and formatting.
 */
public class DateTimeUtil {
    public static final DateTimeFormatter DATE_TIME_INPUT_FORMAT =
            DateTimeFormatter.ofPattern("dd-MM-yyyy'T'HH:mm");

    public static final DateTimeFormatter DATE_TIME_OUTPUT_FORMAT =
            DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");

    /**
     * Converts a String into a LocalDateTime object. The expected format is yyyy-MM-dd'T'HH:mm. For
     * example, 2023-01-29T14:00.
     *
     * @param input The string to be converted to a LocalDateTime object.
     * @return The resulting LocalDateTime object after the conversion.
     * @throws DateTimeParseException If the String is not in the expected format.
     */
    public static LocalDateTime parseDateTimeString(String input) throws DateTimeParseException {
        return LocalDateTime.parse(input, DATE_TIME_INPUT_FORMAT);
    }

    /**
     * Converts a LocalDateTime object into a String in input format. The format is yyyy-MM-dd'T'HH:mm. For
     * example, 2023-01-29T14:00.
     *
     * @param dateTime The LocalDateTime object to be converted.
     * @return The resulting string after the conversion.
     */
    public static String dateTimeToInputString(LocalDateTime dateTime) {
        return dateTime.format(DATE_TIME_INPUT_FORMAT);
    }

    /**
     * Converts a LocalDateTime object into a String in output format. The format is yyyy-MM-dd HH:mm. For
     * example, 2023-01-29 14:00.
     *
     * @param dateTime The LocalDateTime object to be converted.
     * @return The resulting string after the conversion.
     */
    public static String dateTimeToString(LocalDateTime dateTime) {
        return dateTime.format(DATE_TIME_OUTPUT_FORMAT);
    }

    /**
     * Checks if the given LocalDateTime object is after the current date and time.
     *
     * @param dateTime The LocalDateTime object to be checked.
     * @return True if the given LocalDateTime object is after the current date and time, false otherwise.
     */
    public static boolean isAfterCurrentDateTime(LocalDateTime dateTime) {
        return dateTime.isAfter(LocalDateTime.now());
    }

    /**
     * Checks if 2 LocalDateTime objects are equal to each other, with precision up to minutes.
     *
     * @param dateTime1 The first LocalDateTime object to be compared.
     * @param dateTime2 The second LocalDateTime object to be compared.
     * @return True if the 2 LocalDateTime objects are equal up to minutes.
     */
    public static boolean isEqualsDateTimeMinutes(LocalDateTime dateTime1, LocalDateTime dateTime2) {
        return dateTime1.truncatedTo(ChronoUnit.MINUTES).equals(dateTime2.truncatedTo(ChronoUnit.MINUTES));
    }

}
