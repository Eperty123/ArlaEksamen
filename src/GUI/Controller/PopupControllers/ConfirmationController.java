package GUI.Controller.PopupControllers;

import com.jfoenix.controls.JFXTextArea;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;

public class ConfirmationController {
    @FXML
    private JFXTextArea textArea;
    @FXML
    private AnchorPane root;

    public void setText(String text) {
        textArea.setText(text);
    }

    public boolean handleSave(){
        return true;
    }

    public boolean handleCancel(){
        return false;
    }
}
