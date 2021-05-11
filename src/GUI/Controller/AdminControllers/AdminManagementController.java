package GUI.Controller.AdminControllers;

import BE.SceneMover;
import BE.User;
import GUI.Controller.CrudControllers.AddEmployeeController;
import GUI.Controller.CrudControllers.EditEmployeeController;
import GUI.Controller.CrudControllers.RemoveEmployeeController;
import GUI.Model.UserModel;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.ListChangeListener;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AdminManagementController implements Initializable {
    @FXML
    private TableView<User> tblEmployees;
    @FXML
    private TableColumn<User, Integer> eID;
    @FXML
    private TableColumn<User, String> eFN;
    @FXML
    private TableColumn<User, String> eLN;
    @FXML
    private TableColumn<User, String> eSN;
    @FXML
    private TableColumn<User, String> eD;

    private UserModel userModel = new UserModel();
    private SceneMover sceneMover = new SceneMover();

    private double xOffset = 0;
    private double yOffset = 0;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        tblEmployees.setItems(userModel.getAllUsers());
        eID.setCellValueFactory(u -> new ReadOnlyObjectWrapper<>(u.getValue().getId()));
        eFN.setCellValueFactory(u -> new ReadOnlyObjectWrapper<>(u.getValue().getFirstName()));
        eLN.setCellValueFactory(u -> new ReadOnlyObjectWrapper<>(u.getValue().getLastName()));
        //eSN.setCellValueFactory(u -> new ReadOnlyObjectWrapper<>(u.getValue().getAssignedScreen().isEmpty() ? u.getValue().getAssignedScreen().get(0).getName() : "None"));
        //eD.setCellValueFactory(new PropertyValueFactory<>("description"));

        tblEmployees.setRowFactory(tv -> {
            TableRow<User> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getButton().equals(MouseButton.PRIMARY)) {
                    if (event.getClickCount() == 2 && (!row.isEmpty())) {
                        try {
                            handleEditEmployee();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });
            return row;
        });
        handleUserUpdate();
    }

    /**
     * Handle any incoming changes to the User ObservableList and update the table.
     */
    private void handleUserUpdate() {
        UserModel.getInstance().getAllUsers().addListener((ListChangeListener<User>) c -> {
            tblEmployees.setItems(UserModel.getInstance().getAllUsers());
            //System.out.println("Called");
        });
    }

    public void handleAddEmployee(MouseEvent mouseEvent) throws IOException {
        Stage addEmployee = new Stage();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/GUI/VIEW/CRUDViews/AddEmployee.fxml"));
        addEmployee.setTitle("Add Employee");
        Parent root = (Parent) loader.load();
        AddEmployeeController addEmployeeController = loader.getController();

        Scene addEmployeeScene = new Scene(root);
        sceneMover.move(addEmployee, addEmployeeScene.getRoot());

        addEmployee.initStyle(StageStyle.UNDECORATED);
        addEmployee.setScene(addEmployeeScene);
        addEmployee.show();
    }

    public void handleEditEmployee() throws IOException {
        if (tblEmployees.getSelectionModel().getSelectedItem() != null) {
            Stage editEmployee = new Stage();
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/GUI/VIEW/CRUDViews/EditEmployee.fxml"));
            editEmployee.setTitle("Edit Employee");
            Parent root = (Parent) loader.load();
            EditEmployeeController editEmployeeController = loader.getController();

            Scene editEmployeeScene = new Scene(root);
            sceneMover.move(editEmployee, editEmployeeScene.getRoot());

            editEmployeeController.setData(tblEmployees.getSelectionModel().getSelectedItem());
            editEmployee.initStyle(StageStyle.UNDECORATED);
            editEmployee.setScene(editEmployeeScene);
            editEmployee.show();
        }
    }


    public void handleRemoveEmployee() throws IOException {
        if (tblEmployees.getSelectionModel().getSelectedItem() != null) {

            Stage removeEmployeeStage = new Stage();
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/GUI/VIEW/CrudViews/RemoveEmployee.fxml"));

            Parent root = (Parent) loader.load();
            RemoveEmployeeController removeEmployeeController = loader.getController();
            removeEmployeeStage.setTitle("Remove Employee");

            Scene removeEmployeeScene = new Scene(root);
            sceneMover.move(removeEmployeeStage, removeEmployeeScene.getRoot());

            removeEmployeeController.setData(tblEmployees.getSelectionModel().getSelectedItem());

            removeEmployeeStage.initStyle(StageStyle.UNDECORATED);
            removeEmployeeStage.setScene(removeEmployeeScene);
            removeEmployeeStage.show();
        }
    }
}
