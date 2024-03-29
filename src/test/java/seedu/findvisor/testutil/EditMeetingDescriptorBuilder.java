package seedu.findvisor.testutil;

import java.time.LocalDateTime;

import seedu.findvisor.logic.commands.RescheduleCommand.EditMeetingDescriptor;
import seedu.findvisor.model.person.Meeting;

public class EditMeetingDescriptorBuilder {
    private EditMeetingDescriptor descriptor;

    public EditMeetingDescriptorBuilder() {
        descriptor = new EditMeetingDescriptor();
    }

    public EditMeetingDescriptorBuilder(EditMeetingDescriptor descriptor) {
        this.descriptor = new EditMeetingDescriptor(descriptor);
    }

    /**
     * Returns an {@code EditMeetingDescriptor} with fields containing {@code meeting}'s details
     */
    public EditMeetingDescriptorBuilder(Meeting meeting) {
        descriptor = new EditMeetingDescriptor();
        descriptor.setStart(meeting.getStart());
        descriptor.setEnd(meeting.getEnd());
        descriptor.setRemark(meeting.getRemark());
    }

    /**
     * Sets the {@code start} of the {@code EditMeetingDescriptor} that we are building.
     */
    public EditMeetingDescriptorBuilder withStart(LocalDateTime start) {
        descriptor.setStart(start);
        return this;
    }

    /**
     * Sets the {@code end} of the {@code EditMeetingDescriptor} that we are building.
     */
    public EditMeetingDescriptorBuilder withEnd(LocalDateTime end) {
        descriptor.setEnd(end);
        return this;
    }

    /**
     * Sets the {@code remark} of the {@code EditMeetingDescriptor} that we are building.
     */
    public EditMeetingDescriptorBuilder withRemark(String remark) {
        descriptor.setRemark(remark);
        return this;
    }

    public EditMeetingDescriptor build() {
        return descriptor;
    }
}
