package GUI.Controller.PopupControllers;

import BE.Bug;
import GUI.Model.BugModel;
import com.jfoenix.controls.JFXTextArea;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Optional;

public class ConfirmBugController {
    @FXML
    private JFXTextArea textArea;
    @FXML
    private AnchorPane root;

    private Bug selectedBug;

    public void setSelectedBug(Bug selectedBug) {
        this.selectedBug = selectedBug;
    }

    public String getText() {
        return textArea.getText();
    }

    public void handleSave() throws IOException {
        ConfirmationDialog confirmationDialog = new ConfirmationDialog("Are you sure you want to save this bug?\n\n" +
                "The bug will be marked as resolved and will not be able to be editted");

        Optional<Boolean> result = confirmationDialog.showAndWait();
        if (result.isPresent()){
            if (result.get()){
                Bug updatedbug = selectedBug;
                updatedbug.setFixMessage(getText());
                updatedbug.setBugResolved(1);

                BugModel.getInstance().updateBug(selectedBug,updatedbug);

                Stage stage = (Stage) root.getScene().getWindow();
                stage.close();
            }
        }
    }

    public void handleCancel() throws IOException {
        ConfirmationDialog confirmationDialog = new ConfirmationDialog("Are you sure you want to cancel?\n\n" +
                "The bug will not be marked as resolved");

        Optional<Boolean> result = confirmationDialog.showAndWait();
        if (result.isPresent()){
            if (result.get()){
                Stage stage = (Stage) root.getScene().getWindow();
                stage.close();
            }
        }
    }

}
