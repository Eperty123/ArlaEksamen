package GUI.Controller.PopupControllers;

import com.jfoenix.controls.JFXTextArea;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class WarningController {
    @FXML
    private AnchorPane root;
    @FXML
    private JFXTextArea textArea;

    public void setText(String text){
        textArea.setText(text);
    }

    public void handleOk(){
        Stage stage = (Stage) root.getScene().getWindow();
        stage.close();
    }
}
