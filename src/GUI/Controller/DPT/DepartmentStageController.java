package GUI.Controller.DPT;

import BE.Department;
import BE.User;
import GUI.Model.UserModel;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.HBox;
import java.io.IOException;
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

    public HBox gethBox() {
        return hBox;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ObservableList<User> allUsers = UserModel.getInstance().getAllUsers();

        userTable.setItems(FXCollections.observableArrayList(UserModel.getInstance().getAllUsers()));

        userTableColumn.setCellValueFactory(data -> data.getValue().getFullNameProperty());

        searchField.setOnKeyReleased((v) -> {
                    allUsers.forEach(u -> {
                        if (!u.getFullNameProperty().get().toLowerCase().contains(searchField.getText().toLowerCase()))
                            userTable.getItems().remove(u);
                        else if (!userTable.getItems().contains(u))
                            userTable.getItems().add(u);
                    });
                }
        );

        TableDragMod.setDontDeleteFromTable(userTable);
        TableDragMod.makeTableDraggable(userTable);
    }

    public void setChildrenNodes(List<Node> childNodes) {
        hBox.getChildren().clear();
        hBox.getChildren().addAll(childNodes);
    }

    public List<Node> getChildrenNodes() {
        return hBox.getChildren();
    }

    public void addChildrenNode(Department department) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/GUI/View/DPT/DepartmentView.fxml"));
        try {
            hBox.getChildren().add(fxmlLoader.load());
            DepartmentViewController con = fxmlLoader.getController();
            con.setDepartment(department);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

