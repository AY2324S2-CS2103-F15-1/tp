package seedu.findvisor.model.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import seedu.findvisor.testutil.Assert;

public class RemarkTest {
    public static final String REMARK_VALUE = "Wants to afford a car after 5 years of working";

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Remark(null));
    }

    @Test
    public void constructor_invalidRemark_throwsIllegalArgumentException() {
        String invalidRemark = "/";
        Assert.assertThrows(IllegalArgumentException.class, () -> new Remark(invalidRemark));
    }

    @Test
    public void isValidRemark() {
        // null remark
        assertThrows(NullPointerException.class, () -> Remark.isValidRemark(null));

        // invalid remarks
        assertFalse(Remark.isValidRemark("first remark r/second remark"));
        assertFalse(Remark.isValidRemark("♫☼ hello"));
        assertFalse(Remark.isValidRemark("不能使用华文字体"));

        // valid remarks
        assertTrue(Remark.isValidRemark("")); // accept empty string, although remark will never be empty.
        assertTrue(Remark.isValidRemark("this is a normal remark"));
        assertTrue(Remark.isValidRemark("1"));
        assertTrue(Remark.isValidRemark("%"));
        assertTrue(Remark.isValidRemark("Mix of characters with 123 $%^"));
    }

    @Test
    public void equals() {
        Remark remark = new Remark(REMARK_VALUE);

        // same values -> returns true
        assertTrue(remark.equals(new Remark(REMARK_VALUE)));

        // same object -> returns true
        assertTrue(remark.equals(remark));

        // null -> returns false
        assertFalse(remark.equals(null));

        // different types -> returns false
        assertFalse(remark.equals(5.0f));

        // different values -> returns false
        assertFalse(remark.equals(new Remark("Plans to start a family by age of 32")));
    }
}
