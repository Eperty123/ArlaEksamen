package GUI.Controller.ManagerControllers;

import BE.SceneMover;
import BE.ScreenBit;
import BE.User;
import BLL.LoginManager;
import GUI.Controller.PopupControllers.ConfirmationDialog;
import GUI.Controller.PopupControllers.EScreenSelectDialog;
import GUI.Controller.PopupControllers.WarningController;
import GUI.Controller.StageBuilder;
import GUI.Model.ScreenModel;
import com.jfoenix.controls.JFXComboBox;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import javax.swing.event.DocumentEvent;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class ManagerScreenViewController implements Initializable {
    @FXML
    private BorderPane borderPane;
    @FXML
    private JFXComboBox<ScreenBit> comboScreens;

    private User currentUser;
    private boolean isMaximized = false;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        currentUser = LoginManager.getCurrentUser();

        comboScreens.getItems().addAll(currentUser.getAssignedScreen());

        if (!currentUser.getAssignedScreen().isEmpty()) {
            try {
                EScreenSelectDialog selectDialog = new EScreenSelectDialog(currentUser.getAssignedScreen());

                Optional<ScreenBit> results = selectDialog.showAndWait();

                if (results.isPresent()) {
                    ScreenBit s = results.get();
                    comboScreens.getSelectionModel().select(s);
                    try {
                        setScreen(s);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }


        } else {
             try {
                displayNoScreenWarning();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        comboScreens.setOnAction(e -> {
            if (comboScreens.getSelectionModel().getSelectedItem() != null){
                try {
                    setScreen(comboScreens.getValue());
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            }
        });
    }

    private void displayNoScreenWarning() throws IOException {
        String text = "You have not been assigned a screen yet. \n\n" +
                "Please, contact an IT administrator as soon as possible, to resolve this problem.";
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/GUI/View/PopUpViews/Warning.fxml"));
        Parent root = (Parent) loader.load();

        WarningController warningController = loader.getController();
        warningController.setText(text);
        Stage stage = new Stage();

        Scene scene = new Scene(root);

        stage.getIcons().addAll(
                new Image("/GUI/Resources/AppIcons/icon16x16.png"),
                new Image("/GUI/Resources/AppIcons/icon24x24.png"),
                new Image("/GUI/Resources/AppIcons/icon32x32.png"),
                new Image("/GUI/Resources/AppIcons/icon48x48.png"),
                new Image("/GUI/Resources/AppIcons/icon64x64.png"));
        stage.setAlwaysOnTop(true);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setScene(scene);
        stage.show();

    }

    private void setScreen(ScreenBit s) throws Exception {
        StageBuilder stageBuilder = new StageBuilder();
        Node screen = stageBuilder.makeStage(s.getScreenInfo());
        stageBuilder.getRootController().lockPanes();
        borderPane.setCenter(screen);
    }

}
