package seedu.findvisor.model.person;

import seedu.findvisor.commons.util.StringUtil;
import seedu.findvisor.commons.util.ToStringBuilder;

/**
 * A predicate for evaluating if a {@link Person}'s meeting remark contains (case-insensitive) a given keyword.
 */
public class PersonMeetingRemarkPredicate implements PersonPredicate {
    private final String keyword;

    /**
     * Constructs an {@code PersonAddressPredicate} with the specified keyword.
     *
     * @param keyword The keyword to be matched against the person's meeting remark. The match is case-insensitive.
     */
    public PersonMeetingRemarkPredicate(String keyword) {
        this.keyword = keyword;
    }

    /**
     * Returns the description of this predicate, indicating the meeting remark keyword criteria.
     *
     * @return A string describing the predicate.
     */
    public String getPredicateDescription() {
        return String.format("Meeting remark containing \"%1$s\"", keyword);
    }

    @Override
    public boolean test(Person person) {
        if (person.getMeeting().isEmpty()) {
            return false;
        }
        Meeting personMeeting = person.getMeeting().get();
        return StringUtil.containsIgnoreCase(personMeeting.getRemark(), keyword);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof PersonMeetingRemarkPredicate)) {
            return false;
        }

        PersonMeetingRemarkPredicate otherPersonMeetingRemarkPredicate = (PersonMeetingRemarkPredicate) other;
        return keyword.equals(otherPersonMeetingRemarkPredicate.keyword);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("meeting remark", keyword).toString();
    }
}
