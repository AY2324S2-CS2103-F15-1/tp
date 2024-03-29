package seedu.findvisor.model.person;

import java.time.LocalDate;
import java.util.Optional;

import seedu.findvisor.commons.util.ToStringBuilder;

/**
 * A predicate for evaluating if a {@link Person}'s scheduled meeting at the current date.
 * This is used to filter for persons with meetings today.
 */
public class PersonMeetingTodayPredicate implements PersonPredicate {
    private final LocalDate todaysDate;

    /**
     * Constructs an {@code PersonMeetingTodayPredicate} with the today's date.
     */
    public PersonMeetingTodayPredicate() {
        this.todaysDate = LocalDate.now();
    }

    /**
     * Returns the description of this predicate, indicating the phone keyword criteria.
     *
     * @return A string describing the predicate
     */
    // TODO: DateTimeUtils.toDateString
    public String getPredicateDescription() {
        return String.format("Date = \"%1$s\"", todaysDate);
    }

    @Override
    public boolean test(Person person) {
        Optional<Meeting> personMeeting = person.getMeeting();
        return personMeeting.map(meeting -> meeting.start.toLocalDate().equals(todaysDate)).orElse(false);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof PersonMeetingTodayPredicate)) {
            return false;
        }

        PersonMeetingTodayPredicate otherPersonMeetingTodayPredicate = (PersonMeetingTodayPredicate) other;
        return todaysDate.equals(otherPersonMeetingTodayPredicate.todaysDate);
    }

    // TODO: DateTimeUtils.toDateString
    @Override
    public String toString() {
        return new ToStringBuilder(this).add("date", todaysDate).toString();
    }
}
