package GUI.Controller.AdminControllers;

import GUI.Controller.PopupControllers.WarningController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.stage.Modality;
import javafx.stage.StageStyle;

import java.io.IOException;

public class NewScreenDialog extends Dialog<String> {

    public NewScreenDialog(String string) {
        super();
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
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Gui/View/AdminViews/NewScreen.fxml"));
            Parent root = loader.load();
            NewScreenController controller = loader.<NewScreenController>getController();

            getDialogPane().setContent(root);
            setResultConverter(buttonType -> buttonType.getButtonData() == ButtonBar.ButtonData.OK_DONE ? controller.handleContinue() : null);

        } catch (IOException e) {
            e.printStackTrace();
            WarningController.createWarning("Oh no! Something went wrong trying to load the " +
                    "new screen view. It might be corrupted or lost.");
        }
    }
}
