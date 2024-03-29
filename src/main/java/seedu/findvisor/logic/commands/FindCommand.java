package seedu.findvisor.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.findvisor.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.findvisor.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.findvisor.logic.parser.CliSyntax.PREFIX_MEETING;
import static seedu.findvisor.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.findvisor.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.findvisor.logic.parser.CliSyntax.PREFIX_TAG;

import seedu.findvisor.commons.util.ToStringBuilder;
import seedu.findvisor.model.Model;
import seedu.findvisor.model.person.PersonPredicate;

/**
 * Finds persons based on search criteria of the specified category.
 * Only exactly one category of the following can be specified, either name, email, phone, address, meeting or tags.
 * Keyword matching is case insensitive.
 * For meeting dates, user input will be validated to match date format.
 */
public class FindCommand extends Command {

    public static final String COMMAND_WORD = "find";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all persons whose information matches "
            + "the specified keywords (case-insensitive) of the specified category"
            + "and displays them as a list with index numbers.\n"
            + "Parameters: "
            + PREFIX_NAME + "NAME | "
            + PREFIX_EMAIL + "EMAIL | "
            + PREFIX_PHONE + "PHONE | "
            + PREFIX_ADDRESS + "ADDRESS | "
            + PREFIX_MEETING + "MEETING DATE | "
            + PREFIX_TAG + "TAG...\n"
            + "Example: " + COMMAND_WORD + " t/PRUActiveCash t/friends";

    public static final String MESSAGE_FIND_COMMAND_RESULT = "%1$d persons listed with %2$s!";
    public static final String MESSAGE_INVALID_DATE_FORMAT = "Invalid date format supplied: \"%1$s\"\n" 
            + "Please use %2$s format.\n" 
            + MESSAGE_USAGE;

    private final PersonPredicate predicate;

    public FindCommand(PersonPredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredPersonList(predicate);
        return new CommandResult(String.format(MESSAGE_FIND_COMMAND_RESULT, 
                model.getFilteredPersonList().size(), predicate.getPredicateDescription()));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof FindCommand)) {
            return false;
        }

        FindCommand otherFindCommand = (FindCommand) other;
        return predicate.equals(otherFindCommand.predicate);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("predicate", predicate)
                .toString();
    }
}
