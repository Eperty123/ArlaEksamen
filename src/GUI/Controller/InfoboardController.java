package GUI.Controller;

import BE.Bug;
import BE.Department;
import BE.InfoboardPaneFactory;
import GUI.Model.DepartmentModel;
import com.jfoenix.controls.JFXComboBox;
import javafx.beans.InvalidationListener;
import javafx.collections.ListChangeListener;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ResourceBundle;

public class InfoboardController implements Initializable {
    @FXML
    private VBox vbox;
    @FXML
    private HBox hbox;
    @FXML
    private JFXComboBox<Department> cmbAddDepartment;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        for (Department d : DepartmentModel.getInstance().getAllDepartments()) {
            if (!d.getUsers().isEmpty()) {
                hbox.getChildren().add(InfoboardPaneFactory.createDepartmentLabel(d, hbox));
                vbox.getChildren().add(InfoboardPaneFactory.createInfoBoard(d));
            }
        }

        hbox.getChildren().addListener((ListChangeListener<Node>) c -> {
            if (c.next()) {
                if (c.wasRemoved()) {
                    Label l = (Label) c.getRemoved().get(0).lookup("#ID");
                    addToCombo(l.getText());
                    vbox.getChildren().clear();
                    for (Node n : hbox.lookupAll("#ID")){
                        Department department = DepartmentModel.getInstance().getDepartment(((Label) n).getText());
                        vbox.getChildren().add(InfoboardPaneFactory.createInfoBoard(department));
                    }
                }
            }

        });


    }


    public void handleAddDepartment() {
        vbox.getChildren().add(InfoboardPaneFactory.createInfoBoard(cmbAddDepartment.getValue()));
        hbox.getChildren().add(InfoboardPaneFactory.createDepartmentLabel(cmbAddDepartment.getValue(), hbox));
        cmbAddDepartment.getItems().remove(cmbAddDepartment.getValue());

    }

    public void addToCombo(String departmentName) {
        cmbAddDepartment.getItems().add(DepartmentModel.getInstance().getDepartment(departmentName));
    }

}
