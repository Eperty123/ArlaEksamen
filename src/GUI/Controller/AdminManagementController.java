package GUI.Controller;

import BE.Employee;
import javafx.beans.property.ReadOnlyIntegerProperty;
import javafx.beans.property.ReadOnlyIntegerWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.ResourceBundle;

public class AdminManagementController implements Initializable {
    @FXML
    private TableView<Employee> tblEmployees;
    @FXML
    private TableColumn<Employee, Integer> eID;
    @FXML
    private TableColumn<Employee,String> eFN;
    @FXML
    private TableColumn<Employee,String> eLN;
    @FXML
    private TableColumn<Employee,Number> eSN;
    @FXML
    private TableColumn<Employee,String> eD;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ObservableList<Employee> employees = FXCollections.observableArrayList();
        employees.add(new Employee(1,"Hans","Eriksen",1,"Manager hos Cocio"));
        employees.add(new Employee(2,"Jens","Hansen",2,"ansat hos Cocio"));
        employees.add(new Employee(3,"Ulla","Nielsen",3,"ansat hos Mathilde"));
        employees.add(new Employee(4,"Bodil","Jensen",4,"ansat hos Mathilde"));
        employees.add(new Employee(5,"Jan","Olsen",5,"Manager host Mathilde"));
        employees.add(new Employee(6,"Mike","Faaborg",6,"ansat hos Cocio"));
        employees.add(new Employee(7,"Nikolaj","Iversen",7,"Manager hos Arla foods"));
        employees.add(new Employee(8,"Jonas","Smed",8,"ansat hos Arla foods"));
        employees.add(new Employee(9,"Kevin","Einarsen",9,"ansat hos Arla foods"));
        employees.add(new Employee(10,"Ove","Bertelsen",10,"ansat hos Arla foods"));

        tblEmployees.setItems(employees);
        eID.setCellValueFactory(new PropertyValueFactory<>("id"));
        eFN.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        eLN.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        eSN.setCellValueFactory(new PropertyValueFactory<>("screenNumber"));
        eD.setCellValueFactory(new PropertyValueFactory<>("description"));
    }
}
