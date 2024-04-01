package seedu.findvisor.testutil;

import java.time.LocalDateTime;

import seedu.findvisor.model.person.Meeting;

/**
 * A utility class to help with building Meeting objects.
 */
public class MeetingBuilder {
    public static final LocalDateTime DEFAULT_START = LocalDateTime.of(1990, 1, 1, 23, 59);
    public static final LocalDateTime DEFAULT_END = LocalDateTime.of(2100, 12, 31, 23, 59);
    public static final String DEFAULT_REMARK = "Meeting remark";

    private LocalDateTime start;
    private LocalDateTime end;
    private String remark;

    /**
     * Creates a {@code MeetingBuilder} with the default details.
     */
    public MeetingBuilder() {
        start = DEFAULT_START;
        end = DEFAULT_END;
        remark = DEFAULT_REMARK;
    }

    /**
     * Initializes the MeetingBuilder with the data of {@code meetingToCopy}.
     */
    public MeetingBuilder(Meeting meetingToCopy) {
        this.start = meetingToCopy.getStart();
        this.end = meetingToCopy.getEnd();
        this.remark = meetingToCopy.getRemark();
    }

    /**
     * Sets the start datetime of the {@code Meeting} that we are building.
     */
    public MeetingBuilder withStart(LocalDateTime start) {
        this.start = start;
        return this;
    }

    /**
     * Sets the end datetime of the {@code Meeting} that we are building.
     */
    public MeetingBuilder withEnd(LocalDateTime end) {
        this.end = end;
        return this;
    }

    /**
     * Sets the remark of the {@code Meeting} that we are building.
     */
    public MeetingBuilder withRemark(String remark) {
        this.remark = remark;
        return this;
    }

    public Meeting build() {
        return new Meeting(start, end, remark);
    }
}
