package seedu.findvisor.ui;

import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
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

    public static final String GENERAL_MESSAGE = "Below are the major commands for FINDvisor:\n";

    public static final String CLEAR_COMMAND_MESSAGE = ClearCommand.COMMAND_WORD
                + ": Clear all the contacts in FINDvisor\n";
    public static final String EXIT_COMMAND_MESSAGE = ExitCommand.COMMAND_WORD + ": Exit FINDvisor\n";
    public static final String LIST_COMMAND_MESSAGE = ListCommand.COMMAND_WORD + ": List all persons in FINDvisor.\n";
    public static final String HELP_COMMAND_MESSAGE = HelpCommand.COMMAND_WORD
                + ": Shows program usage instructions.\n";

    public static final String ADD_COMMAND_MESSAGE = AddCommand.COMMAND_WORD + ": Adds a person to FINDvisor.\n";
    public static final String DELETE_COMMAND_MESSAGE = DeleteCommand.COMMAND_WORD + ": Deletes the person identified"
                + " by the index number used in the displayed person list.\n";
    public static final String FIND_COMMAND_MESSAGE = FindCommand.COMMAND_WORD
                + ": Finds all persons whose information matches "
                + "the specified keywords (case-insensitive) of the specified category and "
                + "displays them as a list with index numbers.\n";
    public static final String EDIT_COMMAND_MESSAGE = EditCommand.COMMAND_WORD
                + ": Edits the details of the person identified "
                + "by the index number used in the displayed person list. "
                + "Existing values will be overwritten by the input values.\n";

    public static final String ADD_TAG_COMMAND_MESSAGE = AddTagCommand.COMMAND_WORD
                + ": Adds new tags to the person identified"
                + " by the index number used in the displayed person list.\n";
    public static final String DELETE_TAG_COMMAND_MESSAGE = DeleteTagCommand.COMMAND_WORD
                + ": Deletes the tag associated with "
                + "a person identified by the index number used in the displayed person list.\n";

    public static final String SCHEDULE_COMMAND_MESSAGE = ScheduleCommand.COMMAND_WORD
                + ": Schedules a meeting with the person "
                + "identified by the index number used in the displayed person list.\n";
    public static final String UNSCHEDULE_COMMAND_MESSAGE = UnscheduleCommand.COMMAND_WORD
                + ": Unschedules a meeting with the person "
                + "identified by the index number used in the displayed person list.\n";
    public static final String RESCHEDULE_COMMAND_MESSAGE = RescheduleCommand.COMMAND_WORD
                + ": Reschedules a meeting with the person "
                + "identified by the index number used in the displayed person list.\n";

    public static final String REMARK_COMMAND_MESSAGE = RemarkCommand.COMMAND_WORD
                + ": Updates a remark of the person identified "
                + "by the index number used in the displayed person list. "
                + "Existing values will be overwritten by the input.\n";

    private static final Logger logger = LogsCenter.getLogger(HelpWindow.class);
    private static final String FXML = "HelpWindow.fxml";

    @FXML
    private Button copyButton;

    @FXML
    private Label userGuideMessage;

    @FXML
    private Label helpMessage;

    /**
     * Creates a new HelpWindow.
     *
     * @param root Stage to use as the root of the HelpWindow.
     */
    public HelpWindow(Stage root) {
        super(FXML, root);
        
        String message = GENERAL_MESSAGE + ADD_COMMAND_MESSAGE + LIST_COMMAND_MESSAGE
                + EDIT_COMMAND_MESSAGE + FIND_COMMAND_MESSAGE + DELETE_COMMAND_MESSAGE + SCHEDULE_COMMAND_MESSAGE
                + UNSCHEDULE_COMMAND_MESSAGE + RESCHEDULE_COMMAND_MESSAGE + REMARK_COMMAND_MESSAGE
                + ADD_TAG_COMMAND_MESSAGE + DELETE_TAG_COMMAND_MESSAGE + CLEAR_COMMAND_MESSAGE
                + EXIT_COMMAND_MESSAGE + HELP_COMMAND_MESSAGE;
        userGuideMessage.setText(HELP_MESSAGE);
        helpMessage.setText(message);
    }

    /**
     * Creates a new HelpWindow.
     */
    public HelpWindow() {
        this(new Stage());
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
