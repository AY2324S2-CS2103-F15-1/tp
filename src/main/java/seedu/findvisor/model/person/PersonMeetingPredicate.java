package seedu.findvisor.model.person;

import java.time.LocalDate;

import seedu.findvisor.commons.util.DateTimeUtil;
import seedu.findvisor.commons.util.ToStringBuilder;

/**
 * A predicate for evaluating if a {@link Person}'s meeting's start date or end date matches with the specified date.
 */
public class PersonMeetingPredicate implements PersonPredicate {
    private final LocalDate date;

    /**
     * Constructs an {@code PersonMeetingPredicate} with the specified keyword.
     *
     * @param date The {@link LocalDate} to be used to lookup against the person's meeting. The match is OR-based.
     */
    public PersonMeetingPredicate(LocalDate date) {
        this.date = date;
    }

    /**
     * Returns the description of this predicate, indicating the meeting date search criteria.
     *
     * @return A string describing the predicate
     */
    public String getPredicateDescription() {
        return String.format("Meeting on \"%1$s\"", DateTimeUtil.dateToString(date));
    }

    @Override
    public boolean test(Person person) {
        if (person.getMeeting().isEmpty()) {
            return false;
        }
        Meeting meeting = person.getMeeting().get();
        LocalDate meetingStartDate = meeting.start.toLocalDate();
        LocalDate meetingEndDate = meeting.end.toLocalDate();

        return DateTimeUtil.isSameDate(date, meetingStartDate) || DateTimeUtil.isSameDate(date, meetingEndDate);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof PersonMeetingPredicate)) {
            return false;
        }

        PersonMeetingPredicate otherMeetingContainsKeywordsPredicate = (PersonMeetingPredicate) other;
        return date.equals(otherMeetingContainsKeywordsPredicate.date);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("meeting", date).toString();
    }
}
