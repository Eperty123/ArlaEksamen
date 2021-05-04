package GUI.Controller.PopupControllers;

import com.jfoenix.controls.JFXTextArea;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class ConfirmationController {
    @FXML
    private JFXTextArea textArea;
    @FXML
    private AnchorPane root;

    public void setText(String text) {
        System.out.println(text);
        textArea.setText(text);
    }

    public boolean handleJa(){
        return true;
    }

    public boolean handleNej(){
        return false;
    }

}
