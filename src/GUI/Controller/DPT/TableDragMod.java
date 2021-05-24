package GUI.Controller.DPT;

import BE.User;
import javafx.collections.FXCollections;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DataFormat;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;

public class TableDragMod {
    private static User draggedUser;
    private static final DataFormat SERIALIZED_MIME_TYPE = new DataFormat("application/x-java-serialized-object");
    private static TableView<User> initialTableView;
    private static TableView<User> finalTableView;
    private static final User userPlaceHolder = new User("","","",-1);
    private static TableView<User> dontDeleteFromTable;

    public static void setDontDeleteFromTable(TableView<User> dontDeleteFromTable) {
        TableDragMod.dontDeleteFromTable = dontDeleteFromTable;
    }

    public static void makeTableDraggable(TableView<User> tableView) {
        if(tableView.getItems().isEmpty())
            tableView.getItems().add(userPlaceHolder);
        tableView.setRowFactory(tv -> {

            TableRow<User> row = new TableRow<>();

            row.setOnDragDetected(event -> {
                if (!row.isEmpty() && row.getItem().getPhone()!=-1) {
                    initialTableView = tableView;
                    Integer index = row.getIndex();
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
                    int draggedIndex = (Integer) db.getContent(SERIALIZED_MIME_TYPE);
                    if(initialTableView.getItems().size()==1)
                        initialTableView.getItems().add(userPlaceHolder);
                    if(!initialTableView.equals(dontDeleteFromTable))
                    draggedUser = initialTableView.getItems().remove(draggedIndex);
                    else
                        draggedUser=initialTableView.getItems().get(draggedIndex);

                    int dropIndex;

                    if (row.isEmpty()) {
                        dropIndex = finalTableView.getItems().size();
                    } else {
                        dropIndex = row.getIndex();
                    }
                    if(!finalTableView.getItems().contains(draggedUser))
                    finalTableView.getItems().add(dropIndex, draggedUser);
                    finalTableView.getItems().remove(userPlaceHolder);
                    tableView.getSelectionModel().select(dropIndex);
                    event.consume();
                }

            });
            return row;
        });
    }
}
