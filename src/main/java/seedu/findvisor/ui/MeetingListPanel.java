package seedu.findvisor.ui;

import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.findvisor.commons.core.LogsCenter;
import seedu.findvisor.model.person.Person;

/**
 * Panel containing the list of persons with meeting today.
 */
public class MeetingListPanel extends UiPart<Region> {
    private static final String FXML = "MeetingListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(MeetingListPanel.class);

    @FXML
    private ListView<Person> meetingListView;

    /**
     * Creates a {@code MeetingListPanel} with the given {@code ObservableList}.
     */
    public MeetingListPanel(ObservableList<Person> personList) {
        super(FXML);
        meetingListView.setItems(personList);
        meetingListView.setCellFactory(listView -> new MeetingListCell());
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Person} using a {@code MeetingCard}.
     */
    class MeetingListCell extends ListCell<Person> {
        @Override
        protected void updateItem(Person person, boolean empty) {
            super.updateItem(person, empty);

            if (empty || person == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new MeetingCard(person).getRoot());
            }
        }
    }

}
