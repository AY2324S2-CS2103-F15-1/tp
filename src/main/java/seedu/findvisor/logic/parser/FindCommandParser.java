package seedu.findvisor.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.findvisor.commons.util.DateTimeUtil.DATE_PATTERN;
import static seedu.findvisor.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.findvisor.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.findvisor.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.findvisor.logic.parser.CliSyntax.PREFIX_MEETING;
import static seedu.findvisor.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.findvisor.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.findvisor.logic.parser.CliSyntax.PREFIX_TAG;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.stream.Stream;

import seedu.findvisor.commons.util.DateTimeUtil;
import seedu.findvisor.logic.commands.FindCommand;
import seedu.findvisor.logic.parser.exceptions.ParseException;
import seedu.findvisor.model.person.PersonAddressPredicate;
import seedu.findvisor.model.person.PersonEmailPredicate;
import seedu.findvisor.model.person.PersonMeetingPredicate;
import seedu.findvisor.model.person.PersonNamePredicate;
import seedu.findvisor.model.person.PersonPhonePredicate;
import seedu.findvisor.model.tag.PersonTagsPredicate;

/**
 * Parses input arguments and creates a new FindCommand object
 */
public class FindCommandParser implements Parser<FindCommand> {
    private final Prefix[] searchPrefixes = {PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL,
        PREFIX_ADDRESS, PREFIX_MEETING, PREFIX_TAG};

    /**
     * Parses the given {@code String} of arguments in the context of the FindCommand
     * and returns a FindCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public FindCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, searchPrefixes);

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_NAME, PREFIX_PHONE,
                PREFIX_EMAIL, PREFIX_ADDRESS, PREFIX_MEETING);

        if (!isSinglePrefixTypePresent(argMultimap, searchPrefixes)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }

        String userInput;
        if (argMultimap.getValue(PREFIX_NAME).isPresent()) {
            userInput = getUserInput(argMultimap, PREFIX_NAME);
            return new FindCommand(new PersonNamePredicate(userInput));
        }
        if (argMultimap.getValue(PREFIX_EMAIL).isPresent()) {
            userInput = getUserInput(argMultimap, PREFIX_EMAIL);
            return new FindCommand(new PersonEmailPredicate(userInput));
        }
        if (argMultimap.getValue(PREFIX_PHONE).isPresent()) {
            userInput = getUserInput(argMultimap, PREFIX_PHONE);
            return new FindCommand(new PersonPhonePredicate(userInput));
        }
        if (argMultimap.getValue(PREFIX_ADDRESS).isPresent()) {
            userInput = getUserInput(argMultimap, PREFIX_ADDRESS);
            return new FindCommand(new PersonAddressPredicate(userInput));
        }
        if (argMultimap.getValue(PREFIX_MEETING).isPresent()) {
            userInput = getUserInput(argMultimap, PREFIX_MEETING);
            LocalDate meetingDate = verifyMeetingDateFormat(userInput);
            return new FindCommand(new PersonMeetingPredicate(meetingDate));
        }
        argMultimap.verifyNoBlankPrefixValueFor(PREFIX_TAG);
        List<String> tagsKeywords = argMultimap.getAllValues(PREFIX_TAG);
        return new FindCommand(new PersonTagsPredicate(tagsKeywords));
    }

    /**
     * Returns true if exactly one prefix type is present in the given {@code ArgumentMultimap}.
     * @param argMultimap The {@link ArgumentMultimap} to check for the presence of prefixes.
     * @param prefixes A varargs array of {@link Prefix} objects to be checked in the {@code argMultimap}.
     * @return {@code true} if exactly one of the specified prefixes is present in the {@code ArgumentMultimap}.
     */
    private boolean isSinglePrefixTypePresent(ArgumentMultimap argMultimap, Prefix... prefixes) {
        long prefixCount = Stream.of(prefixes)
                .filter(prefix -> argMultimap.getValue(prefix).isPresent())
                .count();
        return prefixCount == 1;
    }

    /**
     * Retrieves the user input associated with a specific prefix. If specified prefix has a blank value,
     * then a ParseException is thrown.
     *
     * @param argMultimap The {@link ArgumentMultimap} to get the specified prefix value.
     * @param prefix The {@link Prefix} specified by the user.
     * @return The user input string associated with the prefix.
     * @throws ParseException If the prefix value is blank.
     */
    private String getUserInput(ArgumentMultimap argMultimap, Prefix prefix) throws ParseException {
        argMultimap.verifyNoBlankPrefixValueFor(prefix);
        return argMultimap.getValue(prefix).get();
    }

    /**
     * Validates and parses the meeting date string to a {@code LocalDate} object.
     *
     * @param userInput The date string to parse.
     * @return Parsed {@code LocalDate} object.
     * @throws ParseException If the date string does not conform to the expected pattern {@code DATE_PATTERN}.
     */
    private LocalDate verifyMeetingDateFormat(String userInput) throws ParseException {
        try {
            LocalDate meetingDate = DateTimeUtil.parseDateString(userInput);
            return meetingDate;
        } catch (DateTimeParseException e) {
            throw new ParseException(String.format(FindCommand.MESSAGE_INVALID_DATE_FORMAT, userInput, DATE_PATTERN));
        }
    }
}
