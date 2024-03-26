package seedu.findvisor.model.util;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.findvisor.logic.commands.CommandTestUtil.VALID_MEETING_END_STR;
import static seedu.findvisor.logic.commands.CommandTestUtil.VALID_MEETING_REMARK;
import static seedu.findvisor.logic.commands.CommandTestUtil.VALID_MEETING_START_STR;
import static seedu.findvisor.logic.commands.CommandTestUtil.VALID_MEETING_WITH_REMARK;

import java.util.Optional;

import org.junit.jupiter.api.Test;

import seedu.findvisor.model.person.Meeting;

public class SampleDataUtilTest {

    @Test
    public void convertToMeeting_returnsOptionalMeeting() {
        Optional<Meeting> validMeetingWithRemark = Optional.of(VALID_MEETING_WITH_REMARK);
        Optional<Meeting> convertedStringArrayToMeeting = SampleDataUtil.convertToMeeting(new String[]
                {VALID_MEETING_START_STR, VALID_MEETING_END_STR, VALID_MEETING_REMARK});
        assertTrue(validMeetingWithRemark.equals(convertedStringArrayToMeeting));
    }
}
