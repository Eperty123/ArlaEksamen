package GUI.Controller;

import BE.Department;
import BE.InfoboardPaneFactory;
import GUI.Model.DataModel;
import GUI.Model.DepartmentModel;
import javafx.collections.ListChangeListener;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
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
    private ScrollPane scroll;
    @FXML
    private ChoiceBox<Department> cmbAddDepartment;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        for (Department d : DepartmentModel.getInstance().getAllDepartments()) {
            if (!d.getUsers().isEmpty()) {
                hbox.getChildren().add(InfoboardPaneFactory.createDepartmentLabel(d, hbox));
                vbox.getChildren().add(InfoboardPaneFactory.createInfoBoard(d));
            }
        }

        cmbAddDepartment.setOnAction(e -> {
            handleAddDepartment();
        });


        hbox.getChildren().addListener((ListChangeListener<Node>) c -> {
            if (c.next()) {
                if (c.wasRemoved()) {
                    Label l = (Label) c.getRemoved().get(0).lookup("#ID");
                    addToCombo(l.getText());
                    vbox.getChildren().clear();
                    for (Node n : hbox.lookupAll("#ID")) {
                        Department department = DataModel.getInstance().getDepartment(((Label) n).getText());
                        vbox.getChildren().add(InfoboardPaneFactory.createInfoBoard(department));
                    }
                }
            }
        });


    }

    private void autoFitSize() {
        scroll.widthProperty().addListener((observable, t, t1) -> {
            hbox.setPrefWidth(t1.doubleValue());
            hbox.setMaxWidth(t1.doubleValue());
            hbox.setMinWidth(t1.doubleValue());
        });
    }
  
    public void handleAddDepartment() {
        if (cmbAddDepartment.getValue() != null) {
            Node infoBoard = InfoboardPaneFactory.createInfoBoard(cmbAddDepartment.getValue());
            vbox.getChildren().add(Math.min(hbox.getChildren().size(),DepartmentModel.getInstance().getAllDepartments().indexOf(cmbAddDepartment.getValue())),infoBoard);
            Node departmentLabel = InfoboardPaneFactory.createDepartmentLabel(cmbAddDepartment.getValue(), hbox);
            hbox.getChildren().add(Math.min(hbox.getChildren().size(),DepartmentModel.getInstance().getAllDepartments().indexOf(cmbAddDepartment.getValue())),departmentLabel);
            cmbAddDepartment.getItems().remove(cmbAddDepartment.getSelectionModel().getSelectedItem());
            cmbAddDepartment.getSelectionModel().select(null);
        }
    }

    public void addToCombo(String departmentName) {
        if (cmbAddDepartment.getItems().stream().noneMatch(d->d.getName().equals(departmentName)))
            cmbAddDepartment.getItems().add(DataModel.getInstance().getDepartment(departmentName));
    }

}
