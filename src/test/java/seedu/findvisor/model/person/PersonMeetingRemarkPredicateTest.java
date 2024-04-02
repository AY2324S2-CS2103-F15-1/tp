package seedu.findvisor.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;
import java.util.Optional;

import org.junit.jupiter.api.Test;

import seedu.findvisor.testutil.PersonBuilder;

public class PersonMeetingRemarkPredicateTest {
    private String meetingRemarkString = "Physical meeting at Serangoon Gardens";
    private Optional<Meeting> meeting = Optional.of(new Meeting(LocalDateTime.now(),
            LocalDateTime.now().plusDays(1), meetingRemarkString));

    @Test
    public void equals() {
        String firstPredicateKeyword = "Physical meeting at Serangoon Gardens";
        String secondPredicateKeyword = "Online Meeting";

        PersonMeetingRemarkPredicate firstPredicate = new PersonMeetingRemarkPredicate(firstPredicateKeyword);
        PersonMeetingRemarkPredicate secondPredicate = new PersonMeetingRemarkPredicate(secondPredicateKeyword);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        PersonMeetingRemarkPredicate firstPredicateCopy = new PersonMeetingRemarkPredicate(firstPredicateKeyword);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different email -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_remarkContainsKeyword_returnsTrue() {
        // Exact match
        PersonMeetingRemarkPredicate predicate = new PersonMeetingRemarkPredicate(meetingRemarkString);
        assertTrue(predicate.test(new PersonBuilder().withMeeting(meeting).build()));

        // Substring match
        predicate = new PersonMeetingRemarkPredicate("Serangoon Gardens");
        assertTrue(predicate.test(new PersonBuilder().withMeeting(meeting).build()));

        // Mixed-case keyword
        predicate = new PersonMeetingRemarkPredicate("serangoon gardens");
        assertTrue(predicate.test(new PersonBuilder().withMeeting(meeting).build()));
    }

    @Test
    public void test_remarkDoesNotContainsKeyword_returnsFalse() {
        // Non-matching keyword
        PersonMeetingRemarkPredicate predicate = new PersonMeetingRemarkPredicate("Online");
        assertFalse(predicate.test(new PersonBuilder().withMeeting(meeting).build()));

        // Non-matching keyword
        predicate = new PersonMeetingRemarkPredicate("Online meeting");
        assertFalse(predicate.test(new PersonBuilder().withMeeting(meeting).build()));
    }

    @Test
    public void testGetPredicateDescription() {
        PersonMeetingRemarkPredicate predicate = new PersonMeetingRemarkPredicate(meetingRemarkString);
        String expected = String.format("Meeting remark containing \"%1$s\"", meetingRemarkString);
        assertEquals(expected, predicate.getPredicateDescription());
    }

    @Test
    public void toStringMethod() {
        PersonMeetingRemarkPredicate predicate = new PersonMeetingRemarkPredicate(meetingRemarkString);

        String expected = PersonMeetingRemarkPredicate.class.getCanonicalName()
                + "{meeting remark=" + meetingRemarkString + "}";
        assertEquals(expected, predicate.toString());
    }
}
