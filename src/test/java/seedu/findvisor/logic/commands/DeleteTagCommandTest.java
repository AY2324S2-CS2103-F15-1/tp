package seedu.findvisor.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.findvisor.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.findvisor.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.findvisor.logic.commands.CommandTestUtil.showPersonAtIndex;
import static seedu.findvisor.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.findvisor.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.findvisor.testutil.TypicalIndexes.INDEX_THIRD_PERSON;
import static seedu.findvisor.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

import seedu.findvisor.commons.core.index.Index;
import seedu.findvisor.logic.Messages;
import seedu.findvisor.model.Model;
import seedu.findvisor.model.ModelManager;
import seedu.findvisor.model.UserPrefs;
import seedu.findvisor.model.person.Person;
import seedu.findvisor.model.tag.Tag;
import seedu.findvisor.testutil.PersonBuilder;

/**
 * Contains integration tests (interaction with the Model) and unit tests for
 * {@code DeleteTagCommand}.
 */
public class DeleteTagCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private Tag friendsTag = new Tag("friends");
    private Tag validTag = new Tag("valid");
    private Set<Tag> targetTag = new HashSet<>();

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Person personToEdit = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        targetTag.add(friendsTag);

        DeleteTagCommand deleteTagCommand = new DeleteTagCommand(INDEX_FIRST_PERSON, targetTag);

        String expectedMessage = String.format(DeleteTagCommand.MESSAGE_DELETE_TAG_SUCCESS, targetTag,
                personToEdit.getName());

        PersonBuilder personBuilder = new PersonBuilder(personToEdit).withTags();
        Person editedPerson = personBuilder.build();

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.setPerson(model.getFilteredPersonList().get(0), editedPerson);

        assertCommandSuccess(deleteTagCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_validIndexUnfilteredList_partialSuccess() {
        Person personToEdit = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        targetTag.add(friendsTag);
        targetTag.add(validTag);

        Set<Tag> friendsTagSet = new HashSet<>();
        friendsTagSet.add(friendsTag);
        Set<Tag> validTagSet = new HashSet<>();
        validTagSet.add(validTag);

        DeleteTagCommand deleteTagCommand = new DeleteTagCommand(INDEX_FIRST_PERSON, targetTag);

        String expectedMessage = String.format(DeleteTagCommand.MESSAGE_DELETE_PARTIAL_TAG, friendsTagSet,
                personToEdit.getName(), validTagSet);

        PersonBuilder personBuilder = new PersonBuilder(personToEdit).withTags();
        Person editedPerson = personBuilder.build();

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.setPerson(model.getFilteredPersonList().get(0), editedPerson);

        assertCommandSuccess(deleteTagCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_validIndexUnfilteredList_invalidTag() {
        Person personToEdit = model.getFilteredPersonList().get(INDEX_THIRD_PERSON.getZeroBased());
        targetTag.add(friendsTag);

        DeleteTagCommand deleteTagCommand = new DeleteTagCommand(INDEX_THIRD_PERSON, targetTag);

        String expectedMessage = String.format(DeleteTagCommand.MESSAGE_CANNOT_FIND_TAG, targetTag,
                personToEdit.getName());

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());

        assertCommandSuccess(deleteTagCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        targetTag.add(friendsTag);
        DeleteTagCommand deleteTagCommand = new DeleteTagCommand(outOfBoundIndex, targetTag);

        assertCommandFailure(deleteTagCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredList_success() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);

        Person personToEdit = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        targetTag.add(friendsTag);
        DeleteTagCommand deleteTagCommand = new DeleteTagCommand(INDEX_FIRST_PERSON, targetTag);

        String expectedMessage = String.format(DeleteTagCommand.MESSAGE_DELETE_TAG_SUCCESS, targetTag,
                personToEdit.getName());

        PersonBuilder personBuilder = new PersonBuilder(personToEdit).withTags();
        Person editedPerson = personBuilder.build();

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.setPerson(model.getFilteredPersonList().get(0), editedPerson);

        assertCommandSuccess(deleteTagCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);
        targetTag.add(friendsTag);

        Index outOfBoundIndex = INDEX_SECOND_PERSON;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getPersonList().size());

        DeleteTagCommand deleteTagCommand = new DeleteTagCommand(outOfBoundIndex, targetTag);

        assertCommandFailure(deleteTagCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        targetTag.add(friendsTag);
        DeleteTagCommand deleteFirstCommand = new DeleteTagCommand(INDEX_FIRST_PERSON, targetTag);
        DeleteTagCommand deleteSecondCommand = new DeleteTagCommand(INDEX_SECOND_PERSON, targetTag);

        // same object -> returns true
        assertTrue(deleteFirstCommand.equals(deleteFirstCommand));

        // same values -> returns true
        DeleteTagCommand deleteFirstCommandCopy = new DeleteTagCommand(INDEX_FIRST_PERSON, targetTag);
        assertTrue(deleteFirstCommand.equals(deleteFirstCommandCopy));

        // different types -> returns false
        assertFalse(deleteFirstCommand.equals(1));

        // null -> returns false
        assertFalse(deleteFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(deleteFirstCommand.equals(deleteSecondCommand));
    }

    @Test
    public void toStringMethod() {
        targetTag.add(friendsTag);
        DeleteTagCommand deleteTagCommand = new DeleteTagCommand(INDEX_FIRST_PERSON, targetTag);
        System.out.println(deleteTagCommand.toString());
        String expected = DeleteTagCommand.class.getCanonicalName() + "{toDeleteTag=" + INDEX_FIRST_PERSON
                + ", tag=" + targetTag + "}";
        assertEquals(expected, deleteTagCommand.toString());
    }

}
