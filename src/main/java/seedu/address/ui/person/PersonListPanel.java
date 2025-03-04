package seedu.address.ui.person;

import java.util.function.Consumer;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.core.index.Index;
import seedu.address.model.person.Person;
import seedu.address.ui.UiPart;

/**
 * Panel containing the list of persons.
 */
public class PersonListPanel extends UiPart<Region> {
    private static final String FXML = "PersonListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(PersonListPanel.class);

    @FXML
    private ListView<Person> personListView;

    /**
     * Creates a {@code PersonListPanel} with the given {@code ObservableList}.
     */
    public PersonListPanel(ObservableList<Person> personList) {
        this(personList, (person) -> {}, (personIndex) -> {});
    }

    /**
     * Creates a {@code PersonListPanel} with the given {@code ObservableList} with a delete handler.
     */
    public PersonListPanel(ObservableList<Person> personList, Consumer<Person> selectHandler,
            Consumer<Index> deleteHandler) {
        super(FXML);
        personListView.setItems(personList);
        personListView.setCellFactory(listView -> new PersonListViewCell());

        personListView.setOnMouseClicked(new EventHandler<Event>() {
            @Override
            public void handle(Event event) {
                selectHandler.accept(personListView.getSelectionModel().getSelectedItem());
            }
        });

        personListView.setOnKeyPressed(new EventHandler<KeyEvent>() {

            @Override
            public void handle(KeyEvent event) {
                if (event.getCode().equals(KeyCode.DELETE)) {
                    logger.info(personListView.getSelectionModel().getSelectedItem().getPersonId() + " for Deletion");
                    deleteHandler.accept(Index.fromZeroBased(personListView.getSelectionModel().getSelectedIndex()));
                    return;
                }
            }

        });
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Person} using a {@code PersonCard}.
     */
    class PersonListViewCell extends ListCell<Person> {
        @Override
        protected void updateItem(Person person, boolean empty) {
            super.updateItem(person, empty);

            if (empty || person == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new PersonCard(person, getIndex() + 1).getRoot());
            }
        }
    }
}
