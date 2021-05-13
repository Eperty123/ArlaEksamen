package GUI.Controller.PopupControllers;

import com.jfoenix.controls.JFXTextArea;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;

public class BugReportController {
    @FXML
    private JFXTextArea textArea;
    @FXML
    private AnchorPane root;

    public String handleJa(){
        return textArea.getText();
    }

    public String handleNej(){
        return "CANCELED";
    }

}
