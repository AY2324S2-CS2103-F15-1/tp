package seedu.findvisor.logic.parser;

import static seedu.findvisor.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.findvisor.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.findvisor.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.findvisor.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.findvisor.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import org.junit.jupiter.api.Test;

import seedu.findvisor.commons.core.index.Index;
import seedu.findvisor.logic.commands.DeleteTagCommand;
import seedu.findvisor.model.tag.Tag;

public class DeleteTagCommandParserTest {
    private DeleteTagCommandParser parser = new DeleteTagCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ", String.format(
                MESSAGE_INVALID_COMMAND_FORMAT,
                DeleteTagCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsDeleteTagCommand() {
        Index targetIndex = INDEX_FIRST_PERSON;
        Tag targetTag = new Tag("friends");
        DeleteTagCommand expectedDeleteTagCommand = new DeleteTagCommand(targetIndex, targetTag);
        assertParseSuccess(parser, targetIndex.getOneBased() + " " + PREFIX_TAG + "friends", expectedDeleteTagCommand);
    }
}
