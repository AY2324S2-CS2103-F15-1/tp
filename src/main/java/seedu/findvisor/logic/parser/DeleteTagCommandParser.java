package seedu.findvisor.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.findvisor.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.findvisor.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.Set;

import seedu.findvisor.commons.core.index.Index;
import seedu.findvisor.logic.commands.DeleteTagCommand;
import seedu.findvisor.logic.parser.exceptions.ParseException;
import seedu.findvisor.model.tag.Tag;

/**
 * Parses input arguments and creates a new DeleteTagCommand object
 */
public class DeleteTagCommandParser implements Parser<DeleteTagCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the
     * DeleteTagCommand
     * and returns a DeleteTagCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public DeleteTagCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_TAG);
        Index index;
        Set<Tag> targetTags;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteTagCommand.MESSAGE_USAGE), pe);
        }

        if (!argMultimap.getValue(PREFIX_TAG).isPresent()) {
            // if there is not at least 1 tag present, throw exception
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteTagCommand.MESSAGE_USAGE));
        }

        try {
            targetTags = ParserUtil.parseTags(argMultimap.getAllValues(PREFIX_TAG));
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteTagCommand.MESSAGE_USAGE), pe);
        }

        return new DeleteTagCommand(index, targetTags);
    }

}
