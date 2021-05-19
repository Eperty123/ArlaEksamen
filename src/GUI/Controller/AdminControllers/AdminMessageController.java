package GUI.Controller.AdminControllers;

import BE.*;
import BLL.LoginManager;
import GUI.Model.MessageModel;
import GUI.Model.ScreenModel;
import com.jfoenix.controls.*;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIcon;
import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIconView;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.TextAlignment;

import java.net.URL;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

public class AdminMessageController implements Initializable {
    @FXML
    private BorderPane borderPane;
    @FXML
    private JFXDatePicker datePicker;
    @FXML
    private JFXTimePicker timePicker;
    @FXML
    private JFXColorPicker colorPicker;
    @FXML
    private FlowPane screenContainer;
    @FXML
    private JFXTextArea messageArea;
    @FXML
    private ChoiceBox<Integer> durationHoursChoice;
    @FXML
    private ChoiceBox<Integer> durationMinutesChoice;
    @FXML
    private TableView<Message> comingMessages;
    @FXML
    private TableColumn<Message, String> msgColumn;
    @FXML
    private TableColumn<Message, LocalTime> timeColumn;
    @FXML
    private TableColumn<Message, LocalDate> dateColumn;
    @FXML
    private JFXButton btnSelectAll;


    private User currentUser;
    private Boolean isAllSelected = false;

    private List<ScreenBit> selectedScreens = new ArrayList<>();
    private List<Message> currentUsersMessages = new ArrayList<>();


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        timePicker.set24HourView(true);
        //currentUsersMessages = MessageModel.getInstance().getUsersMessages(currentUser);

        datePicker.setValue(LocalDate.now());
        timePicker.setValue(LocalTime.now());

        setDurationChoiceBoxes();
        UpdateUpCommingMessages();


        loadAllScreens();
    }

    private void UpdateUpCommingMessages() {


    }

    private void setDurationChoiceBoxes() {
        durationHoursChoice.setItems(FXCollections.observableArrayList(0, 1, 2, 3, 4, 5, 6, 7, 8));
        durationHoursChoice.setValue(0);

        durationMinutesChoice.setItems(FXCollections.observableArrayList(
                0, 30));
        durationMinutesChoice.setValue(0);
    }

    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }

    private void loadAllScreens() {
        // Remove all nodes.
        screenContainer.getChildren().clear();
        // Add all screens.
        for (ScreenBit s : ScreenModel.getInstance().getAllScreenBits()) {
            makeScreen(s);
        }
    }

    private void makeScreen(ScreenBit screenBit) {
        Pane newPane = new Pane();
        newPane.setPrefSize(100, 100);

        newPane.setAccessibleText(screenBit.getName());
        Rectangle newRectangle = new Rectangle(100, 100);
        newRectangle.setArcHeight(33);
        newRectangle.setArcWidth(33);
        newRectangle.setFill(Paint.valueOf("#154c5d"));
        newRectangle.getStyleClass().add("SMButtons");

        FontAwesomeIconView check = new FontAwesomeIconView();
        check.setId("Check");
        check.setIcon(FontAwesomeIcon.CHECK_CIRCLE_ALT);
        check.setFill(Paint.valueOf("#97CE68"));
        check.getStyleClass().add("SMButtons");
        check.setLayoutX(77.3);
        check.setLayoutY(20.66);
        check.setSize(String.valueOf(13.33));
        check.setVisible(false);

        MaterialDesignIconView desktop = new MaterialDesignIconView();
        desktop.setIcon(MaterialDesignIcon.MONITOR);
        desktop.setFill(Paint.valueOf("#0d262e"));
        desktop.getStyleClass().add("SMButtons");
        desktop.setLayoutX(26);
        desktop.setLayoutY(68);
        desktop.setSize(String.valueOf(48));

        Label label = new Label();
        label.setText(screenBit.getName());
        label.setTextFill(Paint.valueOf("#FFFFFF"));
        label.setStyle("-fx-font-size: 11px;-fx-font-weight: bold; -fx-font-style: italic");
        label.setPrefSize(88.66, 16.66);
        label.setLayoutX(6);
        label.setLayoutY(74);
        label.setAlignment(Pos.TOP_CENTER);
        label.setContentDisplay(ContentDisplay.CENTER);
        label.setTextAlignment(TextAlignment.CENTER);

        newPane.getChildren().addAll(newRectangle, check, desktop, label);

        screenContainer.getChildren().add(0, newPane);

        FlowPane.setMargin(newPane, new Insets(15, 15, 0, 15));

        desktop.setOnMouseClicked(mouseEvent -> {
            if (!selectedScreens.contains(screenBit)) {
                selectedScreens.add(screenBit);
            } else {
                selectedScreens.remove(screenBit);
            }
            check.setVisible(!check.isVisible());
        });

        newRectangle.setOnMouseClicked(mouseEvent -> {
            if (!selectedScreens.contains(screenBit)) {
                selectedScreens.add(screenBit);
            } else {
                selectedScreens.remove(screenBit);
            }
            check.setVisible(!check.isVisible());
        });

        check.setOnMouseClicked(mouseEvent -> {
            if (!selectedScreens.contains(screenBit)) {
                selectedScreens.add(screenBit);
            } else {
                selectedScreens.remove(screenBit);
            }
            check.setVisible(!check.isVisible());
        });

        // Assign the screen its respective Pane object.
        screenBit.setPane(newPane);
    }

    public void handleSave() {
        Message newMessage = getMessage();

        MessageModel.getInstance().addMessage(currentUser, newMessage, selectedScreens);

        clearMessageFields();
        loadAllScreens();
    }

    private Message getMessage() {
        String message = messageArea.getText();
        Color color = colorPicker.getValue();
        LocalDateTime startTime = LocalDateTime.of(LocalDate.from(datePicker.getValue()), timePicker.getValue());
        System.out.println(getDurationHours());
        System.out.println(getDurationMinutes());
        LocalDateTime endTime = startTime.plusHours(getDurationHours()).plusMinutes(getDurationMinutes());
        MessageType messageType = currentUser.getUserRole() == UserType.Manager ? MessageType.Manager : MessageType.Admin;

        Message newMessage = new Message(startTime, endTime, message, color, messageType);
        return newMessage;
    }

    private long getDurationMinutes() {
        return durationMinutesChoice.getSelectionModel().getSelectedItem();
    }

    private long getDurationHours() {
        return durationHoursChoice.getSelectionModel().getSelectedItem();
    }

    public void handleCancel() {

    }

    private void clearMessageFields() {
        messageArea.clear();
        messageArea.setPromptText("Enter your message here...");
        colorPicker.setValue(null);
        datePicker.setValue(null);
        timePicker.setValue(null);
        durationHoursChoice.setValue(0);
        durationMinutesChoice.setValue(0);
    }

    public void showSelectedMessage(MouseEvent mouseEvent) {

        messageArea.setText("hey");
    }

    public void handleSelectAll() {
        isAllSelected = !isAllSelected;

        for (ScreenBit s : ScreenModel.getInstance().getAllScreenBits()) {
            if (isAllSelected) {
                btnSelectAll.setText("Deselect All");
                if (!selectedScreens.contains(s)) {
                    selectedScreens.add(s);
                }
            } else {
                btnSelectAll.setText("Select All");
                if (selectedScreens.contains(s)) {
                    selectedScreens.remove(s);
                }
            }
        }
        if (isAllSelected) {
            for (int i = 0; i < screenContainer.getChildren().size(); i++) {
                Pane p = (Pane) screenContainer.getChildren().get(i);
                FontAwesomeIconView f = (FontAwesomeIconView) p.getChildren().get(1);
                f.setVisible(true);
            }
        } else {
            for (int i = 0; i < screenContainer.getChildren().size(); i++) {
                Pane p = (Pane) screenContainer.getChildren().get(i);
                FontAwesomeIconView f = (FontAwesomeIconView) p.getChildren().get(1);
                f.setVisible(false);
            }
        }
    }

    public void handleDeleteMessage(){
        MessageModel.getInstance().deleteMessage(comingMessages.getSelectionModel().getSelectedItem());
    }
}
