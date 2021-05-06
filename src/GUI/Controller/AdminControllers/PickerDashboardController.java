package GUI.Controller.AdminControllers;

import BE.ScreenBit;
import GUI.Controller.StageBuilder;
import GUI.Model.ScreenModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class PickerDashboardController {
    public AnchorPane pickerStageHere;
    @FXML
    private BorderPane borderPane;
    @FXML
    private Label lblTitle;

    private PickerStageController pickerStageController;
    private ScreenBit screenBit;
    StageBuilder stageBuilder = new StageBuilder();

    public void init(ScreenBit screenBit) throws Exception {

        Node node = stageBuilder.makeStage(screenBit.getScreenInfo().trim());
        pickerStageController = stageBuilder.getRootController();

        this.screenBit = screenBit;
        borderPane.setCenter(node);

    }

    public void setTitle(String title) {
        lblTitle.setText(title);
    }

    public void handleExit(MouseEvent mouseEvent) {
        handleCancel();
    }

    public void handleMaximize(MouseEvent mouseEvent) {
    }

    public void handleMinimize(MouseEvent mouseEvent) {
        Stage stage = (Stage) borderPane.getScene().getWindow();
        stage.setIconified(true);
    }

    public void handleCancel() {
        Stage stage = (Stage) borderPane.getScene().getWindow();
        stage.close();
    }

    public void handleSave(ActionEvent actionEvent) {

        ScreenBit oldScreenBit = screenBit;
        screenBit.setScreenInfo(pickerStageController.getParentBuilderString());
        ScreenModel screenModel = ScreenModel.getInstance();
        screenModel.updateScreen(screenBit, oldScreenBit);
        Stage stage = (Stage) borderPane.getScene().getWindow();
        stage.close();
    }
}
