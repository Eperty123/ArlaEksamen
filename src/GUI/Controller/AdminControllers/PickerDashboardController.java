package GUI.Controller.AdminControllers;

import BE.ScreenBit;
import GUI.Controller.PopupControllers.ConfirmationDialog;
import GUI.Controller.PopupControllers.WarningController;
import BLL.StageBuilder;
import GUI.Model.DataModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Optional;

public class PickerDashboardController {
    public AnchorPane pickerStageHere;
    @FXML
    private BorderPane borderPane;
    @FXML
    private Label lblTitle;

    private PickerStageController pickerStageController;
    private ScreenBit screenBit;
    private Boolean isMaximized = false;
    StageBuilder stageBuilder = new StageBuilder();

    /**
     * Initializes the given screenBit
     * @param screenBit
     * @throws Exception
     */
    public void init(ScreenBit screenBit) throws Exception {
        Node node = stageBuilder.makeStage(screenBit.getScreenInfo().trim());
        pickerStageController = stageBuilder.getRootController();

        this.screenBit = screenBit;
        borderPane.setCenter(node);
    }

    /**
     * Set the title of the stage
     * @param title
     */
    public void setTitle(String title) {
        lblTitle.setText(title);
    }

    /**
     * Closes the stage
     * @param mouseEvent
     * @throws IOException
     */
    public void handleExit(MouseEvent mouseEvent) throws IOException {
        handleCancel();
    }

    /**
     * Maximizes the stage
     * @param mouseEvent
     */
    public void handleMaximize(MouseEvent mouseEvent) {
        isMaximized = !isMaximized;
        Stage stage = (Stage) borderPane.getScene().getWindow();
        stage.setMaximized(isMaximized);
    }

    /**
     * Iconizes the window
     * @param mouseEvent
     */
    public void handleMinimize(MouseEvent mouseEvent) {
        Stage stage = (Stage) borderPane.getScene().getWindow();
        stage.setIconified(true);
    }

    /**
     * Gives a warning, and closes the stage
     * @throws IOException
     */
    public void handleCancel() throws IOException {
        String text = """
                Unsaved changes are made!\s

                Are you sure you want to cancel your progress on the current screen?\s

                All unsaved progress will be lost.""";
        ConfirmationDialog confirmationDialog = new ConfirmationDialog(text);
        Optional<Boolean> result = confirmationDialog.showAndWait();
        if (result.isPresent()) {
            if (result.get()) {
                Stage stage = (Stage) borderPane.getScene().getWindow();
                stage.close();
            }
        }
    }

    /**
     * Saves the changes to the screen
     * @param actionEvent
     */
    public void handleSave(ActionEvent actionEvent) {
        ScreenBit oldScreenBit = screenBit;
        screenBit.setScreenInfo(pickerStageController.getParentBuilderString());
        //ScreenModel screenModel = ScreenModel.getInstance();
        try {
            DataModel.getInstance().updateScreenBit(screenBit, oldScreenBit);
            //System.out.println(oldScreenBit.getName() + " " + oldScreenBit.getScreenInfo());
            Stage stage = (Stage) borderPane.getScene().getWindow();
            stage.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            WarningController.createWarning("Oh no! Something went wrong when attempting to update the given screen in the Database. Please try again, and if the problem persists, contact an IT Administrator.");
        }
    }
}
