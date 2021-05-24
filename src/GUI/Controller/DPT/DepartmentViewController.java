package GUI.Controller.DPT;

import BE.Department;
import BE.User;
import com.jfoenix.controls.JFXTextField;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class DepartmentViewController implements Initializable {
    @FXML
    private BorderPane superBP;
    @FXML
    private JFXTextField dptNameField;
    @FXML
    private TableView<User> dptUsersTable;
    @FXML
    private TableColumn<User, String> nameField;
    @FXML
    private TableColumn<User, String> emailField;
    @FXML
    private TableColumn<User, Integer> phoneField;
    private Department department;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        nameField.setCellValueFactory(data -> data.getValue().getFullNameProperty());
        emailField.setCellValueFactory(data -> data.getValue().emailProperty());
        phoneField.setCellValueFactory(data -> data.getValue().phoneProperty());
    }

    public void setDepartment(Department department) {
        this.department = department;
        dptUsersTable.setItems(department.getUsers());
        dptNameField.setText(department.getName());
        HBox hBox = new HBox();
        department.getSubDepartments().forEach(d -> {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUI/View/DPT/DepartmentView.fxml"));
            try {
                Node node = loader.load();
                DepartmentViewController con = loader.getController();
                con.setDepartment(d);
                hBox.getChildren().add(node);
            } catch (IOException e) {
                e.printStackTrace();
            }

        });
        hBox.setAlignment(Pos.TOP_CENTER);
        superBP.setCenter(hBox);
    }
}
