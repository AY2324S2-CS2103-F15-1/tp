package seedu.findvisor.model.person;

import java.time.LocalDate;
import java.util.Optional;

import seedu.findvisor.commons.util.DateTimeUtil;
import seedu.findvisor.commons.util.ToStringBuilder;

/**
 * A predicate for evaluating if a {@link Person}'s scheduled meeting at the current date.
 * This is used to filter for persons with meetings today.
 */
public class PersonTodayMeetingsPredicate implements PersonPredicate {
    private final LocalDate todaysDate;

    /**
     * Constructs an {@code PersonTodayMeetingsPredicate} with the today's date.
     */
    public PersonTodayMeetingsPredicate() {
        this.todaysDate = LocalDate.now();
    }

    /**
     * Returns the description of this predicate, indicating the meetings occurring on the current system's date.
     *
     * @return A string describing the predicate
     */
    public String getPredicateDescription() {
        return String.format("Today's meeting on \"%1$s\"", DateTimeUtil.dateToString(todaysDate));
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
        if (!(other instanceof PersonTodayMeetingsPredicate)) {
            return false;
        }

        PersonTodayMeetingsPredicate otherPersonTodayMeetingsPredicate = (PersonTodayMeetingsPredicate) other;
        return todaysDate.equals(otherPersonTodayMeetingsPredicate.todaysDate);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("date", DateTimeUtil.dateToString(todaysDate)).toString();
    }
}
