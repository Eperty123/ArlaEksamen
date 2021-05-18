package GUI.Controller.PopupControllers;

import com.jfoenix.controls.JFXTextArea;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.StageStyle;

import java.io.IOException;

public class BugReportDialog extends Dialog<String> {

    public BugReportDialog() throws IOException {
        super();
        initStyle(StageStyle.UNDECORATED);
        initModality(Modality.APPLICATION_MODAL);

        var btnContinue = new ButtonType("Send", ButtonBar.ButtonData.OK_DONE);
        var btnCancel = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);

        getDialogPane().getButtonTypes().addAll(btnContinue,btnCancel);

        var continueBtn = (Button) getDialogPane().lookupButton(btnContinue);
        var cancelBtn = (Button) getDialogPane().lookupButton(btnCancel);
        getDialogPane().getStylesheets().add(getClass().getResource("/GUI/Resources/Styles.css").toExternalForm());
        getDialogPane().getStyleClass().add("myDialog");
        continueBtn.getStyleClass().add("ja");
        cancelBtn.getStyleClass().add("nej");


        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUI/View/PopUpViews/BugReport.fxml"));
            Parent root = loader.load();
            BugReportController controller = loader.getController();

            getDialogPane().setContent(root);

            setResultConverter(buttonType -> buttonType.getButtonData() == ButtonBar.ButtonData.OK_DONE ? controller.handleSave() : controller.handleCancel());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
