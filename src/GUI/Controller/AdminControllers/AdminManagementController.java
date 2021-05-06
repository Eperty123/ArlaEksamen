package GUI.Controller.AdminControllers;

import BE.Employee;
import BE.User;
import GUI.Controller.CrudControllers.AddEmployeeController;
import GUI.Controller.CrudControllers.EditEmployeeController;
import GUI.Controller.CrudControllers.RemoveEmployeeController;
import GUI.Controller.PopupControllers.ConfirmationController;
import GUI.Model.UserModel;
import javafx.beans.property.ReadOnlyIntegerProperty;
import javafx.beans.property.ReadOnlyIntegerWrapper;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class AdminManagementController implements Initializable {
    @FXML
    private TableView<User> tblEmployees;
    @FXML
    private TableColumn<User, Integer> eID;
    @FXML
    private TableColumn<User,String> eFN;
    @FXML
    private TableColumn<User,String> eLN;
    @FXML
    private TableColumn<User,Number> eSN;
    @FXML
    private TableColumn<User,String> eD;

    private double xOffset = 0;
    private double yOffset = 0;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ObservableList<User> users = FXCollections.observableArrayList();

        users.addAll(UserModel.getInstance().getAllUsers());

        tblEmployees.setItems(users);
        eID.setCellValueFactory(u -> new ReadOnlyObjectWrapper<>(u.getValue().getId()));
        eFN.setCellValueFactory(u -> new ReadOnlyObjectWrapper<>(u.getValue().getFirstName()));
        eLN.setCellValueFactory(u -> new ReadOnlyObjectWrapper<>(u.getValue().getLastName()));
        //eSN.setCellValueFactory(new PropertyValueFactory<>("screenNumber"));
        //eD.setCellValueFactory(new PropertyValueFactory<>("description"));
    }

    public void handleAddEmployee(MouseEvent mouseEvent) throws IOException {
        Stage addEmployee = new Stage();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/GUI/VIEW/CRUDViews/AddEmployee.fxml"));

        Parent root = (Parent) loader.load();
        AddEmployeeController addEmployeeController = loader.getController();

        Scene addEmployeeScene = new Scene(root);

        addEmployeeScene.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                xOffset = event.getSceneX();
                yOffset = event.getSceneY();
            }
        });

        addEmployeeScene.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                addEmployee.setX(event.getScreenX() - xOffset);
                addEmployee.setY(event.getScreenY() - yOffset);
                addEmployee.setOpacity(0.8f);
            }
        });

        addEmployeeScene.setOnMouseDragExited((event) -> {
            addEmployee.setOpacity(1.0f);
        });

        addEmployeeScene.setOnMouseReleased((event) -> {
            addEmployee.setOpacity(1.0f);
        });


        addEmployee.initStyle(StageStyle.UNDECORATED);
        addEmployee.setScene(addEmployeeScene);
        addEmployee.show();
    }

    public void handleEditEmployee(MouseEvent mouseEvent) throws IOException {
        Stage editEmployee = new Stage();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/GUI/VIEW/CRUDViews/EditEmployee.fxml"));

        Parent root = (Parent) loader.load();
        EditEmployeeController editEmployeeController = loader.getController();

        Scene editEmployeeScene = new Scene(root);

        editEmployeeScene.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                xOffset = event.getSceneX();
                yOffset = event.getSceneY();
            }
        });

        editEmployeeScene.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                editEmployee.setX(event.getScreenX() - xOffset);
                editEmployee.setY(event.getScreenY() - yOffset);
                editEmployee.setOpacity(0.8f);
            }
        });

        editEmployeeScene.setOnMouseDragExited((event) -> {
            editEmployee.setOpacity(1.0f);
        });

        editEmployeeScene.setOnMouseReleased((event) -> {
            editEmployee.setOpacity(1.0f);
        });

        editEmployeeController.setData(tblEmployees.getSelectionModel().getSelectedItem());
        editEmployee.initStyle(StageStyle.UNDECORATED);
        editEmployee.setScene(editEmployeeScene);
        editEmployee.show();
    }


    public void handleRemoveEmployee() throws IOException {
        Stage removeEmployeeStage = new Stage();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/GUI/VIEW/CrudViews/RemoveEmployee.fxml"));

        Parent root = (Parent) loader.load();
        RemoveEmployeeController removeEmployeeController = loader.getController();

        Scene removeEmployeeScene = new Scene(root);

        removeEmployeeScene.setOnMousePressed(event -> {
            xOffset = event.getSceneX();
            yOffset = event.getSceneY();
        });

        removeEmployeeScene.setOnMouseDragged(event -> {
            removeEmployeeStage.setX(event.getScreenX() - xOffset);
            removeEmployeeStage.setY(event.getScreenY() - yOffset);
            removeEmployeeStage.setOpacity(0.8f);
        });

        removeEmployeeScene.setOnMouseDragExited((event) -> {
            removeEmployeeStage.setOpacity(1.0f);
        });

        removeEmployeeScene.setOnMouseReleased((event) -> {
            removeEmployeeStage.setOpacity(1.0f);
        });

        removeEmployeeController.setData(tblEmployees.getSelectionModel().getSelectedItem());

        removeEmployeeStage.initStyle(StageStyle.UNDECORATED);
        removeEmployeeStage.setScene(removeEmployeeScene);
        removeEmployeeStage.show();
    }
}
