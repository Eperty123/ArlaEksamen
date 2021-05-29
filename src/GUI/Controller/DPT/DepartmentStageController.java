package GUI.Controller.DPT;

import BE.Department;
import BE.User;
import GUI.Controller.PopupControllers.WarningController;
import GUI.Model.DataModel;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import java.net.URL;
import java.util.ResourceBundle;

public class DepartmentStageController implements Initializable {
    @FXML
    private JFXTextField searchField;
    @FXML
    private TableView<User> userTable;
    @FXML
    private TableColumn<User, String> userTableColumn;
    @FXML
    private HBox hBox;
    @FXML
    private AnchorPane root;

    private final List<DepartmentViewController> departmentViewControllers = new ArrayList<>();

    public HBox gethBox() {
        return hBox;
    }

    /**
     * Iniitalizes tables, variables, makes the table items draggable
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        try {
            ObservableList<User> allUsers = DataModel.getInstance().getUsers();
            ObservableList<User> userCopy = FXCollections.observableArrayList(DataModel.getInstance().getUsers());
            userCopy.removeIf(u -> u.getUserName().equals("place"));
            userTable.setItems(userCopy);
            userTableColumn.setCellValueFactory(data -> data.getValue().getFullNameProperty());

            initSearchField(allUsers);

            TableDragMod.setUeditableTable(userTable);
            TableDragMod.makeTableDraggable(userTable);
            autofitSize();
        }
        catch (NullPointerException throwables) {
            throwables.printStackTrace();
            WarningController.createWarning("Oh no! Failed to load all users! Please try again. If this persists, contact an IT-Administrator.");
        }
    }

    /**
     * Initializes a simple search function on the searchField
     * @param allUsers the users you can search from
     */
    private void initSearchField(ObservableList<User> allUsers) {
        searchField.setOnKeyReleased((v) -> {
                    allUsers.forEach(u -> {
                        if (!u.getFullNameProperty().get().toLowerCase().contains(searchField.getText().toLowerCase()))
                            userTable.getItems().remove(u);
                        else if (!userTable.getItems().contains(u) && !u.getUserName().equals("place"))
                            userTable.getItems().add(u);
                    });
                }
        );
    }

    /**
     * Fits the user table to the bounds of the root
     */
    private void autofitSize() {
        root.heightProperty().addListener(((observableValue, number, t1) -> {
            userTable.setMaxHeight(t1.doubleValue());
            userTable.setMinHeight(t1.doubleValue());
            userTable.setPrefHeight(t1.doubleValue());
        }));
    }

    /**
     * Sets the children nodes of the hBox
     * @param childNodes the new nodes you want the hBox to have
     */
    public void setChildrenNodes(List<Node> childNodes) {
        hBox.getChildren().clear();
        hBox.getChildren().addAll(childNodes);
    }

    /**
     * Gets the list of child notes
     * @return a list of child notes
     */
    public List<Node> getChildrenNodes() {
        return hBox.getChildren();
    }

    /**
     * Adds the given department as a child note of the hBox
     * @param department the department
     */
    public void addChildrenNode(Department department) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/GUI/View/DPT/DepartmentView.fxml"));
        try {
            hBox.getChildren().add(fxmlLoader.load());
            DepartmentViewController con = fxmlLoader.getController();
            con.setDepartment(department);
            departmentViewControllers.add(con);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Gets the department view controllers
     * @return the departmentViewContrllers
     */
    public List<DepartmentViewController> getDepartmentViewControllers() {
        return departmentViewControllers;
    }
}

