package seedu.findvisor.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;

import org.junit.jupiter.api.Test;

import seedu.findvisor.testutil.PersonBuilder;

public class PersonRemarkPredicateTest {
    private String remarkString = "Working as SWE, wants to BTO with David";
    private Optional<Remark> remark = Optional.of(new Remark(remarkString));

    @Test
    public void equals() {
        String firstPredicateKeyword = "Working as SWE, wants to BTO with David";
        String secondPredicateKeyword = "Wants to move to the new house by next January";

        PersonRemarkPredicate firstPredicate = new PersonRemarkPredicate(firstPredicateKeyword);
        PersonRemarkPredicate secondPredicate = new PersonRemarkPredicate(secondPredicateKeyword);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        PersonRemarkPredicate firstPredicateCopy = new PersonRemarkPredicate(firstPredicateKeyword);
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
        PersonRemarkPredicate predicate = new PersonRemarkPredicate(remarkString);
        assertTrue(predicate.test(new PersonBuilder().withRemark(remark).build()));

        // Substring match
        predicate = new PersonRemarkPredicate("SWE");
        assertTrue(predicate.test(new PersonBuilder().withRemark(remark).build()));

        // Mixed-case keyword
        predicate = new PersonRemarkPredicate("bto with David");
        assertTrue(predicate.test(new PersonBuilder().withRemark(remark).build()));
    }

    @Test
    public void test_remarkDoesNotContainsKeyword_returnsFalse() {
        // Non-matching keyword
        PersonRemarkPredicate predicate = new PersonRemarkPredicate("Tom");
        assertFalse(predicate.test(new PersonBuilder().withRemark(remark).build()));

        // Non-matching keyword
        predicate = new PersonRemarkPredicate("Wants to fund his new business");
        assertFalse(predicate.test(new PersonBuilder().withRemark(remark).build()));
    }

    @Test
    public void testGetPredicateDescription() {
        PersonRemarkPredicate predicate = new PersonRemarkPredicate(remarkString);
        String expected = String.format("Person remark containing \"%1$s\"", remarkString);
        assertEquals(expected, predicate.getPredicateDescription());
    }

    @Test
    public void toStringMethod() {
        PersonRemarkPredicate predicate = new PersonRemarkPredicate(remarkString);

        String expected = PersonRemarkPredicate.class.getCanonicalName() + "{person remark=" + remarkString + "}";
        assertEquals(expected, predicate.toString());
    }
}
