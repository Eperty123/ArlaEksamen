package GUI.Controller.PopupControllers;

import GUI.Controller.AdminControllers.NewScreenController;
import com.jfoenix.controls.JFXTextArea;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
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
import java.net.URL;
import java.util.ResourceBundle;

public class ConfirmationDialog extends Dialog<Boolean> {

    public ConfirmationDialog(String text) throws IOException {
        super();
        setText(text);
        initStyle(StageStyle.UNDECORATED);
        initModality(Modality.APPLICATION_MODAL);

        var btnContinue = new ButtonType("Continue", ButtonBar.ButtonData.OK_DONE);
        var btnCancel = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);

        getDialogPane().getButtonTypes().addAll(btnContinue,btnCancel);

        var continueBtn = (Button) getDialogPane().lookupButton(btnContinue);
        var cancelBtn = (Button) getDialogPane().lookupButton(btnCancel);
        getDialogPane().getStylesheets().add(getClass().getResource("/GUI/Resources/Styles.css").toExternalForm());
        getDialogPane().getStyleClass().add("myDialog");
        continueBtn.getStyleClass().add("ja");
        cancelBtn.getStyleClass().add("nej");


        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Gui/View/PopUpViews/Confirmation.fxml"));
            Parent root = loader.load();
            ConfirmationController controller = loader.getController();

            getDialogPane().setContent(root);
            //FIXME MEGET MEGET MEGET CRUDE Men det virker. FIX PLEASE
            AnchorPane test = (AnchorPane) getDialogPane().getContent().getScene().getRoot().getChildrenUnmodifiable().get(getDialogPane().getContent().getScene().getRoot().getChildrenUnmodifiable().size()-1);
            Pane test2 = (Pane) test.getChildren().get(0);
            JFXTextArea textArea = (JFXTextArea) test2.getChildrenUnmodifiable().get(test2.getChildrenUnmodifiable().size()-1);

            textArea.setText(text);

            setResultConverter(buttonType -> buttonType.getButtonData() == ButtonBar.ButtonData.OK_DONE ? controller.handleJa() : controller.handleNej());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setText(String text) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Gui/View/PopUpViews/Confirmation.fxml"));
        Parent root = loader.load();
        ConfirmationController controller = loader.getController();

        controller.setText(text);
    }
}
