package GUI.Controller.AdminControllers;

import BE.Bug;
import BE.Searcher;
import BE.User;
import GUI.Controller.StageBuilder;
import GUI.Model.BugModel;
import GUI.Model.DataModel;
import GUI.Model.UserModel;
import com.jfoenix.controls.JFXTextField;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class AllBugsController implements Initializable {
    public AnchorPane pickerStageHere;
    @FXML
    private BorderPane borderPane;
    @FXML
    private Label lblTitle;
    @FXML
    private JFXTextField txtSearch;
    @FXML
    private TableView<Bug> tblBugs;
    @FXML
    private TableColumn<Bug,String> bD;
    @FXML
    private TableColumn<Bug,String> bDR;
    @FXML
    private TableColumn<Bug, String> bAR;
    @FXML
    private TableColumn<Bug, String> bFM;


    private final BugModel bugModel = BugModel.getInstance();
    private final ObservableList<Bug> bugs = DataModel.getInstance().getAllBugs();
    ObservableList<Bug> allResolvedBugs = FXCollections.observableArrayList();
    private Boolean isMaximized = false;
    StageBuilder stageBuilder = new StageBuilder();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        lblTitle.setText("All fixed bugs");
        allResolvedBugs.addAll(bugs);
        allResolvedBugs.removeIf(Bug -> !Bug.isBugResolved());
        tblBugs.setItems(allResolvedBugs);

        bD.setCellValueFactory(b -> new ReadOnlyObjectWrapper<>(b.getValue().getDescription()));
        bFM.setCellValueFactory(b -> new ReadOnlyObjectWrapper<>(b.getValue().getFixMessage()));
        bDR.setCellValueFactory(b -> new ReadOnlyObjectWrapper<>(b.getValue().getDateReported()));
        bAR.setCellValueFactory(b -> new ReadOnlyObjectWrapper<>(b.getValue().getAdminId() == 0 ? "None assigned" : getAdmin(b.getValue().getAdminId())));
    }

    private String getAdmin(int id){
        for (User u : DataModel.getInstance().getUsers()){
            if (u.getId() == id){
                return u.getUserName();
            }
        }
        return null;
    }

    public void setTitle(String title) {
        lblTitle.setText(title);
    }

    public void handleExit(MouseEvent mouseEvent) {
        handleClose();
    }

    public void handleMaximize(MouseEvent mouseEvent) {
        isMaximized = !isMaximized;
        Stage stage = (Stage) borderPane.getScene().getWindow();
        stage.setMaximized(isMaximized);
    }

    public void handleMinimize(MouseEvent mouseEvent) {
        Stage stage = (Stage) borderPane.getScene().getWindow();
        stage.setIconified(true);
    }

    public void handleClose() {
        Stage stage = (Stage) borderPane.getScene().getWindow();
        stage.close();
    }


    public void handleSearch(){
        if (!txtSearch.getText().isEmpty() || txtSearch.getText() != null && !tblBugs.getItems().isEmpty()) {
            tblBugs.setItems(Searcher.searchBugs(allResolvedBugs, txtSearch.getText()));
        }
    }


}
