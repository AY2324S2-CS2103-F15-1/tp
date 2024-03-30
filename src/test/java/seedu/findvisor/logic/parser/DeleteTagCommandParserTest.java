package seedu.findvisor.logic.parser;

import static seedu.findvisor.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.findvisor.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.findvisor.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.findvisor.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.findvisor.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

import seedu.findvisor.commons.core.index.Index;
import seedu.findvisor.logic.commands.DeleteTagCommand;
import seedu.findvisor.model.tag.Tag;

public class DeleteTagCommandParserTest {
    private static final String TAGNAME = "friends";
    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteTagCommand.MESSAGE_USAGE);
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
        Set<Tag> targetTag = new HashSet<>();
        Tag tag = new Tag(TAGNAME);
        targetTag.add(tag);
        DeleteTagCommand expectedDeleteTagCommand = new DeleteTagCommand(targetIndex, targetTag);
        assertParseSuccess(parser, targetIndex.getOneBased() + " " + PREFIX_TAG + TAGNAME, expectedDeleteTagCommand);
    }

    @Test
    public void parse_invalidArgs_returnsIndexError() {
        assertParseFailure(parser, "-1", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidArgs_returnsTagError() {
        assertParseFailure(parser, "1 something", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidArgs_returnsTagMissing() {
        assertParseFailure(parser, "1", MESSAGE_INVALID_FORMAT);
    }
}
