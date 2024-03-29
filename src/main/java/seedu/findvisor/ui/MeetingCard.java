package seedu.findvisor.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.findvisor.logic.Messages;
import seedu.findvisor.model.person.Person;

/**
 * An UI component that displays brief information of a {@code Person} that has a meeting today.
 */
public class MeetingCard extends UiPart<Region> {

    private static final String FXML = "MeetingListCard.fxml";

    public final Person person;

    @FXML
    private HBox cardPane;
    @FXML
    private Label name;
    @FXML
    private Label phone;
    @FXML
    private Label meeting;

    /**
     * Creates a {@code MeetingCard} with the given {@code Person} information.
     */
    public MeetingCard(Person person) {
        super(FXML);
        this.person = person;
        name.setText(person.getName().fullName);
        phone.setText(person.getPhone().value);
        meeting.setText(person.getMeeting().map(Messages::format).orElse("No Scheduled Meeting"));
    }
}
