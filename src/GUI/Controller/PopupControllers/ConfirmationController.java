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

    private boolean confirmed = false;

    public void setText(String text) {
        textArea.setText(text);
    }

    public void handleJa(){
        confirmed = true;
        Stage root1 = (Stage) root.getScene().getWindow();
        root1.close();
    }

    public void handleNej(){
        confirmed = true;
        Stage root1 = (Stage) root.getScene().getWindow();
        root1.close();
    }

    public boolean isConfirmed(){
        return confirmed;
    }
}
