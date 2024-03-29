package seedu.findvisor.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.findvisor.logic.commands.CommandTestUtil.createOldMeeting;
import static seedu.findvisor.logic.commands.CommandTestUtil.createValidLongMeeting;
import static seedu.findvisor.logic.commands.CommandTestUtil.createValidMeeting;

import java.time.LocalDate;
import java.util.Optional;

import org.junit.jupiter.api.Test;

import seedu.findvisor.testutil.DateTestUtil;
import seedu.findvisor.testutil.PersonBuilder;

public class PersonMeetingPredicateTest {

    @Test
    public void equals() {
        LocalDate firstPredicateKeyword = LocalDate.of(2024, 12, 24);
        LocalDate secondPredicateKeyword = LocalDate.of(2024, 12, 31);

        PersonMeetingPredicate firstPredicate = new PersonMeetingPredicate(firstPredicateKeyword);
        PersonMeetingPredicate secondPredicate = new PersonMeetingPredicate(secondPredicateKeyword);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        PersonMeetingPredicate firstPredicateCopy = new PersonMeetingPredicate(firstPredicateKeyword);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different email -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_meetingDateOnSpecifiedDate_returnsTrue() {
        LocalDate date = LocalDate.now().plusDays(1);
        // Specified date is equal to start and end date
        PersonMeetingPredicate predicate = new PersonMeetingPredicate(date);
        assertTrue(predicate.test(new PersonBuilder().withMeeting(Optional.of(createValidMeeting())).build()));

        // Specified date is equal to start date
        assertTrue(predicate.test(new PersonBuilder().withMeeting(Optional.of(createValidLongMeeting())).build()));

        // Specified date is equal to end date
        date = LocalDate.now().plusDays(2);
        predicate = new PersonMeetingPredicate(date);
        assertTrue(predicate.test(new PersonBuilder().withMeeting(Optional.of(createValidLongMeeting())).build()));
    }

    @Test
    public void test_dateNotOnMeetingDate_returnsFalse() {
        LocalDate date = LocalDate.now().plusDays(2);
        // Specified date is after meeting start and end date
        PersonMeetingPredicate predicate = new PersonMeetingPredicate(date);
        assertFalse(predicate.test(new PersonBuilder().withMeeting(Optional.of(createOldMeeting())).build()));
    }

    @Test
    public void testGetPredicateDescription() {
        LocalDate date = LocalDate.of(2024, 12, 24);
        PersonMeetingPredicate predicate = new PersonMeetingPredicate(date);

        String expected = String.format("Meeting on \"%1$s\"", DateTestUtil.formatDateToString(date));
        assertEquals(expected, predicate.getPredicateDescription());
    }

    @Test
    public void toStringMethod() {
        LocalDate date = LocalDate.of(2024, 12, 24);
        PersonMeetingPredicate predicate = new PersonMeetingPredicate(date);

        String expected = PersonMeetingPredicate.class.getCanonicalName() + "{meeting=" + date + "}";
        assertEquals(expected, predicate.toString());
    }
}
