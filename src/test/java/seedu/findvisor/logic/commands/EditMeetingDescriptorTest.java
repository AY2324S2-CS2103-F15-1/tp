package seedu.findvisor.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.findvisor.logic.commands.CommandTestUtil.createValidMeeting;

import org.junit.jupiter.api.Test;

import seedu.findvisor.logic.commands.RescheduleCommand.EditMeetingDescriptor;
import seedu.findvisor.model.person.Meeting;
import seedu.findvisor.testutil.EditMeetingDescriptorBuilder;

public class EditMeetingDescriptorTest {
    @Test
    public void equals() {
        Meeting meeting = createValidMeeting();
        EditMeetingDescriptor descriptor = new EditMeetingDescriptorBuilder(meeting).build();
        // same object -> returns true
        assertTrue(descriptor.equals(descriptor));

        // null -> returns false
        assertFalse(descriptor.equals(null));

        // different types -> returns false
        assertFalse(descriptor.equals(5));

        // different remark -> returns false
        EditMeetingDescriptor editedDescriptor = new EditMeetingDescriptorBuilder(descriptor)
                .withRemark("Different remark").build();
        assertFalse(descriptor.equals(editedDescriptor));

        // different start -> returns false
        editedDescriptor = new EditMeetingDescriptorBuilder(descriptor).withStart(meeting.getStart().plusDays(1)).build();
        assertFalse(descriptor.equals(editedDescriptor));

        // different end -> returns false
        editedDescriptor = new EditMeetingDescriptorBuilder(descriptor).withEnd(meeting.getEnd().plusDays(1)).build();
        assertFalse(descriptor.equals(editedDescriptor));
    }

    @Test
    public void toStringMethod() {
        EditMeetingDescriptor editMeetingDescriptor = new EditMeetingDescriptor();
        String expected = EditMeetingDescriptor.class.getCanonicalName() + "{start="
                + editMeetingDescriptor.getStart().orElse(null) + ", end="
                + editMeetingDescriptor.getEnd().orElse(null) + ", remark="
                + editMeetingDescriptor.getRemark().orElse(null) + "}";
        assertEquals(expected, editMeetingDescriptor.toString());
    }
}
