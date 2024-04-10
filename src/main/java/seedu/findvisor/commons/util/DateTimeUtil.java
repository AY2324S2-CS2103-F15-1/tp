package seedu.findvisor.commons.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;
import java.time.temporal.ChronoUnit;

/**
 * Handles DateTime parsing and formatting.
 */
public class DateTimeUtil {
    public static final String DATE_PATTERN = "dd-MM-uuuu";

    public static final DateTimeFormatter DATE_TIME_INPUT_FORMAT =
            DateTimeFormatter.ofPattern("dd-MM-uuuu'T'HH:mm")
            .withResolverStyle(ResolverStyle.STRICT);

    public static final DateTimeFormatter DATE_TIME_OUTPUT_FORMAT =
            DateTimeFormatter.ofPattern("dd-MM-uuuu HH:mm")
            .withResolverStyle(ResolverStyle.STRICT);

    public static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern(DATE_PATTERN)
            .withResolverStyle(ResolverStyle.STRICT);

    /**
     * Returns true if {@code date} can be converted into a {@code LocalDate} via {@link LocalDate#parse(CharSequence)},
     * otherwise returns false.
     * @param date A string representing the date.
     */
    public static boolean isValidDate(String date) {
        try {
            LocalDate.parse(date, DATE_FORMAT);
        } catch (DateTimeParseException e) {
            return false;
        }
        return true;
    }

    /**
     * Returns true if {@code dateTime} can be converted into a {@code LocalDateTime}
     * via {@link LocalDateTime#parse(CharSequence)}, otherwise returns false.
     * @param dateTime A string representing the date and time.
     */
    public static boolean isValidDateTime(String dateTime) {
        try {
            LocalDateTime.parse(dateTime, DATE_TIME_INPUT_FORMAT);
        } catch (DateTimeParseException e) {
            return false;
        }
        return true;
    }

    /**
     * Converts a String into a LocalDateTime object. The expected format is dd-MM-yyyy'T'HH:mm. For
     * example, 29-01-2023T14:00.
     *
     * @param input The string to be converted to a LocalDateTime object.
     * @return The resulting LocalDateTime object after the conversion.
     * @throws DateTimeParseException If the String is not in the expected format.
     */
    public static LocalDateTime parseDateTimeString(String input) throws DateTimeParseException {
        return LocalDateTime.parse(input, DATE_TIME_INPUT_FORMAT);
    }

    /**
     * Converts a String into a LocalDate object. The expected format is dd-MM-yyyy. For
     * example, 29-12-2024.
     *
     * @param input The string to be converted to a LocalDate object.
     * @return The resulting LocalDate object after the conversion.
     * @throws DateTimeParseException If the String is not in the expected format.
     */
    public static LocalDate parseDateString(String input) {
        return LocalDate.parse(input, DATE_FORMAT);
    }

    /**
     * Converts a LocalDateTime object into a String in input format. The format is dd-MM-yyyy'T'HH:mm. For
     * example, 29-01-2023T14:00.
     *
     * @param dateTime The LocalDateTime object to be converted.
     * @return The resulting string after the conversion.
     */
    public static String dateTimeToInputString(LocalDateTime dateTime) {
        return dateTime.format(DATE_TIME_INPUT_FORMAT);
    }

    /**
     * Converts a LocalDate object into a String in input format. The format is dd-MM-yyyy. For
     * example, 29-12-2024.
     *
     * @param date The LocalDate object to be converted.
     * @return The resulting string after the conversion.
     */
    public static String dateToString(LocalDate date) {
        return date.format(DATE_FORMAT);
    }

    /**
     * Converts a LocalDateTime object into a String in output format. The format is dd-MM-yyyy HH:mm. For
     * example, 29-01-2023 14:00.
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
     * Checks if two LocalDateTime objects are equal to each other, with precision up to minutes.
     *
     * @param dateTime The first LocalDateTime object to be compared.
     * @param otherDateTime The other LocalDateTime object to be compared.
     * @return True if the two LocalDateTime objects are equal up to minutes.
     */
    public static boolean isEqualsDateTimeMinutes(LocalDateTime dateTime, LocalDateTime otherDateTime) {
        return dateTime.truncatedTo(ChronoUnit.MINUTES).equals(otherDateTime.truncatedTo(ChronoUnit.MINUTES));
    }

    /**
     * Checks if the two given LocalDate object have the same date.
     *
     * @param date The LocalDate object to be checked.
     * @param otherDate The other LocalDate object to be checked against.
     * @return True if the given LocalDate objects have the same date, false otherwise.
     */
    public static boolean isSameDate(LocalDate date, LocalDate otherDate) {
        return date.equals(otherDate);
    }

}
