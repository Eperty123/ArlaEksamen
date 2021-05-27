package GUI.Controller.DPT;

import BE.User;
import javafx.collections.ObservableList;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DataFormat;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import java.util.List;
import java.util.ArrayList;

public class TableDragMod {
    private static final DataFormat SERIALIZED_MIME_TYPE = new DataFormat("application/x-java-serialized-object");
    private static TableView<User> initialTableView;
    private static TableView<User> finalTableView;
    private static final User userPlaceHolder = new User("", "", "", -1);
    private static TableView<User> dontDeleteFromTable;
    private static final List<User> selections = new ArrayList<>();
    private static final List<TableView<User>> tableViews = new ArrayList<>();

    public static void setDontDeleteFromTable(TableView<User> dontDeleteFromTable) {
        TableDragMod.dontDeleteFromTable = dontDeleteFromTable;
    }

    /**
     * Does a lot of stuff to make items in a table be draggable
     *
     * @param tableView the tableView you want to be draggable
     */
    public static void makeTableDraggable(TableView<User> tableView) {
        if (tableView != dontDeleteFromTable)
            tableViews.add(tableView);
        tableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        //If the table is empty we add a user place holder, no users in a tables makes problems
        if (tableView.getItems().isEmpty())
            tableView.getItems().add(userPlaceHolder);

        //Modifies the row factory, a lot of this is the functionality of the actual drag and drop
        tableView.setRowFactory(tv -> {

            TableRow<User> row = new TableRow<>();

            //Gathers selected items in a list and copies them to the clipboard
            row.setOnDragDetected(event -> {
                if (!row.isEmpty() && row.getItem().getPhone() != -1) {
                    initialTableView = tableView;
                    Integer index = row.getIndex();

                    selections.clear();

                    ObservableList<User> items = tableView.getSelectionModel().getSelectedItems();

                    selections.addAll(items);

                    //Adds animation, initializes drag and drop
                    Dragboard db = row.startDragAndDrop(TransferMode.MOVE);
                    db.setDragView(row.snapshot(null, null));
                    ClipboardContent cc = new ClipboardContent();
                    cc.put(SERIALIZED_MIME_TYPE, index);
                    db.setContent(cc);
                    event.consume();
                }
            });

            row.setOnDragOver(event -> {
                        Dragboard db = event.getDragboard();
                        if (db.hasContent(SERIALIZED_MIME_TYPE)) {
                            if (row.getIndex() != ((Integer) db.getContent(SERIALIZED_MIME_TYPE)).intValue()) {
                                event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
                                finalTableView = tableView;
                                event.consume();
                            }
                        }
                    }
            );
            row.setOnDragDropped(event -> {
                Dragboard db = event.getDragboard();
                if (db.hasContent(SERIALIZED_MIME_TYPE)) {
                    int dropIndex;

                    //deltaI ~ current User
                    User dI = null;

                    //Gets the index of the row where we insert our Users
                    if (row.isEmpty()) {
                        dropIndex = finalTableView.getItems().size();
                    } else {
                        dropIndex = row.getIndex();
                        dI = tableView.getItems().get(dropIndex);
                    }

                    //More work to get the proper index
                    int delta = 0;
                    if (dI != null)
                        while (selections.contains(dI)) {
                            delta = 1;
                            --dropIndex;
                            if (dropIndex < 0) {
                                dI = null;
                                dropIndex = 0;
                                break;
                            }
                            dI = finalTableView.getItems().get(dropIndex);
                        }

                    if (!initialTableView.equals(dontDeleteFromTable)) {
                        for (User u : selections)
                            initialTableView.getItems().remove(u);
                    }

                    if (dI != null)
                        dropIndex = finalTableView.getItems().indexOf(dI) + delta;
                    else if (dropIndex != 0)
                        dropIndex = finalTableView.getItems().size();

                    finalTableView.getSelectionModel().clearSelection();
                    int initialDropIndex = dropIndex;

                    selections.forEach(sI -> {
                        tableViews.forEach(t -> {
                            t.getItems().removeIf(u -> u.getId() == sI.getId());
                            if (t.getItems().isEmpty())
                                t.getItems().add(userPlaceHolder);
                        });
                    });

                    //Actually adds the selected items
                    for (User sI : selections) {

                        if (!finalTableView.getItems().contains(sI)) {
                            finalTableView.getItems().add(dropIndex, sI);
                        }

                        finalTableView.getSelectionModel().select(dropIndex);
                        dropIndex++;
                    }

                    if (finalTableView.getItems().contains(userPlaceHolder))
                        finalTableView.getSelectionModel().selectAll();

                    //Some more placeholders to make this work
                    finalTableView.getItems().remove(userPlaceHolder);
                    if (initialTableView.getItems().size() == 0)
                        initialTableView.getItems().add(userPlaceHolder);

                    selections.clear();
                    event.setDropCompleted(true);
                    event.consume();
                }

            });
            return row;
        });
    }
}
