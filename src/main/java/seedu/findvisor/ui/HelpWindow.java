package seedu.findvisor.ui;

import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import seedu.findvisor.commons.core.LogsCenter;
import seedu.findvisor.logic.commands.AddCommand;
import seedu.findvisor.logic.commands.AddTagCommand;
import seedu.findvisor.logic.commands.ClearCommand;
import seedu.findvisor.logic.commands.DeleteCommand;
import seedu.findvisor.logic.commands.DeleteTagCommand;
import seedu.findvisor.logic.commands.EditCommand;
import seedu.findvisor.logic.commands.ExitCommand;
import seedu.findvisor.logic.commands.FindCommand;
import seedu.findvisor.logic.commands.HelpCommand;
import seedu.findvisor.logic.commands.ListCommand;
import seedu.findvisor.logic.commands.RemarkCommand;
import seedu.findvisor.logic.commands.RescheduleCommand;
import seedu.findvisor.logic.commands.ScheduleCommand;
import seedu.findvisor.logic.commands.UnscheduleCommand;

/**
 * Controller for a help page
 */
public class HelpWindow extends UiPart<Stage> {

    public static final String USERGUIDE_URL = "https://ay2324s2-cs2103-f15-1.github.io/tp/UserGuide.html";
    public static final String HELP_MESSAGE = "Refer to the user guide: " + USERGUIDE_URL + "\n\n";

    public static final String CLEAR_COMMAND_MESSAGE = "Clear all the contacts in FINDvisor.";
    public static final String EXIT_COMMAND_MESSAGE = "Exit FINDvisor.";
    public static final String LIST_COMMAND_MESSAGE = "List all persons in FINDvisor.";
    public static final String HELP_COMMAND_MESSAGE = "Shows program usage instructions.";

    public static final String ADD_COMMAND_MESSAGE = "Adds a person to FINDvisor.";
    public static final String DELETE_COMMAND_MESSAGE = "Deletes the person identified."
                + " by the index number used in the displayed person list.";
    public static final String FIND_COMMAND_MESSAGE = "Finds all persons whose information matches "
                + "the specified keywords (case-insensitive) of the specified category and "
                + "displays them as a list with index numbers.";
    public static final String EDIT_COMMAND_MESSAGE = "Edits the details of the person identified "
                + "by the index number used in the displayed person list. "
                + "Existing values will be overwritten by the input values.";

    public static final String ADD_TAG_COMMAND_MESSAGE = "Adds new tags to the person identified"
                + " by the index number used in the displayed person list.";
    public static final String DELETE_TAG_COMMAND_MESSAGE = "Deletes the tag associated with "
                + "a person identified by the index number used in the displayed person list.";

    public static final String SCHEDULE_COMMAND_MESSAGE = "Schedules a meeting with the person "
                + "identified by the index number used in the displayed person list.";
    public static final String UNSCHEDULE_COMMAND_MESSAGE = "Unschedules a meeting with the person "
                + "identified by the index number used in the displayed person list.";
    public static final String RESCHEDULE_COMMAND_MESSAGE = "Reschedules a meeting with the person "
                + "identified by the index number used in the displayed person list.";

    public static final String REMARK_COMMAND_MESSAGE = "Updates a remark of the person identified "
                + "by the index number used in the displayed person list. "
                + "Existing values will be overwritten by the input.";

    private static final Logger logger = LogsCenter.getLogger(HelpWindow.class);
    private static final String FXML = "HelpWindow.fxml";

    @FXML
    private Button copyButton;

    @FXML
    private Label userGuideMessage;

    @FXML
    private GridPane helpTable;

    /**
     * Creates a new HelpWindow.
     *
     * @param root Stage to use as the root of the HelpWindow.
     */
    public HelpWindow(Stage root) {
        super(FXML, root);
        helpTable.setPadding(new Insets(10, 10, 10, 10));
        userGuideMessage.setText(HELP_MESSAGE);
        int i = 0;
        helpTable.addRow(i++, createHeaderLabel("Command"), createHeaderLabel("Description"));
        helpTable.addRow(i++, createCommandLabel(AddCommand.COMMAND_WORD),
                createDescriptionLabel(ADD_COMMAND_MESSAGE));
        helpTable.addRow(i++, createCommandLabel(ListCommand.COMMAND_WORD),
                createDescriptionLabel(LIST_COMMAND_MESSAGE));
        helpTable.addRow(i++, createCommandLabel(EditCommand.COMMAND_WORD),
                createDescriptionLabel(EDIT_COMMAND_MESSAGE));
        helpTable.addRow(i++, createCommandLabel(FindCommand.COMMAND_WORD),
                createDescriptionLabel(FIND_COMMAND_MESSAGE));
        helpTable.addRow(i++, createCommandLabel(DeleteCommand.COMMAND_WORD),
                createDescriptionLabel(DELETE_COMMAND_MESSAGE));
        helpTable.addRow(i++, createCommandLabel(ScheduleCommand.COMMAND_WORD),
                createDescriptionLabel(SCHEDULE_COMMAND_MESSAGE));
        helpTable.addRow(i++, createCommandLabel(UnscheduleCommand.COMMAND_WORD),
                createDescriptionLabel(UNSCHEDULE_COMMAND_MESSAGE));
        helpTable.addRow(i++, createCommandLabel(RescheduleCommand.COMMAND_WORD),
                createDescriptionLabel(RESCHEDULE_COMMAND_MESSAGE));
        helpTable.addRow(i++, createCommandLabel(RemarkCommand.COMMAND_WORD),
                createDescriptionLabel(REMARK_COMMAND_MESSAGE));
        helpTable.addRow(i++, createCommandLabel(AddTagCommand.COMMAND_WORD),
                createDescriptionLabel(ADD_TAG_COMMAND_MESSAGE));
        helpTable.addRow(i++, createCommandLabel(DeleteTagCommand.COMMAND_WORD),
                createDescriptionLabel(DELETE_TAG_COMMAND_MESSAGE));
        helpTable.addRow(i++, createCommandLabel(ClearCommand.COMMAND_WORD),
                createDescriptionLabel(CLEAR_COMMAND_MESSAGE));
        helpTable.addRow(i++, createCommandLabel(ExitCommand.COMMAND_WORD),
                createDescriptionLabel(EXIT_COMMAND_MESSAGE));
        helpTable.addRow(i, createCommandLabel(HelpCommand.COMMAND_WORD),
                createDescriptionLabel(HELP_COMMAND_MESSAGE));
    }

    /**
     * Creates a new HelpWindow.
     */
    public HelpWindow() {
        this(new Stage());
    }

    /**
     * Creates a {@code Label} with text wrap based on {@code text}
     */
    private Label createTextWrapLabel(String text) {
        Label label = new Label(text);
        label.setWrapText(true);
        label.getStyleClass().add("cell");
        return label;
    }

    /**
     * Creates a header {@code Label} based on {@code header}
     */
    private Label createHeaderLabel(String header) {
        Label label = createTextWrapLabel(header);
        label.getStyleClass().add("header-cell");
        return label;
    }

    /**
     * Creates a command {@code Label} based on {@code command}
     */
    private Label createCommandLabel(String command) {
        Label label = createTextWrapLabel(command);
        label.getStyleClass().add("command-cell");
        return label;
    }

    /**
     * Creates a description {@code Label} based on {@code description}
     */
    private Label createDescriptionLabel(String description) {
        Label label = createTextWrapLabel(description);
        label.getStyleClass().add("description-cell");
        return label;
    }

    /**
     * Shows the help window.
     * @throws IllegalStateException
     *     <ul>
     *         <li>
     *             if this method is called on a thread other than the JavaFX Application Thread.
     *         </li>
     *         <li>
     *             if this method is called during animation or layout processing.
     *         </li>
     *         <li>
     *             if this method is called on the primary stage.
     *         </li>
     *         <li>
     *             if {@code dialogStage} is already showing.
     *         </li>
     *     </ul>
     */
    public void show() {
        logger.fine("Showing help page about the application.");
        getRoot().show();
        getRoot().centerOnScreen();
    }

    /**
     * Returns true if the help window is currently being shown.
     */
    public boolean isShowing() {
        return getRoot().isShowing();
    }

    /**
     * Hides the help window.
     */
    public void hide() {
        getRoot().hide();
    }

    /**
     * Focuses on the help window.
     */
    public void focus() {
        getRoot().requestFocus();
    }

    /**
     * Copies the URL to the user guide to the clipboard.
     */
    @FXML
    private void copyUrl() {
        final Clipboard clipboard = Clipboard.getSystemClipboard();
        final ClipboardContent url = new ClipboardContent();
        url.putString(USERGUIDE_URL);
        clipboard.setContent(url);
    }
}
