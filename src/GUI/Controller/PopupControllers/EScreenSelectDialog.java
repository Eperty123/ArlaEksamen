package GUI.Controller.PopupControllers;

import BE.ScreenBit;
import com.jfoenix.controls.JFXComboBox;
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
import java.util.List;

public class EScreenSelectDialog extends Dialog<ScreenBit> {

    public EScreenSelectDialog(List<ScreenBit> screenBits) throws IOException {
        super();
        setData(screenBits);
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
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUI/View/PopUpViews/EmployeeScreenSelect.fxml"));
            Parent root = loader.load();
            EmployeeScreenSelectController controller = loader.getController();

            getDialogPane().setContent(root);
            //FIXME MEGET MEGET MEGET CRUDE Men det virker. FIX PLEASE
            AnchorPane test = (AnchorPane) getDialogPane().getContent().getScene().getRoot().getChildrenUnmodifiable().get(getDialogPane().getContent().getScene().getRoot().getChildrenUnmodifiable().size()-1);
            Pane test2 = (Pane) test.getChildren().get(0);
            JFXComboBox<ScreenBit> comboBox = (JFXComboBox) test2.getChildrenUnmodifiable().get(test2.getChildrenUnmodifiable().size()-3);

            comboBox.getItems().addAll(screenBits);

            setResultConverter(buttonType -> buttonType.getButtonData() == ButtonBar.ButtonData.OK_DONE ? controller.handleContinue() : controller.handleCancel());

        } catch (IOException e) {
            e.printStackTrace();
            WarningController.createWarning("Oh no! Something went wrong trying to load the " +
                    "employee screen selector view. It might be corrupted or lost.");
        }
    }

    public void setData(List<ScreenBit> screenBits) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUI/View/PopUpViews/EmployeeScreenSelect.fxml"));
        Parent root = (Parent)loader.load();
        EmployeeScreenSelectController controller = loader.getController();
        controller.setData(screenBits);

    }
}
