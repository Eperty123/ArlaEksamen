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
    private static List<User> selections = new ArrayList<>();

    public static void setDontDeleteFromTable(TableView<User> dontDeleteFromTable) {
        TableDragMod.dontDeleteFromTable = dontDeleteFromTable;
    }

    public static void makeTableDraggable(TableView<User> tableView) {
        tableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        if (tableView.getItems().isEmpty())
            tableView.getItems().add(userPlaceHolder);
        tableView.setRowFactory(tv -> {

            TableRow<User> row = new TableRow<>();

            row.setOnDragDetected(event -> {
                if (!row.isEmpty() && row.getItem().getPhone() != -1) {
                    initialTableView = tableView;
                    Integer index = row.getIndex();

                    selections.clear();

                    ObservableList<User> items = tableView.getSelectionModel().getSelectedItems();

                    for (User u : items)
                        selections.add(u);

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
                    User dI = null;

                    int draggedIndex = (Integer) db.getContent(SERIALIZED_MIME_TYPE);

                    if (row.isEmpty()) {
                        dropIndex = finalTableView.getItems().size();
                    } else {
                        dropIndex = row.getIndex();
                        dI = tableView.getItems().get(draggedIndex);
                    }
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
                            dI = tableView.getItems().get(draggedIndex);
                        }

                    if (!initialTableView.equals(dontDeleteFromTable)) {
                        for (User u : selections)
                            initialTableView.getItems().remove(u);
                    }

                    if(dI!=null)
                        dropIndex=tableView.getItems().indexOf(dI)+delta;
                    else if(dropIndex!=0)
                        dropIndex=tableView.getItems().size();

                    tableView.getSelectionModel().clearSelection();

                    for(User sI:selections){
                        if(!finalTableView.getItems().contains(sI))
                        tableView.getItems().add(dropIndex,sI);
                        tableView.getSelectionModel().select(dropIndex);
                        dropIndex++;
                    }
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
