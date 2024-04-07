package seedu.findvisor.commons.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.findvisor.testutil.Assert.assertThrows;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;

import org.junit.jupiter.api.Test;

public class DateTimeUtilTest {
    @Test
    public void parseDateTimeString_validString() {
        assertEquals(LocalDateTime.of(2024, 1, 29, 14, 0), DateTimeUtil.parseDateTimeString("29-01-2024T14:00"));
    }

    @Test
    public void parseDateTimeString_invalidString_throwsDateTimeParseException() {
        assertThrows(DateTimeParseException.class, () -> DateTimeUtil.parseDateTimeString("INVALID STRING"));

        // Invalid date
        assertThrows(DateTimeParseException.class, () -> DateTimeUtil.parseDateTimeString("31-04-2024T14:00"));

        // Invalid date leap year
        assertThrows(DateTimeParseException.class, () -> DateTimeUtil.parseDateTimeString("29-02-2025T14:00"));
    }

    @Test
    public void isValidDate_invalidString_returnsFalse() {
        // Invalid string
        assertFalse(DateTimeUtil.isValidDate("Invalid String"));

        // Invalid date format
        assertFalse(DateTimeUtil.isValidDate("2024/10/12"));
    }

    @Test
    public void isValidDate_validString_returnsTrue() {
        assertTrue(DateTimeUtil.isValidDate("10-12-2024"));
        assertTrue(DateTimeUtil.isValidDate("01-01-2024"));
    }

    @Test
    public void isValidDateTime_invalidString_returnsFalse() {
        // Invalid string
        assertFalse(DateTimeUtil.isValidDateTime("Invalid String"));

        // Invalid date format
        assertFalse(DateTimeUtil.isValidDateTime("2024/10/12"));
    }

    @Test
    public void isValidDateTime_validString_returnsTrue() {
        assertTrue(DateTimeUtil.isValidDateTime("10-12-2024T14:00"));
        assertTrue(DateTimeUtil.isValidDateTime("01-01-2024T14:00"));
    }

    @Test
    public void parseDateString_validString() {
        assertEquals(LocalDate.of(2024, 12, 31), DateTimeUtil.parseDateString("31-12-2024"));
    }

    @Test
    public void parseDateString_invalidString_throwsDateTimeParseException() {
        assertThrows(DateTimeParseException.class, () -> DateTimeUtil.parseDateString("INVALID STRING"));

        // Invalid date
        assertThrows(DateTimeParseException.class, () -> DateTimeUtil.parseDateString("31-04-2024"));

        // Invalid date leap year
        assertThrows(DateTimeParseException.class, () -> DateTimeUtil.parseDateString("29-02-2025"));
    }

    @Test
    public void parseDateString_invalidDateFormat_throwsDateTimeParseException() {
        assertThrows(DateTimeParseException.class, () -> DateTimeUtil.parseDateString("2024-10-10"));
    }

    @Test
    public void dateTimeToInputString() {
        assertEquals("29-01-2024T14:00", DateTimeUtil.dateTimeToInputString(LocalDateTime.of(2024, 1, 29, 14, 0)));
    }

    @Test
    public void dateTimeToString() {
        assertEquals("29-01-2024 14:00", DateTimeUtil.dateTimeToString(LocalDateTime.of(2024, 1, 29, 14, 0)));
    }

    @Test
    public void dateToString() {
        assertEquals("24-07-2024", DateTimeUtil.dateToString(LocalDate.of(2024, 07, 24)));
    }

    @Test
    public void isAfterCurrentDateTime() {
        assertTrue(DateTimeUtil.isAfterCurrentDateTime(LocalDateTime.now().plusMinutes(5)));
    }

    @Test
    public void isSameDate_equalDates_returnsTrue() {
        assertTrue(DateTimeUtil.isSameDate(LocalDate.of(2024, 12, 31), LocalDate.of(2024, 12, 31)));
    }

    @Test
    public void isSameDate_differentDates_returnsFalse() {
        assertFalse(DateTimeUtil.isSameDate(LocalDate.of(2024, 12, 31), LocalDate.of(2024, 12, 30)));
    }

}

