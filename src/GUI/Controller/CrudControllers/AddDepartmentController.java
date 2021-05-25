package GUI.Controller.CrudControllers;

import BE.Department;
import BE.SceneMover;
import BLL.DepartmentManager;
import GUI.Controller.DPT.DepartmentViewController;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class AddDepartmentController {
    @FXML
    private Label signifierLabel;
    @FXML
    private AnchorPane root;
    @FXML
    private JFXTextField txtDepartmentName;
    private DepartmentManager departmentManager = new DepartmentManager();
    private boolean isEdit = false;
    private Department department;
    private SceneMover sceneMover = new SceneMover();

    public void setDepartment(Department department) {
        signifierLabel.setText(String.format("Edit department (%s)", department.getName()));
        this.department = department;
        txtDepartmentName.setText(department.getName());
        isEdit = true;
    }

    public Department getDepartment() {
        return department;
    }

    public void handleSave() {
        if (!isEdit) {
            department = new Department(-1, txtDepartmentName.getText());
            departmentManager.getAllDepartments().add(department);
            departmentViewController.addSubDepartment(department);
        } else {
            department.setName(txtDepartmentName.getText());
            departmentManager.editDepartment(department);
        }
        Stage stage = (Stage) root.getScene().getWindow();
        stage.close();
    }

    public void handleCancel() {
        Stage stage = (Stage) root.getScene().getWindow();
        stage.close();
    }

    private DepartmentViewController departmentViewController;

    public void setDepartmentViewController(DepartmentViewController departmentViewController) {
        this.departmentViewController=departmentViewController;
    }
}
