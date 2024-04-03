package seedu.findvisor.ui;

import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
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

    public static final String GENERAL_MESSAGE = "Below are the major commands for FINDvisor:\n\n";

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
    private TableView<CommandHelpDescriptor> helpTable;

    @FXML
    private TableColumn<CommandHelpDescriptor, String> commandColumn;

    @FXML
    private TableColumn<CommandHelpDescriptor, String> descriptionColumn;

    /**
     * Creates a new HelpWindow.
     *
     * @param root Stage to use as the root of the HelpWindow.
     */
    public HelpWindow(Stage root) {
        super(FXML, root);
        // Solution below adapted from https://stackoverflow.com/a/11186231
        helpTable.setColumnResizePolicy(helpTable.CONSTRAINED_RESIZE_POLICY);
        commandColumn.setCellValueFactory(new PropertyValueFactory<>("command"));
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        helpTable.setPadding(new Insets(10, 10, 10, 10));
        userGuideMessage.setText(HELP_MESSAGE);
    }
    /**
     * Creates a new HelpWindow.
     */
    public HelpWindow() {
        this(new Stage());
    }

    /**
     * Initializes the help window by setting up command descriptions in a table view. It configures
     * the description column to support text wrapping for long descriptions using a custom cell factory.
     */
    @FXML
    public void initialize() {
        List<CommandHelpDescriptor> commands = prepareCommandHelpList();

        // @@author Javiery3889-reused
        // Reused from https://stackoverflow.com/a/22732723
        // with minor modifications
        descriptionColumn.setCellFactory(tc -> {
            TableCell<CommandHelpDescriptor, String> cell = new TableCell<>();
            Text text = new Text();
            text.setFill(Color.WHITE);
            cell.setGraphic(text);
            cell.setPadding(new Insets(5, 5, 5, 5));
            text.wrappingWidthProperty().bind(descriptionColumn.widthProperty());
            text.textProperty().bind(cell.itemProperty());
            return cell;
        });
        // @@author

        helpTable.getItems().addAll(commands);
    }

    private List<CommandHelpDescriptor> prepareCommandHelpList() {
        return Arrays.asList(
            new CommandHelpDescriptor(AddCommand.COMMAND_WORD, ADD_COMMAND_MESSAGE),
            new CommandHelpDescriptor(ListCommand.COMMAND_WORD, LIST_COMMAND_MESSAGE),
            new CommandHelpDescriptor(EditCommand.COMMAND_WORD, EDIT_COMMAND_MESSAGE),
            new CommandHelpDescriptor(FindCommand.COMMAND_WORD, FIND_COMMAND_MESSAGE),
            new CommandHelpDescriptor(DeleteCommand.COMMAND_WORD, DELETE_COMMAND_MESSAGE),
            new CommandHelpDescriptor(ScheduleCommand.COMMAND_WORD, SCHEDULE_COMMAND_MESSAGE),
            new CommandHelpDescriptor(UnscheduleCommand.COMMAND_WORD, UNSCHEDULE_COMMAND_MESSAGE),
            new CommandHelpDescriptor(RescheduleCommand.COMMAND_WORD, RESCHEDULE_COMMAND_MESSAGE),
            new CommandHelpDescriptor(RemarkCommand.COMMAND_WORD, REMARK_COMMAND_MESSAGE),
            new CommandHelpDescriptor(AddTagCommand.COMMAND_WORD, ADD_TAG_COMMAND_MESSAGE),
            new CommandHelpDescriptor(DeleteTagCommand.COMMAND_WORD, DELETE_TAG_COMMAND_MESSAGE),
            new CommandHelpDescriptor(ClearCommand.COMMAND_WORD, CLEAR_COMMAND_MESSAGE),
            new CommandHelpDescriptor(ExitCommand.COMMAND_WORD, EXIT_COMMAND_MESSAGE),
            new CommandHelpDescriptor(HelpCommand.COMMAND_WORD, HELP_COMMAND_MESSAGE)
        );
    }

    /**
     * Stores the details of a command including the command word, and command description.
     */
    public static class CommandHelpDescriptor {
        private final String command;
        private final String description;

        /**
         * Constructor for CommandHelpDescriptor
         * @param command
         * @param description
         */
        public CommandHelpDescriptor(String command, String description) {
            this.command = command;
            this.description = description;
        }

        public String getCommand() {
            return command;
        }

        public String getDescription() {
            return description;
        }
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
