package seedu.findvisor.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.findvisor.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.findvisor.logic.parser.CliSyntax.PREFIX_END_DATETIME;
import static seedu.findvisor.logic.parser.CliSyntax.PREFIX_MEETING_REMARK;
import static seedu.findvisor.logic.parser.CliSyntax.PREFIX_START_DATETIME;

import java.time.LocalDateTime;

import seedu.findvisor.commons.core.index.Index;
import seedu.findvisor.logic.Messages;
import seedu.findvisor.logic.commands.RescheduleCommand;
import seedu.findvisor.logic.commands.RescheduleCommand.EditMeetingDescriptor;
import seedu.findvisor.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new RescheduleCommand object
 */
public class RescheduleCommandParser implements Parser<RescheduleCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the RescheduleCommand
     * and returns an RescheduleCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public RescheduleCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_START_DATETIME, PREFIX_END_DATETIME, PREFIX_MEETING_REMARK);

        Index index;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, RescheduleCommand.MESSAGE_USAGE), pe);
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_START_DATETIME, PREFIX_END_DATETIME, PREFIX_MEETING_REMARK);

        EditMeetingDescriptor editMeetingDescriptor = new EditMeetingDescriptor();

        if (argMultimap.getValue(PREFIX_START_DATETIME).isPresent()) {
            LocalDateTime start = ParserUtil.parseMeetingDateTime(argMultimap.getValue(PREFIX_START_DATETIME).get());
            editMeetingDescriptor.setStart(start);
        }
        if (argMultimap.getValue(PREFIX_END_DATETIME).isPresent()) {
            LocalDateTime end = ParserUtil.parseMeetingDateTime(argMultimap.getValue(PREFIX_END_DATETIME).get());
            editMeetingDescriptor.setEnd(end);
        }
        if (argMultimap.getValue(PREFIX_MEETING_REMARK).isPresent()) {
            String remark = ParserUtil.parseMeetingRemark(argMultimap.getValue(PREFIX_MEETING_REMARK).get());
            editMeetingDescriptor.setRemark(remark);
        }

        if (!editMeetingDescriptor.isAnyFieldEdited()) {
            throw new ParseException(Messages.MESSAGE_REQUIRE_AT_LEAST_ONE_FIELD);
        }

        return new RescheduleCommand(index, editMeetingDescriptor);
    }
}
