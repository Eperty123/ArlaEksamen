package GUI.Controller.AdminControllers;

import BE.Screen;
import GUI.Controller.StageBuilder;
import GUI.Model.ScreenModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class PickerDashboardController implements Initializable {
    @FXML
    private BorderPane borderPane;
    @FXML
    private Label lblTitle;

    private PickerStageController pickerStageController;
    private Screen screen;
    StageBuilder stageBuilder = new StageBuilder();


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void init(Screen screen) throws Exception {
        Node root = stageBuilder.makeStage("");
        pickerStageController = stageBuilder.getRootController();

        this.screen = screen;
        borderPane.setCenter(root);
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
        System.out.println(pickerStageController.getParentBuilderString());
        Screen oldScreen = screen;
        screen.setScreenInfo(pickerStageController.getParentBuilderString());
        ScreenModel screenModel = ScreenModel.getInstance();
        screenModel.updateScreen(screen, oldScreen);
        Stage stage = (Stage) borderPane.getScene().getWindow();
        stage.close();
    }
}
