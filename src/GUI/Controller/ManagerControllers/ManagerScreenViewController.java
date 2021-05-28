package GUI.Controller.ManagerControllers;

import BE.*;
import BLL.EmailManager;
import BLL.LoginManager;
import GUI.Controller.PopupControllers.BugReportDialog;
import GUI.Controller.PopupControllers.WarningController;
import GUI.Controller.StageBuilder;
import GUI.Model.DataModel;
import com.jfoenix.controls.JFXComboBox;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

public class ManagerScreenViewController implements Initializable {
    @FXML
    private BorderPane borderPane;
    @FXML
    private JFXComboBox<ScreenBit> comboScreens;
    @FXML
    private Label lblBar;
    @FXML
    private Label lblTime;
    @FXML
    private TextArea txtMessage;

    private User currentUser;
    private boolean isMaximized = false;
    private final ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();
    private Stage parentStage;
    private ScreenBit selectedScreen;
    private EmailManager emailManager = EmailManager.getInstance();
    private DataModel dataModel = DataModel.getInstance();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        currentUser = LoginManager.getCurrentUser();
        ClockCalender.initClock(lblTime);

        comboScreens.getItems().addAll(currentUser.getAssignedScreenBits());

    }

    public void init(ScreenBit screenBit) {
        if (!currentUser.getAssignedScreenBits().isEmpty()) {
            if (currentUser.getAssignedScreenBits().size() == 1) {
                try {
                    setScreen(currentUser.getAssignedScreenBits().get(0));
                    comboScreens.setValue(currentUser.getAssignedScreenBits().get(0));
                    lblBar.setText("Manager Screen - " + currentUser.getAssignedScreenBits().get(0).getName() + " - " + currentUser.getFirstName() + " " + currentUser.getLastName());

                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {


                ScreenBit s = screenBit;
                comboScreens.getSelectionModel().select(s);
                lblBar.setText("Manager Screen - " + s.getName() + " - " + currentUser.getFirstName() + " " + currentUser.getLastName());

                try {
                    setScreen(s);
                    selectedScreen = s;
                    DataModel.getInstance().loadScreenBitsMessages(selectedScreen);
                } catch (Exception e) {
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
        autoUpdateMessageBox();
    }

    private void autoUpdateMessageBox() {
        service.scheduleAtFixedRate(new Thread(() -> {

            AtomicReference<Message> messageAtomicReference = new AtomicReference<>(selectedScreen.getCurrentMessage());
            Message currentMessage = messageAtomicReference.get();

            String textColor = String.format("rgb( %s , %s , %s )", currentMessage.getTextColor().getRed() * 255, currentMessage.getTextColor().getGreen() * 255, currentMessage.getTextColor().getBlue() * 255);
            String highLightTextFillColor = String.format("rgb( %s , %s , %s )", currentMessage.getTextColor().brighter().getRed() * 255, currentMessage.getTextColor().brighter().getGreen() * 255, currentMessage.getTextColor().brighter().getBlue() * 255);
            String highLightColor = String.format("rgb( %s , %s , %s )", currentMessage.getTextColor().darker().getRed() * 255, currentMessage.getTextColor().darker().getGreen() * 255, currentMessage.getTextColor().darker().getBlue() * 255);
            updateMessage(currentMessage, textColor, highLightTextFillColor, highLightColor);
        }), 0, Integer.parseInt(DataModel.getInstance().getSettingByType(SettingsType.MESSAGE_CHECK_FREQUENCY).getAttribute()), TimeUnit.SECONDS);
    }

    private void updateMessage(Message message, String textColor, String highLightTextFillColor, String hightLightColor) {
        Platform.runLater(new Thread(() -> {
            txtMessage.setStyle(String.format("-fx-text-fill: %s; -fx-highlight-text-fill: %s; -fx-highlight-fill: %s;", textColor, highLightTextFillColor, hightLightColor));
            txtMessage.setText(message.getMessage());
        }));
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

    /**
     * Handle bug reports.
     *
     * @throws IOException
     */
    public void handleReportIssue() throws IOException {
        BugReportDialog reportDialog = new BugReportDialog();
        Optional<String> result = reportDialog.showAndWait();

        if (result.isPresent()) {
            if (!result.get().equals("CANCELED")) {
                Bug newBug = new Bug(result.get(), Timestamp.valueOf(LocalDateTime.now()).toString());
                newBug.setReferencedScreen(comboScreens.getSelectionModel().getSelectedItem() != null ? comboScreens.getSelectionModel().getSelectedItem().getName() : "None");
                newBug.setReferencedUser(currentUser.getUserName());

                try {
                    dataModel.addBug(newBug);

                    //TODO uncheck

                    // Check if we can send emails at all.
                    if (emailManager.canSendEmail()) {
                        // Proceed to do so.
                        //EmailExtension.sendEmailBugReportToAllAdmins(newBug, comboScreens.getSelectionModel().getSelectedItem(), currentUser);
                    } else
                        WarningController.createWarning("The email for sending email notification for administrators is incorrect! Please contact an IT-Administrator about this!");
                    WarningController.createWarning("Report Send!", "Bug report successfully send, " +
                            "thank you for helping improving this program!");

                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                    WarningController.createWarning("Oh no! Failed to add the bug report to the database. If this persists, contact an IT-Administrator.");
                }


            }
        }
    }

    public void handleBack() {
        handleClose();
    }

    public void setParentStage(Stage parentStage) {
        this.parentStage = parentStage;
    }
}
