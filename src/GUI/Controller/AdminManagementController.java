package GUI.Controller;

import BE.Employee;
import BE.User;
import GUI.Model.UserModel;
import javafx.beans.property.ReadOnlyIntegerProperty;
import javafx.beans.property.ReadOnlyIntegerWrapper;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

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
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ObservableList<User> users = FXCollections.observableArrayList();

        try {
            users.addAll(UserModel.getInstance().getAllUsers());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        tblEmployees.setItems(users);
        eID.setCellValueFactory(u -> new ReadOnlyObjectWrapper<>(u.getValue().getId()));
        eFN.setCellValueFactory(u -> new ReadOnlyObjectWrapper<>(u.getValue().getFirstName()));
        eLN.setCellValueFactory(u -> new ReadOnlyObjectWrapper<>(u.getValue().getLastName()));
        //eSN.setCellValueFactory(new PropertyValueFactory<>("screenNumber"));
        //eD.setCellValueFactory(new PropertyValueFactory<>("description"));
    }
}
