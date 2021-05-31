package GUI.Controller.AdminControllers;

import BE.ScreenBit;
import GUI.Model.DataModel;
import GUI.Model.ScreenModel;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class NewScreenController {
    @FXML
    private JFXTextField txtScreenName;
    @FXML
    private AnchorPane root;


    public String handleContinue() {
        if (!txtScreenName.getText().isEmpty()){

            Stage stage = (Stage) root.getScene().getWindow();
            stage.close();
            return txtScreenName.getText();
        }
        return null;
    }

    public void handleCancel() {
        Stage stage = (Stage) root.getScene().getWindow();
        stage.close();
    }


}
