package GUI.Controller.AdminControllers;

import BE.*;
import GUI.Controller.CrudControllers.AddEmployeeController;
import GUI.Controller.CrudControllers.EditEmployeeController;
import GUI.Controller.CrudControllers.RemoveEmployeeController;
import GUI.Controller.EmployeeCardController;
import GUI.Controller.PopupControllers.WarningController;
import GUI.Model.DepartmentModel;
import GUI.Model.UserModel;
import com.jfoenix.controls.JFXTextField;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ListChangeListener;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.lang.reflect.Array;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class AdminManagementController implements Initializable {
    @FXML
    private VBox vbox;
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private JFXTextField txtSearch;

    private UserModel userModel = UserModel.getInstance();
    private SceneMover sceneMover = new SceneMover();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadAllUsers();
        handleUserUpdate();
    }


    /**
     * Load all the available screens.
     */
    private void loadAllUsers() {
        // Remove all nodes.
        vbox.getChildren().clear();

        // Add all screens.
        for (Department d : DepartmentModel.getInstance().getAllDepartments()) {
            if (!d.getUsers().isEmpty())
                vbox.getChildren().add(UserManagementPaneFactory.createUserManagementBoard(d, vbox));
        }
    }

    public void handleSearchUser() {
        vbox.getChildren().clear();
        if (!txtSearch.getText().isEmpty() || !txtSearch.getText().isBlank()) {
            for (Department d : Searcher.searchDepartmentUsers(DepartmentModel.getInstance().getAllDepartments(), txtSearch.getText())){
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
            System.out.println("Called");
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

        Scene addEmployeeScene = new Scene(root);
        sceneMover.move(addEmployee, addEmployeeScene.getRoot());

        addEmployee.getIcons().addAll(
                new Image("/GUI/Resources/AppIcons/icon16x16.png"),
                new Image("/GUI/Resources/AppIcons/icon24x24.png"),
                new Image("/GUI/Resources/AppIcons/icon32x32.png"),
                new Image("/GUI/Resources/AppIcons/icon48x48.png"),
                new Image("/GUI/Resources/AppIcons/icon64x64.png"));
        addEmployee.initStyle(StageStyle.UNDECORATED);
        addEmployee.setScene(addEmployeeScene);
        addEmployee.show();
    }


    public void handleEditEmployee() throws IOException {
        if (UserManagementPaneFactory.getSelectedUser().get(0) != null) {
            Stage editEmployee = new Stage();
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/GUI/VIEW/CRUDViews/EditEmployee.fxml"));
            editEmployee.setTitle("Edit Employee");
            Parent root = (Parent) loader.load();
            EditEmployeeController editEmployeeController = loader.getController();

            Scene editEmployeeScene = new Scene(root);
            sceneMover.move(editEmployee, editEmployeeScene.getRoot());

            editEmployeeController.setData(UserManagementPaneFactory.getSelectedUser().get(0));
            editEmployee.getIcons().addAll(
                    new Image("/GUI/Resources/AppIcons/icon16x16.png"),
                    new Image("/GUI/Resources/AppIcons/icon24x24.png"),
                    new Image("/GUI/Resources/AppIcons/icon32x32.png"),
                    new Image("/GUI/Resources/AppIcons/icon48x48.png"),
                    new Image("/GUI/Resources/AppIcons/icon64x64.png"));
            editEmployee.initStyle(StageStyle.UNDECORATED);
            editEmployee.setScene(editEmployeeScene);
            editEmployee.show();
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

            Scene removeEmployeeScene = new Scene(root);
            sceneMover.move(removeEmployeeStage, removeEmployeeScene.getRoot());

            removeEmployeeController.setData(UserManagementPaneFactory.getSelectedUser().get(0));

            removeEmployeeStage.getIcons().addAll(
                    new Image("/GUI/Resources/AppIcons/icon16x16.png"),
                    new Image("/GUI/Resources/AppIcons/icon24x24.png"),
                    new Image("/GUI/Resources/AppIcons/icon32x32.png"),
                    new Image("/GUI/Resources/AppIcons/icon48x48.png"),
                    new Image("/GUI/Resources/AppIcons/icon64x64.png"));
            removeEmployeeStage.initStyle(StageStyle.UNDECORATED);
            removeEmployeeStage.setScene(removeEmployeeScene);
            removeEmployeeStage.show();
        }
    }
}
