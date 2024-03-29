package seedu.findvisor.logic.parser;

import static seedu.findvisor.commons.util.DateTimeUtil.dateTimeToString;
import static seedu.findvisor.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.findvisor.logic.commands.CommandTestUtil.createValidMeeting;
import static seedu.findvisor.logic.commands.CommandTestUtil.createValidMeetingNonEmptyRemark;
import static seedu.findvisor.logic.parser.CliSyntax.PREFIX_END_DATETIME;
import static seedu.findvisor.logic.parser.CliSyntax.PREFIX_MEETING_REMARK;
import static seedu.findvisor.logic.parser.CliSyntax.PREFIX_START_DATETIME;
import static seedu.findvisor.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.findvisor.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.findvisor.testutil.TypicalIndexes.INDEX_THIRD_PERSON;

import org.junit.jupiter.api.Test;

import seedu.findvisor.commons.core.index.Index;
import seedu.findvisor.logic.Messages;
import seedu.findvisor.logic.commands.RescheduleCommand;
import seedu.findvisor.logic.commands.RescheduleCommand.EditMeetingDescriptor;
import seedu.findvisor.model.person.Meeting;
import seedu.findvisor.testutil.EditMeetingDescriptorBuilder;
import seedu.findvisor.testutil.MeetingUtil;

public class RescheduleCommandParserTest {
    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, RescheduleCommand.MESSAGE_USAGE);

    private RescheduleCommandParser parser = new RescheduleCommandParser();
    private Meeting meetingNoRemark = createValidMeeting();
    private Meeting meetingWithRemark = createValidMeetingNonEmptyRemark();

    @Test
    public void parse_missingParts_failure() {
        // no index specified
        assertParseFailure(parser, dateTimeToString(meetingNoRemark.start), MESSAGE_INVALID_FORMAT);

        // no field specified
        assertParseFailure(parser, "1", Messages.MESSAGE_REQUIRE_AT_LEAST_ONE_FIELD);

        // no index and no field specified
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidPreamble_failure() {
        // invalid index
        assertParseFailure(parser, "a" + " " + dateTimeToString(meetingNoRemark.start), MESSAGE_INVALID_FORMAT);

        // invalid index
        assertParseFailure(parser, "-1" + " " + dateTimeToString(meetingNoRemark.start), MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid start datetime
        assertParseFailure(parser, "1" + " " + "22-02-1922T14:00" + " " + dateTimeToString(meetingNoRemark.end),
                MESSAGE_INVALID_FORMAT);

        // invalid end datetime
        assertParseFailure(parser, "1" + " " + dateTimeToString(meetingNoRemark.start) + " " + "22-02-1922T15:00",
                MESSAGE_INVALID_FORMAT);

        // invalid start and end datetime
        assertParseFailure(parser, "1" + " " + "22-02-1922T18:00" + " " + "22-02-1922T15:00",
                MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_allFieldsSpecified_success() {
        Index targetIndex = INDEX_THIRD_PERSON;
        EditMeetingDescriptor descriptor = new EditMeetingDescriptorBuilder(meetingWithRemark).build();
        RescheduleCommand expectedCommand = new RescheduleCommand(targetIndex, descriptor);
        String userInput = targetIndex.getOneBased() + " " + MeetingUtil.getEditMeetingDescriptorDetails(descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_oneFieldSpecified_success() {
        Index targetIndex = INDEX_THIRD_PERSON;

        // start datetime
        EditMeetingDescriptor descriptor = new EditMeetingDescriptorBuilder().withStart(meetingNoRemark.start).build();
        RescheduleCommand expectedCommand = new RescheduleCommand(targetIndex, descriptor);
        String userInput = targetIndex.getOneBased() + " " + MeetingUtil.getEditMeetingDescriptorDetails(descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // end datetime
        descriptor = new EditMeetingDescriptorBuilder().withEnd(meetingNoRemark.end).build();
        expectedCommand = new RescheduleCommand(targetIndex, descriptor);
        userInput = targetIndex.getOneBased() + " " + MeetingUtil.getEditMeetingDescriptorDetails(descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // remark
        descriptor = new EditMeetingDescriptorBuilder().withRemark(meetingWithRemark.remark).build();
        expectedCommand = new RescheduleCommand(targetIndex, descriptor);
        userInput = targetIndex.getOneBased() + " " + MeetingUtil.getEditMeetingDescriptorDetails(descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_multipleRepeatedFields_failure() {
        // multiple all fields
        Index targetIndex = INDEX_THIRD_PERSON;
        EditMeetingDescriptor descriptor = new EditMeetingDescriptorBuilder(meetingWithRemark).build();
        String userInput = targetIndex.getOneBased() + " " + MeetingUtil.getEditMeetingDescriptorDetails(descriptor)
                + " " + MeetingUtil.getEditMeetingDescriptorDetails(descriptor);
        assertParseFailure(parser, userInput, Messages.getErrorMessageForDuplicatePrefixes(
                    PREFIX_START_DATETIME, PREFIX_END_DATETIME, PREFIX_MEETING_REMARK));

        // multiple start datetime
        descriptor = new EditMeetingDescriptorBuilder().withStart(meetingNoRemark.start).build();
        userInput = targetIndex.getOneBased() + " " + MeetingUtil.getEditMeetingDescriptorDetails(descriptor)
                + " " + MeetingUtil.getEditMeetingDescriptorDetails(descriptor);
        assertParseFailure(parser, userInput, Messages.getErrorMessageForDuplicatePrefixes(PREFIX_START_DATETIME));

        // multiple end datetime
        descriptor = new EditMeetingDescriptorBuilder().withEnd(meetingNoRemark.end).build();
        userInput = targetIndex.getOneBased() + " " + MeetingUtil.getEditMeetingDescriptorDetails(descriptor)
                + " " + MeetingUtil.getEditMeetingDescriptorDetails(descriptor);
        assertParseFailure(parser, userInput, Messages.getErrorMessageForDuplicatePrefixes(PREFIX_END_DATETIME));

        // multiple remark
        descriptor = new EditMeetingDescriptorBuilder().withRemark(meetingWithRemark.remark).build();
        userInput = targetIndex.getOneBased() + " " + MeetingUtil.getEditMeetingDescriptorDetails(descriptor)
                + " " + MeetingUtil.getEditMeetingDescriptorDetails(descriptor);
        assertParseFailure(parser, userInput, Messages.getErrorMessageForDuplicatePrefixes(PREFIX_MEETING_REMARK));
    }
}
