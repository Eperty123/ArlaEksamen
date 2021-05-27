package GUI.Controller.PopupControllers;

import com.jfoenix.controls.JFXTextArea;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;

public class BugReportController {
    @FXML
    private JFXTextArea textArea;
    @FXML
    private AnchorPane root;

    public String handleSave(){
        return textArea.getText();
    }

    public String handleCancel(){
        return "CANCELED";
    }
}
