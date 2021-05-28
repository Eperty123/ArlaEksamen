package GUI.Controller;

import BE.*;
import BLL.LoginManager;
import BLL.StageShower;
import GUI.Controller.PopupControllers.BugReportDialog;
import GUI.Controller.PopupControllers.ConfirmationDialog;
import GUI.Controller.PopupControllers.EScreenSelectDialog;
import GUI.Controller.PopupControllers.WarningController;
import GUI.Model.*;
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
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

public class EmployeeScreenController implements Initializable {
    @FXML
    private BorderPane borderPane;
    @FXML
    private Label lblBar;
    @FXML
    private Label lblTime;
    @FXML
    private TextArea txtMessage;
    @FXML
    private JFXComboBox<ScreenBit> comboScreens;
    private final ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();
    private List<Message> userMessages = new ArrayList<>();

    private User currentUser;
    private boolean isMaximized = false;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        currentUser = LoginManager.getCurrentUser();
        ClockCalender.initClock(lblTime);

        comboScreens.getItems().addAll(currentUser.getAssignedScreenBits());

        if (!currentUser.getAssignedScreenBits().isEmpty()) {
            if (currentUser.getAssignedScreenBits().size() == 1) {
                try {
                    setScreen(currentUser.getAssignedScreenBits().get(0));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                try {
                    EScreenSelectDialog selectDialog = new EScreenSelectDialog(currentUser.getAssignedScreenBits());

                    Optional<ScreenBit> results = selectDialog.showAndWait();

                    if (results.isPresent()) {
                        ScreenBit s = results.get();
                        lblBar.setText("Employee Screen - " + s.getName() + " - " + currentUser.getFirstName() + " " + currentUser.getLastName());
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
            }
        } else {
            lblBar.setText("Employee Screen - NONE Contact admin - " + currentUser.getFirstName() + " " + currentUser.getLastName());
            try {
                displayNoScreenWarning();
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
            //TODO fix such that this get the relevant messages for the current screen
            userMessages = new ArrayList<>(DataModel.getInstance().getMessages());
            userMessages.sort(Comparator.comparing(Message::getMessageStartTime));
            AtomicReference<Message> messageAtomicReference = new AtomicReference<>();
            userMessages.forEach(message -> {
                if (txtMessage.getText() == message.getMessage() || LocalDateTime.now().isBefore(message.getMessageStartTime()) || LocalDateTime.now().isAfter(message.getMessageEndTime())) {
                    if (message.getMessageEndTime().isBefore(LocalDateTime.now())) ;
                    DataModel.getInstance().deleteMessage(message);
                } else {
                    if (messageAtomicReference.get() == null || messageAtomicReference.get().getMessageType() != MessageType.Admin || message.getMessageType() == MessageType.Admin)
                        messageAtomicReference.set(message);
                }
            });
            Message message = messageAtomicReference.get();
            String textColor = String.format("rgb( %s , %s , %s )", message.getTextColor().getRed() * 255, message.getTextColor().getGreen() * 255, message.getTextColor().getBlue() * 255);
            String highLightTextFillColor = String.format("rgb( %s , %s , %s )", message.getTextColor().brighter().getRed() * 255, message.getTextColor().brighter().getGreen() * 255, message.getTextColor().brighter().getBlue() * 255);
            String highLightColor = String.format("rgb( %s , %s , %s )", message.getTextColor().darker().getRed() * 255, message.getTextColor().darker().getGreen() * 255, message.getTextColor().darker().getBlue() * 255);
            updateMessage(message, textColor, highLightTextFillColor, highLightColor);
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

    public void handleLogout() throws IOException {
        ConfirmationDialog confirmation = new ConfirmationDialog("Are you sure you want to logout of the application?");

        Optional<Boolean> result = confirmation.showAndWait();

        if (result.isPresent()) {
            if (result.get()) {
                SceneMover sceneMover = new SceneMover();
                StageShower stageShower = new StageShower();

                Stage root1 = (Stage) borderPane.getScene().getWindow();

                stageShower.handleLogout(root1, sceneMover);
            }
        }
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
    private void handleClose(MouseEvent mouseEvent) {
        Stage stage = (Stage) borderPane.getScene().getWindow();
        stage.close();
    }

    public void handleReportIssue() throws IOException {
        BugReportDialog reportDialog = new BugReportDialog();
        Optional<String> result = reportDialog.showAndWait();

        if (result.isPresent()) {
            if (!result.get().equals("CANCELED")) {
                Bug newBug = new Bug(result.get(), Timestamp.valueOf(LocalDateTime.now()).toString());
                newBug.setReferencedScreen(comboScreens.getSelectionModel().getSelectedItem() != null ? comboScreens.getSelectionModel().getSelectedItem().getName() : "None");
                newBug.setReferencedUser(currentUser.getUserName());
                DataModel.getInstance().addBug(newBug);
                WarningController.createWarning("Report Send!", "Bug report successfully send, " +
                        "thank you for helping improving this program!");
            }
        }
    }
}
