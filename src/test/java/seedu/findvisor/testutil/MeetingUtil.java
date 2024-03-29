package seedu.findvisor.testutil;

import static seedu.findvisor.commons.util.DateTimeUtil.dateTimeToInputString;
import static seedu.findvisor.logic.parser.CliSyntax.PREFIX_END_DATETIME;
import static seedu.findvisor.logic.parser.CliSyntax.PREFIX_MEETING_REMARK;
import static seedu.findvisor.logic.parser.CliSyntax.PREFIX_START_DATETIME;

import seedu.findvisor.logic.commands.RescheduleCommand.EditMeetingDescriptor;

/**
 * A utility class for Meeting.
 */
public class MeetingUtil {
    /**
     * Returns the part of command string for the given {@code EditMeetingDescriptor}'s details.
     */
    public static String getEditMeetingDescriptorDetails(EditMeetingDescriptor descriptor) {
        StringBuilder sb = new StringBuilder();
        descriptor.getStart().ifPresent(start -> sb.append(PREFIX_START_DATETIME).append(dateTimeToInputString(start)).append(" "));
        descriptor.getEnd().ifPresent(end -> sb.append(PREFIX_END_DATETIME).append(dateTimeToInputString(end)).append(" "));
        descriptor.getRemark().ifPresent(remark -> sb.append(PREFIX_MEETING_REMARK).append(remark).append(" "));
        return sb.toString();
    }
}
