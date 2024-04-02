package seedu.findvisor.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.findvisor.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.findvisor.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.findvisor.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.findvisor.logic.parser.CliSyntax.PREFIX_MEETING;
import static seedu.findvisor.logic.parser.CliSyntax.PREFIX_MEETING_REMARK;
import static seedu.findvisor.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.findvisor.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.findvisor.logic.parser.CliSyntax.PREFIX_REMARK;
import static seedu.findvisor.logic.parser.CliSyntax.PREFIX_TAG;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Stream;

import seedu.findvisor.logic.commands.FindCommand;
import seedu.findvisor.logic.parser.exceptions.ParseException;
import seedu.findvisor.model.person.PersonAddressPredicate;
import seedu.findvisor.model.person.PersonEmailPredicate;
import seedu.findvisor.model.person.PersonMeetingPredicate;
import seedu.findvisor.model.person.PersonMeetingRemarkPredicate;
import seedu.findvisor.model.person.PersonNamePredicate;
import seedu.findvisor.model.person.PersonPhonePredicate;
import seedu.findvisor.model.person.PersonRemarkPredicate;
import seedu.findvisor.model.tag.PersonTagsPredicate;

/**
 * Parses input arguments and creates a new FindCommand object
 */
public class FindCommandParser implements Parser<FindCommand> {
    private final Prefix[] searchPrefixes = {PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL,
        PREFIX_ADDRESS, PREFIX_MEETING, PREFIX_REMARK, PREFIX_MEETING_REMARK, PREFIX_TAG};

    /**
     * Parses the given {@code String} of arguments in the context of the FindCommand
     * and returns a FindCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public FindCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, searchPrefixes);

        // Check if there is any text preceding valid prefix
        if (!argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_NAME, PREFIX_PHONE,
                PREFIX_EMAIL, PREFIX_ADDRESS, PREFIX_REMARK, PREFIX_MEETING_REMARK, PREFIX_MEETING);

        Prefix presentPrefix = findSinglePresentPrefixType(argMultimap, searchPrefixes);
        argMultimap.verifyNoBlankPrefixValueFor(presentPrefix);

        return prepareFindCommand(argMultimap, presentPrefix);
    }

    /**
     * Checks through the {@link ArgumentMultimap} to identify the single prefix type.
     * It is expected that exactly one prefix is present based on the given prefixes;
     * otherwise, a {@link ParseException} is thrown.
     * @param argMultimap The {@link ArgumentMultimap} to check for the presence of prefixes types.
     * @param prefixes The prefixes to search for.
     * @return The single present {@link Prefix} type.
     * @throws ParseException if none or more than one prefix is present.
     */
    private Prefix findSinglePresentPrefixType(ArgumentMultimap argMultimap, Prefix... prefixes) throws ParseException {
        assert argMultimap != null && prefixes != null;

        Prefix[] presentPrefixes = Stream.of(prefixes)
                .filter(prefix -> argMultimap.getValue(prefix).isPresent())
                .toArray(Prefix[]::new);

        if (presentPrefixes.length != 1) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }
        return presentPrefixes[0];
    }

    /**
     * Creates a {@link FindCommand} based on the given prefix and its value in {@link ArgumentMultimap}.
     *
     * @param argMultimap The {@link ArgumentMultimap} containing the prefixes and corresponding values.
     * @param prefix The detected prefix to define the search type.
     * @return The {@link FindCommand} created for the detected prefix.
     */
    private FindCommand prepareFindCommand(ArgumentMultimap argMultimap, Prefix prefix) throws ParseException {
        assert argMultimap != null && prefix != null;

        if (prefix.equals(PREFIX_TAG)) {
            List<String> tagsKeywords = argMultimap.getAllValues(PREFIX_TAG);
            return new FindCommand(new PersonTagsPredicate(tagsKeywords));
        }

        String userKeyword = argMultimap.getValue(prefix).get();
        if (prefix.equals(PREFIX_MEETING)) {
            LocalDate meetingDate = ParserUtil.parseMeetingDate(userKeyword);
            return new FindCommand(new PersonMeetingPredicate(meetingDate));
        }
        if (prefix.equals(PREFIX_NAME)) {
            return new FindCommand(new PersonNamePredicate(userKeyword));
        }
        if (prefix.equals(PREFIX_PHONE)) {
            return new FindCommand(new PersonPhonePredicate(userKeyword));
        }
        if (prefix.equals(PREFIX_EMAIL)) {
            return new FindCommand(new PersonEmailPredicate(userKeyword));
        }
        if (prefix.equals(PREFIX_REMARK)) {
            return new FindCommand(new PersonRemarkPredicate(userKeyword));
        }
        if (prefix.equals(PREFIX_MEETING_REMARK)) {
            return new FindCommand(new PersonMeetingRemarkPredicate(userKeyword));
        }
        return new FindCommand(new PersonAddressPredicate(userKeyword));
    }
}
