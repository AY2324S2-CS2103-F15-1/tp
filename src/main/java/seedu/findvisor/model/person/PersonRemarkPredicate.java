package seedu.findvisor.model.person;

import seedu.findvisor.commons.util.StringUtil;
import seedu.findvisor.commons.util.ToStringBuilder;

/**
 * A predicate for evaluating if a {@link Person}'s remark contains (case-insensitive) a given keyword.
 */
public class PersonRemarkPredicate implements PersonPredicate {
    private final String keyword;

    /**
     * Constructs an {@code PersonRemarkPredicate} with the specified keyword.
     *
     * @param keyword The keyword to be matched against the person's remark. The match is case-insensitive.
     */
    public PersonRemarkPredicate(String keyword) {
        this.keyword = keyword;
    }

    /**
     * Returns the description of this predicate, indicating the remark keyword criteria.
     *
     * @return A string describing the predicate.
     */
    public String getPredicateDescription() {
        return String.format("Person remark containing \"%1$s\"", keyword);
    }

    @Override
    public boolean test(Person person) {
        if (person.getRemark().isEmpty()) {
            return false;
        }
        String personRemark = person.getRemark().get().value;
        return StringUtil.containsIgnoreCase(personRemark, keyword);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof PersonRemarkPredicate)) {
            return false;
        }

        PersonRemarkPredicate otherPersonRemarkPredicate = (PersonRemarkPredicate) other;
        return keyword.equals(otherPersonRemarkPredicate.keyword);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("person remark", keyword).toString();
    }
}
