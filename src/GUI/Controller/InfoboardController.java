package GUI.Controller;

import BE.Department;
import BE.InfoboardPaneFactory;
import GUI.Model.DepartmentModel;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ResourceBundle;

public class InfoboardController implements Initializable {
    @FXML
    private VBox vbox;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        for (Department d : DepartmentModel.getInstance().getAllDepartments()){
            if (!d.getUsers().isEmpty())
            vbox.getChildren().add(InfoboardPaneFactory.createInfoBoard(d,vbox));
        }
    }
}
