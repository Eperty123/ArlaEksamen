package GUI.Controller.ManagerControllers;

import BE.Bug;
import BE.SceneMover;
import BE.ScreenBit;
import BE.User;
import BLL.LoginManager;
import GUI.Controller.PopupControllers.BugReportDialog;
import GUI.Controller.PopupControllers.ConfirmationDialog;
import GUI.Controller.PopupControllers.EScreenSelectDialog;
import GUI.Controller.PopupControllers.WarningController;
import GUI.Controller.StageBuilder;
import GUI.Model.BugModel;
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
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.ResourceBundle;

public class ManagerScreenViewController implements Initializable {
    @FXML
    private BorderPane borderPane;
    @FXML
    private JFXComboBox<ScreenBit> comboScreens;
    @FXML
    private Label lblBar;

    private User currentUser;
    private boolean isMaximized = false;
    private Stage parentStage;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        currentUser = LoginManager.getCurrentUser();

        comboScreens.getItems().addAll(currentUser.getAssignedScreen());
        if (!currentUser.getAssignedScreen().isEmpty()) {
            if (currentUser.getAssignedScreen().size() == 1) {
                try {
                    setScreen(currentUser.getAssignedScreen().get(0));
                    comboScreens.setValue(currentUser.getAssignedScreen().get(0));
                    lblBar.setText("Manager Screen - " + currentUser.getAssignedScreen().get(0).getName()+ " - " + currentUser.getFirstName() + " " + currentUser.getLastName());

                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                try {
                    EScreenSelectDialog selectDialog = new EScreenSelectDialog(currentUser.getAssignedScreen());

                    Optional<ScreenBit> results = selectDialog.showAndWait();

                    if (results.isPresent()) {
                        ScreenBit s = results.get();
                        comboScreens.getSelectionModel().select(s);
                        lblBar.setText("Manager Screen - " + s.getName() + " - " + currentUser.getFirstName() + " " + currentUser.getLastName());

                        try {
                            setScreen(s);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } else {
            try {
                displayNoScreenWarning();
                lblBar.setText("Manager Screen - NONE Contact admin - " + currentUser.getFirstName() + " " + currentUser.getLastName());
            } catch (IOException e) {
                e.printStackTrace();
            }

    }

        comboScreens.setOnAction(e -> {
            if (comboScreens.getSelectionModel().getSelectedItem() != null) {
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

    @FXML
    private void handleMinimize(MouseEvent mouseEvent) {
        Stage stage = (Stage) borderPane.getScene().getWindow();
        stage.setIconified(true);
    }

    @FXML
    private void handleMaximize(MouseEvent mouseEvent) {
        isMaximized = !isMaximized;
        Stage stage = (Stage) borderPane.getScene().getWindow();
        stage.setMaximized(isMaximized);
    }

    @FXML
    private void handleClose() {
        parentStage.setIconified(false);
        Stage stage = (Stage) borderPane.getScene().getWindow();
        stage.close();
    }

    public void handleReportIssue() throws IOException {
        BugReportDialog reportDialog = new BugReportDialog();
        Optional<String> result = reportDialog.showAndWait();

        if (result.isPresent()){
            if (!result.get().equals("CANCELED")){
                Bug newBug = new Bug(result.get(), Timestamp.valueOf(LocalDateTime.now()).toString());
                newBug.setReferencedScreen(comboScreens.getSelectionModel().getSelectedItem() != null ? comboScreens.getSelectionModel().getSelectedItem().getName() : "None");
                newBug.setReferencedUser(currentUser.getUserName());
                BugModel.getInstance().addBug(newBug);
                WarningController.createWarning("Report Send!","Bug report successfully send, " +
                        "thank you for helping improving this program!");
            }
        }
    }

    public void handleBack(){
        handleClose();
    }

    public void setParentStage(Stage parentStage) {
        this.parentStage = parentStage;
    }
}
