package seedu.findvisor.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.findvisor.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.findvisor.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.findvisor.logic.commands.CommandTestUtil.createValidMeeting;
import static seedu.findvisor.logic.commands.CommandTestUtil.createValidMeetingNonEmptyRemark;
import static seedu.findvisor.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.findvisor.testutil.TypicalIndexes.INDEX_THIRD_PERSON;
import static seedu.findvisor.testutil.TypicalPersons.getTypicalAddressBook;

import java.time.LocalDateTime;
import java.util.Optional;

import org.junit.jupiter.api.Test;

import seedu.findvisor.commons.core.index.Index;
import seedu.findvisor.logic.Messages;
import seedu.findvisor.logic.commands.RescheduleCommand.EditMeetingDescriptor;
import seedu.findvisor.model.AddressBook;
import seedu.findvisor.model.Model;
import seedu.findvisor.model.ModelManager;
import seedu.findvisor.model.UserPrefs;
import seedu.findvisor.model.person.Meeting;
import seedu.findvisor.model.person.Person;
import seedu.findvisor.testutil.EditMeetingDescriptorBuilder;
import seedu.findvisor.testutil.MeetingBuilder;
import seedu.findvisor.testutil.PersonBuilder;

public class RescheduleCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private Meeting meetingNoRemark = createValidMeeting();
    private Meeting meetingWithRemark = createValidMeetingNonEmptyRemark();

    @Test
    public void execute_allFieldsSpecifiedUnfilteredList_success() {
        EditMeetingDescriptor descriptor = new EditMeetingDescriptorBuilder(meetingWithRemark).build();
        RescheduleCommand editCommand = new RescheduleCommand(INDEX_THIRD_PERSON, descriptor);
        Person editedPerson = new PersonBuilder(model.getFilteredPersonList()
                .get(INDEX_THIRD_PERSON.getZeroBased()))
                .withMeeting(Optional.of(meetingWithRemark)).build();

        String expectedMessage = String.format(RescheduleCommand.MESSAGE_RESCHEDULE_SUCCESS,
                editedPerson.getName(), Messages.format(editedPerson.getMeeting().get()));

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setPerson(model.getFilteredPersonList().get(INDEX_THIRD_PERSON.getZeroBased()), editedPerson);

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_someFieldsSpecifiedUnfilteredList_success() {
        EditMeetingDescriptor descriptor = new EditMeetingDescriptorBuilder()
                .withEnd(MeetingBuilder.DEFAULT_END).build();
        RescheduleCommand editCommand = new RescheduleCommand(INDEX_THIRD_PERSON, descriptor);
        Person personToEdit = model.getFilteredPersonList().get(INDEX_THIRD_PERSON.getZeroBased());
        Meeting editedMeeting = new MeetingBuilder(personToEdit.getMeeting().get())
                .withEnd(MeetingBuilder.DEFAULT_END).build();
        Person editedPerson = new PersonBuilder(personToEdit).withMeeting(Optional.of(editedMeeting)).build();

        String expectedMessage = String.format(RescheduleCommand.MESSAGE_RESCHEDULE_SUCCESS,
                editedPerson.getName(), Messages.format(editedPerson.getMeeting().get()));

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setPerson(model.getFilteredPersonList().get(INDEX_THIRD_PERSON.getZeroBased()), editedPerson);

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidPersonIndexFilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        EditMeetingDescriptor descriptor = new EditMeetingDescriptorBuilder(meetingWithRemark).build();
        RescheduleCommand editCommand = new RescheduleCommand(outOfBoundIndex, descriptor);
        assertCommandFailure(editCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_rescheduleEmptyMeeting_failure() {
        EditMeetingDescriptor descriptor = new EditMeetingDescriptorBuilder(meetingNoRemark).build();
        RescheduleCommand editCommand = new RescheduleCommand(INDEX_FIRST_PERSON, descriptor);
        assertCommandFailure(editCommand, model, RescheduleCommand.MESSAGE_CANNOT_RESCHEDULE_NON_EXISTENT_MEETING);
    }

    @Test
    public void execute_rescheduleMeetingToThePast_failure() {
        EditMeetingDescriptor descriptor = new EditMeetingDescriptorBuilder(meetingNoRemark)
                .withStart(LocalDateTime.now().minusDays(1)).build();
        RescheduleCommand editCommand = new RescheduleCommand(INDEX_THIRD_PERSON, descriptor);
        assertCommandFailure(editCommand, model, Messages.MESSAGE_CANNOT_SCHEDULE_MEETING_IN_THE_PAST);
    }

    @Test
    public void equals() {
        final EditMeetingDescriptor standardDescriptor = new EditMeetingDescriptorBuilder(meetingNoRemark).build();
        final RescheduleCommand standardCommand = new RescheduleCommand(INDEX_THIRD_PERSON, standardDescriptor);

        // same values -> returns true
        EditMeetingDescriptor copyDescriptor = new EditMeetingDescriptorBuilder(meetingNoRemark).build();
        RescheduleCommand commandWithSameValues = new RescheduleCommand(INDEX_THIRD_PERSON, copyDescriptor);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different index -> returns false
        RescheduleCommand commandWithDifferentIndex = new RescheduleCommand(INDEX_FIRST_PERSON, standardDescriptor);
        assertFalse(standardCommand.equals(commandWithDifferentIndex));

        // different descriptor -> returns false
        EditMeetingDescriptor editedDescriptor = new EditMeetingDescriptorBuilder(meetingWithRemark).build();
        RescheduleCommand commandWithDifferentDescriptor = new RescheduleCommand(INDEX_THIRD_PERSON, editedDescriptor);
        assertFalse(standardCommand.equals(commandWithDifferentDescriptor));
    }

    @Test
    public void toStringMethod() {
        Index index = Index.fromOneBased(1);
        EditMeetingDescriptor descriptor = new EditMeetingDescriptorBuilder(meetingWithRemark).build();
        RescheduleCommand command = new RescheduleCommand(index, descriptor);
        String expected = RescheduleCommand.class.getCanonicalName() + "{toReschedule=" + index
                + ", editMeetingDescriptor=" + descriptor.toString() + "}";
        assertEquals(expected, command.toString());
    }
}
