package seedu.address.ui;

import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.notes.Notes;

/**
 * Panel containing the list of Notes.
 */
public class NotesListPanel extends UiPart<Region> {
    private static final String FXML = "NotesListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(NotesListPanel.class);

    @FXML
    private ListView<Notes> notesListView;

    public NotesListPanel(ObservableList<Notes> notesList) {
        super(FXML);
        notesListView.setItems(notesList);
        notesListView.setCellFactory(listView -> new NotesListViewCell());
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Notes} using a {@code NotesCard}.
     */
    class NotesListViewCell extends ListCell<Notes> {
        @Override
        protected void updateItem(Notes note, boolean empty) {
            super.updateItem(note, empty);

            if (empty || note.getPath() == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new NotesCard(note, getIndex() + 1).getRoot());
            }
        }
    }

}
