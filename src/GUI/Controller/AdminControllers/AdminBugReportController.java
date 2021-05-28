package GUI.Controller.AdminControllers;

import BE.Bug;
import BE.SceneMover;
import BLL.StageShower;
import BE.User;
import GUI.Controller.CrudControllers.EditBugController;
import GUI.Controller.PopupControllers.ConfirmBugController;
import GUI.Controller.PopupControllers.WarningController;
import GUI.Model.BugModel;
import GUI.Model.DataModel;
import GUI.Model.UserModel;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import javax.xml.crypto.Data;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AdminBugReportController implements Initializable {
    @FXML
    private TableView<Bug> tblBugs;
    @FXML
    private TableColumn<Bug, String> bD;
    @FXML
    private TableColumn<Bug, String> bDR;
    @FXML
    private TableColumn<Bug, String> bAR;

    private final BugModel bugModel = BugModel.getInstance();
    private final SceneMover sceneMover = new SceneMover();
    private final ObservableList<Bug> bugs = DataModel.getInstance().getAllBugs();
    private final StageShower stageShower = new StageShower();


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Set the table's content with the BugModel's.
        loadAllBugs();
        handleBugUpdate();

        bD.setCellValueFactory(b -> new ReadOnlyObjectWrapper<>(b.getValue().getDescription()));
        bDR.setCellValueFactory(b -> new ReadOnlyObjectWrapper<>(b.getValue().getDateReported()));
        bAR.setCellValueFactory(b -> new ReadOnlyObjectWrapper<>(b.getValue().getAdminId() == 0 ? "None assigned" : getAdmin(b.getValue().getAdminId())));

        tblBugs.setRowFactory(tv -> {
            TableRow<Bug> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getButton() == MouseButton.PRIMARY) {
                    if (event.getClickCount() == 2 && (!row.isEmpty())) {
                        try {
                            handleEditBug();
                        } catch (IOException e) {
                            e.printStackTrace();
                            WarningController.createWarning("Oh no! Something went wrong trying to load the admins bug report view." +
                                    " It might be corrupted or lost.");
                        }
                    }
                }
            });
            return row;
        });
    }

    private void loadAllBugs(){
        ObservableList<Bug> allUnresolvedBugs = FXCollections.observableArrayList();
        allUnresolvedBugs.addAll(bugs);
        allUnresolvedBugs.removeIf(Bug::isBugResolved);
        tblBugs.setItems(allUnresolvedBugs);
    }


    private String getAdmin(int id){
        for (User u : DataModel.getInstance().getUsers()){
            if (u.getId() == id){
                return u.getUserName();
            }
        }
        return null;
    }

    /**
     * Handle any incoming changes to the Bug ObservableList and update the table.
     */
    private void handleBugUpdate() {
        DataModel.getInstance().getAllBugs().addListener((ListChangeListener<Bug>) c -> {
            ObservableList<Bug> allUnresolvedBugs = FXCollections.observableArrayList();
            allUnresolvedBugs.addAll(bugs);
            allUnresolvedBugs.removeIf(Bug::isBugResolved);
            tblBugs.setItems(allUnresolvedBugs);
        });
    }

    /**
     * handles clicking the view all bugs icon on the UI
     * @throws IOException if it cannot find the FXML file.
     */
    public void handleViewAllBugs() throws IOException {
        Stage stage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader();

        fxmlLoader.setLocation(getClass().getResource("/GUI/VIEW/AdminViews/AllBugs.fxml"));
        Parent root = fxmlLoader.load();
        stage.setTitle("All fixed bugs");

        Scene scene = new Scene(root);

        SceneMover sceneMover = new SceneMover();
        BorderPane bp = (BorderPane) root.getChildrenUnmodifiable().get(0);
        sceneMover.move(stage,bp.getTop());

        stage.getIcons().addAll(
                new Image("/GUI/Resources/AppIcons/icon16x16.png"),
                new Image("/GUI/Resources/AppIcons/icon24x24.png"),
                new Image("/GUI/Resources/AppIcons/icon32x32.png"),
                new Image("/GUI/Resources/AppIcons/icon48x48.png"),
                new Image("/GUI/Resources/AppIcons/icon64x64.png"));
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * handles clicking the bug fixed icon.
     * @throws IOException if it cannot find the FXML file in the ConfirmationDialog.
     */
    public void handleBugFixed() throws IOException {
        if (tblBugs.getSelectionModel().getSelectedItem() != null) {
            Stage fixBug = new Stage();
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/GUI/VIEW/PopUpViews/ConfirmBug.fxml"));
            fixBug.setTitle("Fix Bug");
            Parent root = loader.load();
            ConfirmBugController bugFixController = loader.getController();
            bugFixController.setSelectedBug(tblBugs.getSelectionModel().getSelectedItem());

            stageShower.showScene(fixBug, root, sceneMover,root);
        }
    }


    /**
     * handles clicking the edit bug icon.
     * @throws IOException if it cannot find the FXML file given.
     */
    public void handleEditBug() throws IOException {
        if (tblBugs.getSelectionModel().getSelectedItem() != null) {
            Stage editBug = new Stage();
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/GUI/VIEW/CRUDViews/EditBug.fxml"));
            editBug.setTitle("Edit Bug");
            Parent root = loader.load();
            EditBugController editBugController = loader.getController();

            editBugController.setData(tblBugs.getSelectionModel().getSelectedItem());

           stageShower.showScene(editBug,root,sceneMover);
        }
    }
}
