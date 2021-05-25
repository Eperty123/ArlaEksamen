package GUI.Controller.DPT;

import BE.Department;
import BE.SceneMover;
import BE.User;
import BLL.DepartmentManager;
import GUI.Controller.CrudControllers.AddDepartmentController;
import com.jfoenix.controls.JFXTextField;
import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIcon;
import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIconView;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class DepartmentViewController implements Initializable {
    @FXML
    private BorderPane manageIconsBP;
    @FXML
    private MaterialDesignIconView hideIcon;
    @FXML
    private AnchorPane superAC;
    @FXML
    private MaterialDesignIconView removeIcon;
    @FXML
    private MaterialDesignIconView settingsIcon;
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
    private ObservableList<User> users = FXCollections.observableArrayList();
    private ObservableList<Department> subDepartments = FXCollections.observableArrayList();
    private HBox hBox = new HBox();
    private boolean isHidden = false;
    private List<Node> deadChildren = new ArrayList<>();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        hideIcon.setIcon(MaterialDesignIcon.MINUS_BOX);
        settingsIcon.setIcon(MaterialDesignIcon.SETTINGS);
        removeIcon.setIcon(MaterialDesignIcon.CLOSE_BOX);
        settingsIcon.setOnMouseClicked(v -> openDepartmentModifyWindow());
        nameField.setCellValueFactory(data -> data.getValue().getFullNameProperty());
        emailField.setCellValueFactory(data -> data.getValue().emailProperty());
        phoneField.setCellValueFactory(data -> data.getValue().phoneProperty().get()<0?new SimpleObjectProperty<>():data.getValue().phoneProperty());
        initUserChangeListener();
        hideIcon.setOnMouseClicked((v) -> {
            if (!isHidden) {
                deadChildren.addAll(superAC.getChildren());
                superAC.getChildren().clear();
                hideIcon.setIcon(MaterialDesignIcon.PLUS_BOX);
                superAC.getChildren().add(hideIcon);
                isHidden = true;
            } else {
                superAC.getChildren().clear();
                superAC.getChildren().addAll(deadChildren);
                deadChildren.clear();
                manageIconsBP.setLeft(hideIcon);
                isHidden = false;
            }
        });
        removeIcon.setOnMouseClicked((v) -> {
            //TODO make some confirmation
            DepartmentManager departmentManager = new DepartmentManager();
            departmentManager.removeDepartment(department);
            superAC.getChildren().clear();
        });

    }

    private void openDepartmentModifyWindow() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUI/View/CRUDViews/AddDepartment.fxml"));
            AnchorPane pane = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(pane));
            AddDepartmentController con = loader.getController();
            con.setDepartment(department);
            SceneMover sceneMover = new SceneMover();
            sceneMover.move(stage, pane);
            stage.initStyle(StageStyle.TRANSPARENT);
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private AddDepartmentController openDepartmentAddWindow() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUI/View/CRUDViews/AddDepartment.fxml"));
            AnchorPane pane = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(pane));
            AddDepartmentController con = loader.getController();
            SceneMover sceneMover = new SceneMover();
            sceneMover.move(stage, pane);
            stage.initStyle(StageStyle.TRANSPARENT);
            stage.show();
            return con;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private void initUserChangeListener() {
        users.addListener((ListChangeListener<? super User>) change -> {
            change.next();
            if (change.getAddedSize() > 0)
                change.getAddedSubList().forEach(u -> dptUsersTable.getItems().add(u));
            if (change.getRemovedSize() > 0)
                change.getRemoved().forEach(u -> dptUsersTable.getItems().remove(u));
        });
    }

    public void setDepartment(Department department) {
        this.department = department;
        this.users = department.getUsers();
        this.subDepartments = department.getSubDepartments();
        dptUsersTable.setItems(department.getUsers());

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
        Button addSubDepartmentButton = new Button("Add sub department");
        hBox.getChildren().add(addSubDepartmentButton);
        addSubDepartmentButton.setOnMouseClicked((v) -> addSubDepartment());
        hBox.setAlignment(Pos.TOP_CENTER);
        superBP.setCenter(hBox);
    }

    public void addSubDepartment(Department subDepartment) {
        subDepartments.add(subDepartments.size(), subDepartment);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUI/View/DPT/DepartmentView.fxml"));
        try {
            Node node = loader.load();
            DepartmentViewController con = loader.getController();
            con.setDepartment(subDepartment);
            hBox.getChildren().add(hBox.getChildren().size() - 1, node);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void addSubDepartment() {
        AddDepartmentController addDepartmentController = openDepartmentAddWindow();
        addDepartmentController.setDepartmentViewController(this);
    }
}
