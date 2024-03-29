package seedu.findvisor.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.findvisor.logic.Messages.MESSAGE_CANNOT_SCHEDULE_MEETING_IN_THE_PAST;
import static seedu.findvisor.logic.parser.CliSyntax.PREFIX_END_DATETIME;
import static seedu.findvisor.logic.parser.CliSyntax.PREFIX_MEETING_REMARK;
import static seedu.findvisor.logic.parser.CliSyntax.PREFIX_START_DATETIME;
import static seedu.findvisor.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import seedu.findvisor.commons.core.index.Index;
import seedu.findvisor.commons.util.CollectionUtil;
import seedu.findvisor.commons.util.DateTimeUtil;
import seedu.findvisor.commons.util.ToStringBuilder;
import seedu.findvisor.logic.Messages;
import seedu.findvisor.logic.commands.exceptions.CommandException;
import seedu.findvisor.model.Model;
import seedu.findvisor.model.person.Address;
import seedu.findvisor.model.person.Email;
import seedu.findvisor.model.person.Meeting;
import seedu.findvisor.model.person.Name;
import seedu.findvisor.model.person.Person;
import seedu.findvisor.model.person.Phone;
import seedu.findvisor.model.person.Remark;
import seedu.findvisor.model.tag.Tag;

/**
 * Reschedules a meeting with a person.
 */
public class RescheduleCommand extends Command {
    public static final String COMMAND_WORD = "reschedule";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Reschedules a meeting with the person identified "
            + "by the index number used in the displayed person list.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + PREFIX_START_DATETIME + "START DATETIME] "
            + "[" + PREFIX_END_DATETIME + "END DATETIME] "
            + "[" + PREFIX_MEETING_REMARK + "REMARK]\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_END_DATETIME + "22-02-2024T16:00 "
            + PREFIX_MEETING_REMARK + "Extended by one hour";

    public static final String MESSAGE_RESCHEDULE_SUCCESS = "Rescheduled meeting with %1$s.\n%2$s";
    public static final String MESSAGE_CANNOT_RESCHEDULE_NON_EXISTENT_MEETING = "To reschedule a meeting"
            + " you must first schedule a meeting with the contact!";

    private final Index targetIndex;
    private final EditMeetingDescriptor editMeetingDescriptor;

    /**
     * Creates an RescheduleCommand to reschedule a meeting with the specified {@code Person}
     */
    public RescheduleCommand(Index targetIndex, EditMeetingDescriptor editMeetingDescriptor) {
        requireNonNull(editMeetingDescriptor);
        this.targetIndex = targetIndex;
        this.editMeetingDescriptor = editMeetingDescriptor;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person personToEdit = lastShownList.get(targetIndex.getZeroBased());
        if (personToEdit.getMeeting().isEmpty()) {
            throw new CommandException(MESSAGE_CANNOT_RESCHEDULE_NON_EXISTENT_MEETING);
        }
        Person editedPerson = createEditedPerson(personToEdit, editMeetingDescriptor);

        if (editMeetingDescriptor.getStart().isPresent()
                && !DateTimeUtil.isAfterCurrentDateTime(editMeetingDescriptor.getStart().get())) {
            throw new CommandException(MESSAGE_CANNOT_SCHEDULE_MEETING_IN_THE_PAST);
        }

        model.setPerson(personToEdit, editedPerson);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);

        return new CommandResult(String.format(MESSAGE_RESCHEDULE_SUCCESS,
                editedPerson.getName(),
                Messages.format(editedPerson.getMeeting().get())));
    }

    /**
     * Creates and returns a {@code Person} with the same details of {@code personToEdit}
     * with the meeting details edited.
     *
     * @param personToEdit Person to edit. Must have a meeting scheduled.
     * @param editMeetingDescriptor Details to edit the meeting with.
     * @return Person with the meeting details edited.
     */
    private static Person createEditedPerson(Person personToEdit, EditMeetingDescriptor editMeetingDescriptor) {
        assert personToEdit != null && editMeetingDescriptor != null;

        Name name = personToEdit.getName();
        Phone phone = personToEdit.getPhone();
        Email email = personToEdit.getEmail();
        Address address = personToEdit.getAddress();
        Set<Tag> tags = personToEdit.getTags();
        Optional<Remark> remark = personToEdit.getRemark();

        // We should only reach this point if the person has a meeting scheduled.
        assert personToEdit.getMeeting().isPresent();
        Meeting currentMeeting = personToEdit.getMeeting().get();

        LocalDateTime start = editMeetingDescriptor.getStart().orElse(currentMeeting.getStart());
        LocalDateTime end = editMeetingDescriptor.getEnd().orElse(currentMeeting.getEnd());
        String remarkString = editMeetingDescriptor.getRemark().orElse(currentMeeting.getRemark());

        Meeting meeting = new Meeting(start, end, remarkString);
        return new Person(name, phone, email, address, tags, Optional.of(meeting), remark);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof RescheduleCommand)) {
            return false;
        }

        RescheduleCommand otherScheduleCommand = (RescheduleCommand) other;
        return targetIndex.equals(otherScheduleCommand.targetIndex)
                && editMeetingDescriptor.equals(otherScheduleCommand.editMeetingDescriptor);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("toReschedule", targetIndex)
                .add("editMeetingDescriptor", editMeetingDescriptor)
                .toString();
    }

    /**
     * Stores the details to edit the meeting with. Each non-empty field value will replace the
     * corresponding field value of the meeting.
     */
    public static class EditMeetingDescriptor {
        private LocalDateTime start;
        private LocalDateTime end;
        private String remark;

        public EditMeetingDescriptor() {}

        /**
         * Copy constructor.
         */
        public EditMeetingDescriptor(EditMeetingDescriptor toCopy) {
            setStart(toCopy.start);
            setEnd(toCopy.end);
            setRemark(toCopy.remark);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(start, end, remark);
        }

        public void setStart(LocalDateTime start) {
            this.start = start;
        }

        public Optional<LocalDateTime> getStart() {
            return Optional.ofNullable(start);
        }

        public void setEnd(LocalDateTime end) {
            this.end = end;
        }

        public Optional<LocalDateTime> getEnd() {
            return Optional.ofNullable(end);
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }

        public Optional<String> getRemark() {
            return Optional.ofNullable(remark);
        }

        @Override
        public boolean equals(Object other) {
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof EditMeetingDescriptor)) {
                return false;
            }

            EditMeetingDescriptor otherEditMeetingDescriptor = (EditMeetingDescriptor) other;
            return Objects.equals(start, otherEditMeetingDescriptor.start)
                    && Objects.equals(end, otherEditMeetingDescriptor.end)
                    && Objects.equals(remark, otherEditMeetingDescriptor.remark);
        }

        @Override
        public String toString() {
            return new ToStringBuilder(this)
                    .add("start", start)
                    .add("end", end)
                    .add("remark", remark)
                    .toString();
        }
    }
}
