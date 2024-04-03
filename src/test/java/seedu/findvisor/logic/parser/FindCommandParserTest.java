package seedu.findvisor.logic.parser;

import static seedu.findvisor.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.findvisor.logic.commands.CommandTestUtil.ADDRESS_DESC_BOB;
import static seedu.findvisor.logic.commands.CommandTestUtil.EMAIL_DESC_AMY;
import static seedu.findvisor.logic.commands.CommandTestUtil.EMAIL_DESC_BOB;
import static seedu.findvisor.logic.commands.CommandTestUtil.EMPTY_ADDRESS_DESC;
import static seedu.findvisor.logic.commands.CommandTestUtil.EMPTY_EMAIL_DESC;
import static seedu.findvisor.logic.commands.CommandTestUtil.EMPTY_NAME_DESC;
import static seedu.findvisor.logic.commands.CommandTestUtil.EMPTY_PHONE_DESC;
import static seedu.findvisor.logic.commands.CommandTestUtil.EMPTY_TAG_DESC;
import static seedu.findvisor.logic.commands.CommandTestUtil.INCOMPLETE_TAG_DESC;
import static seedu.findvisor.logic.commands.CommandTestUtil.MEETING_DATE_DESC;
import static seedu.findvisor.logic.commands.CommandTestUtil.MEETING_REMARK_DESC;
import static seedu.findvisor.logic.commands.CommandTestUtil.NAME_DESC_AMY;
import static seedu.findvisor.logic.commands.CommandTestUtil.NAME_DESC_BOB;
import static seedu.findvisor.logic.commands.CommandTestUtil.PHONE_DESC_AMY;
import static seedu.findvisor.logic.commands.CommandTestUtil.PHONE_DESC_BOB;
import static seedu.findvisor.logic.commands.CommandTestUtil.REMARK;
import static seedu.findvisor.logic.commands.CommandTestUtil.REMARK_DESC;
import static seedu.findvisor.logic.commands.CommandTestUtil.TAG_DESC_FRIEND;
import static seedu.findvisor.logic.commands.CommandTestUtil.TAG_DESC_HUSBAND;
import static seedu.findvisor.logic.commands.CommandTestUtil.VALID_DATE;
import static seedu.findvisor.logic.commands.CommandTestUtil.VALID_MEETING_REMARK;
import static seedu.findvisor.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.findvisor.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.findvisor.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.findvisor.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.findvisor.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.findvisor.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.findvisor.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

import seedu.findvisor.logic.Messages;
import seedu.findvisor.logic.commands.FindCommand;
import seedu.findvisor.model.person.PersonAddressPredicate;
import seedu.findvisor.model.person.PersonEmailPredicate;
import seedu.findvisor.model.person.PersonMeetingPredicate;
import seedu.findvisor.model.person.PersonMeetingRemarkPredicate;
import seedu.findvisor.model.person.PersonNamePredicate;
import seedu.findvisor.model.person.PersonPhonePredicate;
import seedu.findvisor.model.person.PersonRemarkPredicate;
import seedu.findvisor.model.tag.PersonTagsPredicate;

public class FindCommandParserTest {

    private FindCommandParser parser = new FindCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "  ", String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
    }


    @Test
    public void parse_validArgs_returnsFindCommand() {
        // parse name
        FindCommand expectedFindCommand =
                new FindCommand(new PersonNamePredicate("Bob Choo"));
        assertParseSuccess(parser, NAME_DESC_BOB, expectedFindCommand);

        // multiple whitespaces before and after name keyword
        String paddedKeyword = "\n \t " + NAME_DESC_BOB + "\n \t";
        assertParseSuccess(parser, paddedKeyword, expectedFindCommand);

        // parse email
        expectedFindCommand = new FindCommand(new PersonEmailPredicate("amy@example.com"));
        assertParseSuccess(parser, EMAIL_DESC_AMY, expectedFindCommand);

        // parse phone
        expectedFindCommand = new FindCommand(new PersonPhonePredicate("81234567"));
        assertParseSuccess(parser, PHONE_DESC_BOB, expectedFindCommand);

        // parse address
        expectedFindCommand = new FindCommand(new PersonAddressPredicate("Block 123, Bobby Street 3"));
        assertParseSuccess(parser, ADDRESS_DESC_BOB, expectedFindCommand);

        // parse meeting date
        expectedFindCommand = new FindCommand(new PersonMeetingPredicate(VALID_DATE));
        assertParseSuccess(parser, MEETING_DATE_DESC, expectedFindCommand);

        // parse remark
        expectedFindCommand = new FindCommand(new PersonRemarkPredicate(REMARK));
        assertParseSuccess(parser, REMARK_DESC, expectedFindCommand);

        // parse meeting remark
        expectedFindCommand = new FindCommand(new PersonMeetingRemarkPredicate(VALID_MEETING_REMARK));
        assertParseSuccess(parser, MEETING_REMARK_DESC, expectedFindCommand);

        // parse multiple tags
        expectedFindCommand = new FindCommand(new PersonTagsPredicate(
                Arrays.asList(new String[]{"friend", "husband"})));
        assertParseSuccess(parser, TAG_DESC_FRIEND + TAG_DESC_HUSBAND, expectedFindCommand);
    }

    @Test
    public void parse_repeatedNonTagValue_failure() {
        assertParseFailure(parser, NAME_DESC_BOB + NAME_DESC_AMY,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_NAME));

        assertParseFailure(parser, EMAIL_DESC_AMY + EMAIL_DESC_BOB,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_EMAIL));

        assertParseFailure(parser, PHONE_DESC_AMY + PHONE_DESC_BOB,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_PHONE));
    }

    @Test
    public void parse_invalidArgs_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE);

        // Empty string
        assertParseFailure(parser, "", expectedMessage);

        // Whitespaces only
        assertParseFailure(parser, "     ", expectedMessage);

        // Invalid prefix
        assertParseFailure(parser, "@/test", expectedMessage);

        // Invalid and valid prefix
        assertParseFailure(parser, "@/test " + NAME_DESC_AMY, expectedMessage);

        // Multiple valid prefixes
        assertParseFailure(parser, EMAIL_DESC_AMY + " " + NAME_DESC_AMY, expectedMessage);
    }

    @Test
    public void parse_emptyArgs_failure() {
        // Empty name prefix
        assertParseFailure(parser, EMPTY_NAME_DESC, String.format(Messages.MESSAGE_EMPTY_FIELD, PREFIX_NAME));

        // Empty email prefix
        assertParseFailure(parser, EMPTY_EMAIL_DESC, String.format(Messages.MESSAGE_EMPTY_FIELD, PREFIX_EMAIL));

        // Empty phone prefix
        assertParseFailure(parser, EMPTY_PHONE_DESC, String.format(Messages.MESSAGE_EMPTY_FIELD, PREFIX_PHONE));

        // Empty address prefix
        assertParseFailure(parser, EMPTY_ADDRESS_DESC, String.format(Messages.MESSAGE_EMPTY_FIELD, PREFIX_ADDRESS));

        // Empty tag prefix
        assertParseFailure(parser, EMPTY_TAG_DESC, String.format(Messages.MESSAGE_EMPTY_FIELD, PREFIX_TAG));

        // One empty tag prefix
        // Empty tag prefix
        assertParseFailure(parser, INCOMPLETE_TAG_DESC, String.format(Messages.MESSAGE_EMPTY_FIELD, PREFIX_TAG));
    }
}
