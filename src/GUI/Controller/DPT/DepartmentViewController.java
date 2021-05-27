package GUI.Controller.DPT;

import BE.Department;
import BE.User;
import BLL.DepartmentManager;
import GUI.Controller.PopupControllers.ConfirmationDialog;
import GUI.Model.DepartmentModel;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIcon;
import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIconView;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
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
import javafx.scene.paint.Paint;

import java.io.IOException;
import java.net.URL;
import java.util.*;

public class DepartmentViewController implements Initializable {
    @FXML
    private AnchorPane superAC;
    @FXML
    private BorderPane manageIconsBP;
    @FXML
    private MaterialDesignIconView hideIcon;
    @FXML
    private MaterialDesignIconView saveIcon;
    @FXML
    private MaterialDesignIconView removeIcon;
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
    private ObservableList<Department> subDepartments = FXCollections.observableArrayList();
    private HBox hBox = new HBox();
    private boolean isHidden = false;
    private List<Node> hiddenChildren = new ArrayList<>();
    private int dptCount = DepartmentModel.getInstance().getAllDepartments().size();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initIcons();
        initTables();
        initChangeName();
    }

    public MaterialDesignIconView getRemoveIcon() {
        return removeIcon;
    }

    private void initChangeName() {
        dptNameField.setOnAction((v) -> saveDPTNameChange());
        dptNameField.setOnInputMethodTextChanged((v) -> {
            saveIcon.fillProperty().set(Paint.valueOf("Red"));
        });
        saveIcon.setOnMouseClicked((v) -> saveDPTNameChange());

    }

    private void initIcons() {
        hideIcon.setIcon(MaterialDesignIcon.MINUS_BOX);
        removeIcon.setIcon(MaterialDesignIcon.CLOSE_BOX);
        saveIcon.setIcon(MaterialDesignIcon.CONTENT_SAVE);

        hideIcon.setOnMouseClicked((v) -> {
            if (!isHidden) {
                hiddenChildren.addAll(superAC.getChildren());
                superAC.getChildren().clear();
                hideIcon.setIcon(MaterialDesignIcon.PLUS_BOX);
                superAC.getChildren().add(hideIcon);
                isHidden = true;
            } else {
                superAC.getChildren().clear();
                superAC.getChildren().addAll(hiddenChildren);
                hiddenChildren.clear();
                manageIconsBP.setLeft(hideIcon);
                isHidden = false;
            }
        });
        removeIcon.setOnMouseClicked((v) -> {
            if (ConfirmationDialog.createConfirmationDialog("Are you sure you want to remove this department?\n\n" +
                    "This action is not reversible")) {
                DepartmentModel.getInstance().deleteDepartment(department);
                superAC.getChildren().clear();
            }
        });
    }

    public Department getDepartment() {
        return department;
    }

    private void initTables() {
        nameField.setCellValueFactory(data -> data.getValue().getFullNameProperty());
        emailField.setCellValueFactory(data -> data.getValue().emailProperty());
        phoneField.setCellValueFactory(data -> data.getValue().phoneProperty().get() < 0 ? new SimpleObjectProperty<>() : data.getValue().phoneProperty());
    }

    public void setDepartment(Department department) {
        this.department = department;
        this.subDepartments = department.getSubDepartments();
        dptUsersTable.setItems(department.getUsers());
        department.setDepartmentViewController(this);

        TableDragMod.makeTableDraggable(dptUsersTable);

        department.nameProperty().addListener((observableValue, s, t1) -> dptNameField.setText(t1));
        dptNameField.setText(department.getName());
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
        JFXButton addSubDepartmentButton = new JFXButton("Add sub department");
        addSubDepartmentButton.getStyleClass().add("addSubDeptButton");
        hBox.getChildren().add(addSubDepartmentButton);
        addSubDepartmentButton.setOnMouseClicked((v) -> addSubDepartment());
        hBox.setAlignment(Pos.TOP_CENTER);
        superBP.setCenter(hBox);
    }

    private void addSubDepartment() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUI/View/DPT/DepartmentView.fxml"));
            AnchorPane pane = loader.load();
            DepartmentViewController con = loader.getController();
            Department dpt = new Department("New Department" + dptCount++);
            department.addSubDepartment(dpt);
            DepartmentModel.getInstance().addDepartment(dpt);
            DepartmentModel.getInstance().addSubDepartment(department, dpt);
            con.setDepartment(dpt);
            department.setDepartmentViewController(con);
            hBox.getChildren().add(hBox.getChildren().size() - 1, pane);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<Department> getSubDepartments() {
        return department.getSubDepartments();
    }

    public List<Department> getAllSubDepartments() {
        List<Department> tmp = new ArrayList<>();
        tmp.add(department);
        tmp.addAll(department.getSubDepartments());
        subDepartments.forEach(sd -> tmp.addAll(sd.getSubDepartments()));
        return tmp;
    }

    private void saveDPTNameChange() {
        if (ConfirmationDialog.createConfirmationDialog("Are you sure you want to save the name on this department?")) {
            department.setName(dptNameField.getText());
            DepartmentModel.getInstance().updateDepartment(department);
            saveIcon.fillProperty().set(Paint.valueOf("Green"));
        }
    }
}
