package seedu.address.ui.person;

import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.Logic;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.commands.person.DeleteCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.ui.UiPart;
import seedu.address.ui.main.CommandBox;
import seedu.address.ui.main.ResultDisplay;
import seedu.address.ui.main.StatusBarFooter;

/**
 * Displays contact list.
 */
public class AddressBookWindow extends UiPart<Stage> {

    private static final String FXML = "AddressBookWIndow.fxml";

    private final Logger logger = LogsCenter.getLogger(getClass());

    private Stage primaryStage;
    private Logic logic;

    private ResultDisplay resultDisplay;
    private PersonListPanel personListPanel;

    @FXML
    private StackPane commandBoxPlaceholder;
    @FXML
    private StackPane resultDisplayPlaceholder;
    @FXML
    private StackPane personListPanelPlaceholder;
    @FXML
    private StackPane statusbarPlaceholder;

    /**
     * Creates a {@code AddressBookWindow} with the given {@code Stage} and {@code Logic}.
     */
    public AddressBookWindow(Stage primaryStage, Logic logic) {
        super(FXML, primaryStage);

        // Set dependencies
        this.primaryStage = primaryStage;
        this.logic = logic;

        // Configure the UI
    }

    /**
     * Show main window.
     */
    public void show() {
        logger.fine("Showing address book page");
        getRoot().show();
        getRoot().centerOnScreen();
    }

    /**
     * Returns true if the stats window is currently being shown.
     */
    public boolean isShowing() {
        return getRoot().isShowing();
    }

    /**
     * Hides the stats window.
     */
    public void hide() {
        getRoot().hide();
    }

    /**
     * Focuses on the stats window.
     */
    public void focus() {
        getRoot().requestFocus();
    }

    /**
     * fillInnerParts.
     */
    public void fillInnerParts() {
        personListPanel = new PersonListPanel(logic.getFilteredPersonList(), personIndex -> {
            try {
                executeCommand(DeleteCommand.COMMAND_WORD + " " + personIndex.getOneBased());
            } catch (ParseException | CommandException e) {
                logger.warning(e.getMessage());
            }
        });
        personListPanelPlaceholder.getChildren().add(personListPanel.getRoot());

        resultDisplay = new ResultDisplay();
        resultDisplayPlaceholder.getChildren().add(resultDisplay.getRoot());

        StatusBarFooter statusBarFooter = new StatusBarFooter(logic.getAddressBookFilePath());
        statusbarPlaceholder.getChildren().add(statusBarFooter.getRoot());

        CommandBox commandBox = new CommandBox(this::executeCommand);
        commandBoxPlaceholder.getChildren().add(commandBox.getRoot());
    }

    @FXML
    private void handleExit() {
        primaryStage.hide();
    }

    /**
     * Executes the command and returns the result.
     *
     * @see seedu.address.logic.Logic#execute(String)
     */
    private CommandResult executeCommand(String commandText) throws CommandException, ParseException {
        try {
            CommandResult commandResult = logic.execute(commandText);
            logger.info("Result: " + commandResult.getFeedbackToUser());
            resultDisplay.setFeedbackToUser(commandResult.getFeedbackToUser());

            if (commandResult.isExit()) {
                handleExit();
            }

            return commandResult;
        } catch (CommandException | ParseException e) {
            logger.info("Invalid command: " + commandText);
            resultDisplay.setFeedbackToUser(e.getMessage());
            throw e;
        }
    }
}
