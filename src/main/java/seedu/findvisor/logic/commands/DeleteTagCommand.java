package seedu.findvisor.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.findvisor.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.findvisor.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import seedu.findvisor.commons.core.index.Index;
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
 * Deletes an existing tag of a person identified using it's displayed index
 * from the address book.
 */
public class DeleteTagCommand extends Command {

    public static final String COMMAND_WORD = "deletetag";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the tag associated with a particular person "
            + "identified by the index number used in the displayed person list.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + PREFIX_TAG + "TAG...\n"
            + "Example: " + COMMAND_WORD + " 1 t/PRUTravellerProtect";

    public static final String MESSAGE_DELETE_TAG_SUCCESS = "Deleted tag %1$s for Person: %2$s";
    public static final String MESSAGE_CANNOT_FIND_TAG = "There is no tag %1$s for Person: %2$s";

    private final Index targetIndex;
    private final Set<Tag> targetTags;

    /**
     * Creates an DeleteTagCommand to delete an existing tag with the person at the
     * specified {@code Index}
     */
    public DeleteTagCommand(Index targetIndex, Set<Tag> targetTags) {
        requireNonNull(targetIndex);
        requireNonNull(targetTags);

        this.targetIndex = targetIndex;
        this.targetTags = targetTags;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person personToEdit = lastShownList.get(targetIndex.getZeroBased());
        Set<Tag> tagsOfPerson = personToEdit.getTags();
        Set<Tag> missingTags = new HashSet<>();
        for (Tag tag : targetTags) {
            if (!tagsOfPerson.contains(tag)) {
                missingTags.add(tag);
            }
        }

        if (missingTags.size() > 0) {
            throw new CommandException(String.format(MESSAGE_CANNOT_FIND_TAG, missingTags, personToEdit.getName()));
        }

        Person editedPerson = createEditedPerson(personToEdit, targetTags);
        model.setPerson(personToEdit, editedPerson);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        return new CommandResult(String.format(MESSAGE_DELETE_TAG_SUCCESS, targetTags, editedPerson.getName()));
    }

    /**
     * Creates and returns a copy of {@code personToEdit} without {@code targetTags}.
     */
    private static Person createEditedPerson(Person personToEdit, Set<Tag> targetTags) {
        assert personToEdit != null;

        Name name = personToEdit.getName();
        Phone phone = personToEdit.getPhone();
        Email email = personToEdit.getEmail();
        Address address = personToEdit.getAddress();
        Set<Tag> tags = personToEdit.getTags();
        Optional<Meeting> meeting = personToEdit.getMeeting();
        Optional<Remark> remark = personToEdit.getRemark();

        Set<Tag> updatedTags = new HashSet<>();
        for (Tag tag : tags) {
            if (!targetTags.contains(tag)) {
                updatedTags.add(tag);
            }
        }

        return new Person(name, phone, email, address, updatedTags, meeting, remark);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof DeleteTagCommand)) {
            return false;
        }

        DeleteTagCommand otherDeleteTagCommand = (DeleteTagCommand) other;
        return targetIndex.equals(otherDeleteTagCommand.targetIndex)
                && targetTags.equals(otherDeleteTagCommand.targetTags);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("toDeleteTag", targetIndex)
                .add("tag", targetTags)
                .toString();
    }
}
