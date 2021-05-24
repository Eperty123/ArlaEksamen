package GUI.Controller.CrudControllers;

import BE.Department;
import BE.SceneMover;
import BLL.DepartmentManager;
import GUI.Controller.DPT.DepartmentViewController;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class AddDepartmentController implements Initializable {
    @FXML
    private Label signifierLabel;
    @FXML
    private AnchorPane root;
    @FXML
    private JFXTextField txtDepartmentName;
    @FXML
    private JFXComboBox<Department> dptSuperior;
    @FXML
    private TableView<Department> subDptTable;
    @FXML
    private TableColumn<Department, String> subDepartments;
    @FXML
    private JFXComboBox<Department> subDpt;
    private DepartmentManager departmentManager = new DepartmentManager();
    private boolean isEdit = false;
    private Department department;
    private SceneMover sceneMover = new SceneMover();

    public void setDepartment(Department department) {
        signifierLabel.setText(String.format("Edit department (%s)", department.getName()));
        this.department = department;
        txtDepartmentName.setText(department.getName());
        subDpt.setItems(departmentManager.getAllDepartments());
        subDptTable.setItems(department.getAllSubDepartments());
        if (department.getIsSubDepartment()) {
            departmentManager.getAllDepartments().forEach(d -> {
                    if (d.getAllSubDepartments().contains(department)) {
                        if (!dptSuperior.getItems().contains(d))
                            dptSuperior.getItems().add(d);
                        dptSuperior.getSelectionModel().select(d);
                    }
            });
        }
        isEdit = true;
    }



    public void addSubDpt() {
        if (!subDptTable.getItems().contains(subDpt.getSelectionModel().getSelectedItem()))
            subDptTable.getItems().add(subDpt.getSelectionModel().getSelectedItem());
    }

    public void RemoveSubDpt() {
        subDptTable.getItems().remove(subDptTable.getSelectionModel().getSelectedItem());
    }

    public Department getDepartment() {
        return department;
    }

    public void handleSave() {
        if (!isEdit) {
            department = new Department(-1, txtDepartmentName.getText());
            department.getSubDepartments().clear();
            department.getSubDepartments().addAll(subDptTable.getItems());
            departmentManager.getAllDepartments().add(department);
            departmentViewController.addSubDepartment(department);
        } else {
            department.setName(txtDepartmentName.getText());
            department.getSubDepartments().clear();
            department.getSubDepartments().addAll(subDptTable.getItems());
            departmentManager.editDepartment(department);
        }
        Stage stage = (Stage) root.getScene().getWindow();
        stage.close();
    }

    public void handleCancel() {
        Stage stage = (Stage) root.getScene().getWindow();
        stage.close();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        dptSuperior.setItems(departmentManager.getSuperDepartments());
        subDepartments.setCellValueFactory(data -> data.getValue().nameProperty());
        dptSuperior.getSelectionModel().selectedItemProperty().addListener((observableValue, oldDepartment, newDepartment) -> {
            subDpt.getItems().add(oldDepartment);
            subDpt.getItems().remove(newDepartment);
            subDptTable.getItems().remove(newDepartment);
        });
        departmentManager.getAllDepartments().forEach(d->{
            if(d.getIsSubDepartment() && !subDptTable.getItems().contains(d))
                subDpt.getItems().add(d);
        });
    }

    private DepartmentViewController departmentViewController;

    public void setDepartmentViewController(DepartmentViewController departmentViewController) {
        this.departmentViewController=departmentViewController;
    }
}
