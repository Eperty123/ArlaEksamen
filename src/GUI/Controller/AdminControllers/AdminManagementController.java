package GUI.Controller.AdminControllers;

import BE.*;
import GUI.Controller.CrudControllers.AddEmployeeController;
import GUI.Controller.CrudControllers.EditEmployeeController;
import GUI.Controller.CrudControllers.RemoveEmployeeController;
import GUI.Model.DataModel;
import GUI.Model.DepartmentModel;
import GUI.Model.UserModel;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.ListChangeListener;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AdminManagementController implements Initializable {
    @FXML
    private VBox vbox;
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private JFXTextField txtSearch;

    private final UserModel userModel = UserModel.getInstance();
    private final SceneMover sceneMover = new SceneMover();
    private final StageShower stageShower = new StageShower();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadAllUsers();
        handleUserUpdate();
    }


    /**
     * Load all the available screens.
     */
    private void loadAllUsers() {
        UserManagementPaneFactory.getSelectedUser().clear();
        // Remove all nodes.
        vbox.getChildren().clear();
        // Add all screens.
        for (Department d : DataModel.getInstance().getDepartments()) {
            if (!d.getUsers().isEmpty())
                vbox.getChildren().add(UserManagementPaneFactory.createUserManagementBoard(d, vbox));
        }
    }

    public void handleSearchUser() {
        vbox.getChildren().clear();
        if (!txtSearch.getText().isEmpty() || !txtSearch.getText().isBlank()) {
            for (Department d : Searcher.searchDepartmentUsers(DataModel.getInstance().getDepartments(), txtSearch.getText())){
                vbox.getChildren().add((UserManagementPaneFactory.createUserManagementBoard(d,vbox)));
            }
        }else{
            loadAllUsers();
        }
    }

    /**
     * Handle any incoming changes to the User ObservableList and update the table.
     */
    private void handleUserUpdate() {
        UserModel.getInstance().getAllUsers().addListener((ListChangeListener<User>) c -> {
            loadAllUsers();
        });
    }

    /**
     * Displays the add Employee screen.
     *
     * @throws IOException if it cannot find the FXML file.
     */
    public void handleAddEmployee() throws IOException {
        Stage addEmployee = new Stage();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/GUI/VIEW/CRUDViews/AddEmployee.fxml"));
        addEmployee.setTitle("Add Employee");
        Parent root = (Parent) loader.load();
        AddEmployeeController addEmployeeController = loader.getController();

        stageShower.showScene(addEmployee, root, sceneMover);
    }


    public void handleEditEmployee() throws IOException {
        if (UserManagementPaneFactory.getSelectedUser().get(0) != null) {
            Stage editEmployee = new Stage();
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/GUI/VIEW/CRUDViews/EditEmployee.fxml"));
            editEmployee.setTitle("Edit Employee");
            Parent root = (Parent) loader.load();
            EditEmployeeController editEmployeeController = loader.getController();

            stageShower.showScene(editEmployee,root,sceneMover);
        }
    }


    public void handleRemoveEmployee() throws IOException {
        if (UserManagementPaneFactory.getSelectedUser().get(0) != null) {

            Stage removeEmployeeStage = new Stage();
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/GUI/VIEW/CrudViews/RemoveEmployee.fxml"));

            Parent root = (Parent) loader.load();
            RemoveEmployeeController removeEmployeeController = loader.getController();
            removeEmployeeStage.setTitle("Remove Employee");

            stageShower.showScene(removeEmployeeStage,root,sceneMover);
        }
    }
}
