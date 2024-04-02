package seedu.findvisor.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.findvisor.commons.util.AppUtil.checkArgument;
import static seedu.findvisor.logic.Messages.MESSAGE_SAFE_STRING_INPUT_CHARACTERS;

import seedu.findvisor.commons.util.StringUtil;


/**
 * Represents the remark about a Person in the address book.
 * Guarantees: immutable; a remark value is as declared in {@link #isValidRemark(String)}
 */
public class Remark {
    public static final String MESSAGE_CONSTRAINTS = "Remark should only contain "
            + MESSAGE_SAFE_STRING_INPUT_CHARACTERS + ".";

    public final String value;

    /**
     * Constructs a {@code Remark}.
     *
     * @param remark A valid remark.
     */
    public Remark(String remark) {
        requireNonNull(remark);
        checkArgument(isValidRemark(remark), MESSAGE_CONSTRAINTS);
        value = remark;
    }

    /**
     * Returns true if a given string is a valid remark.
     * A valid remark is one that is safe to accept as input.
     */
    public static boolean isValidRemark(String test) {
        return StringUtil.isSafeString(test);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Remark)) {
            return false;
        }

        Remark otherRemark = (Remark) other;
        return value.equals(otherRemark.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
