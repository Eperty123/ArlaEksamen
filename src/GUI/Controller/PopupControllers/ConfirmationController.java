package GUI.Controller.PopupControllers;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextArea;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

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
