package GUI.Controller;

import BE.Department;
import BLL.InfoboardPaneBuilder;
import GUI.Model.DataModel;
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
        for (Department d : DataModel.getInstance().getDepartments()) {
            if (!d.getUsers().isEmpty()) {
                hbox.getChildren().add(InfoboardPaneBuilder.createDepartmentLabel(d, hbox));
                vbox.getChildren().add(InfoboardPaneBuilder.createInfoBoard(d));
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
                        vbox.getChildren().add(InfoboardPaneBuilder.createInfoBoard(department));
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
            Node infoBoard = InfoboardPaneBuilder.createInfoBoard(cmbAddDepartment.getValue());
            vbox.getChildren().add(Math.min(hbox.getChildren().size(),DataModel.getInstance().getDepartments().indexOf(cmbAddDepartment.getValue())),infoBoard);
            Node departmentLabel = InfoboardPaneBuilder.createDepartmentLabel(cmbAddDepartment.getValue(), hbox);
            hbox.getChildren().add(Math.min(hbox.getChildren().size(),DataModel.getInstance().getDepartments().indexOf(cmbAddDepartment.getValue())),departmentLabel);
            cmbAddDepartment.getItems().remove(cmbAddDepartment.getSelectionModel().getSelectedItem());
            cmbAddDepartment.getSelectionModel().select(null);
        }
    }

    public void addToCombo(String departmentName) {
        if (cmbAddDepartment.getItems().stream().noneMatch(d->d.getName().equals(departmentName)))
            cmbAddDepartment.getItems().add(DataModel.getInstance().getDepartment(departmentName));
    }

}
