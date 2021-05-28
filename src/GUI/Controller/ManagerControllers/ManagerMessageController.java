package GUI.Controller.ManagerControllers;

import BE.*;
import BLL.LoginManager;
import BLL.TimeSlotCalculator;
import GUI.Controller.PopupControllers.ConfirmationDialog;
import GUI.Controller.PopupControllers.WarningController;
import GUI.Model.MessageModel;
import GUI.Model.ScreenModel;
import GUI.Model.DataModel;
import com.jfoenix.controls.*;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIcon;
import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIconView;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
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
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.ResourceBundle;

public class ManagerMessageController implements Initializable {
    @FXML
    private BorderPane borderPane;
    @FXML
    private JFXDatePicker datePicker;
    @FXML
    private JFXComboBox<Integer> hourBox;
    @FXML
    private JFXComboBox<Integer> minuteBox;
    @FXML
    private JFXColorPicker colorPicker;
    @FXML
    private FlowPane screenContainer;
    @FXML
    private JFXTextArea messageArea;
    @FXML
    private JFXComboBox<Integer> durationHoursChoice;
    @FXML
    private JFXComboBox<Integer> durationMinutesChoice;
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

    private final List<ScreenBit> selectedScreens = new ArrayList<>();
    private final ObservableList<Message> currentUsersMessages = FXCollections.observableArrayList();
    private Message selectedMessage;
    private Boolean isAllSelected = false;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        currentUser = LoginManager.getCurrentUser();
        datePicker.setValue(LocalDate.now());


        initializeTable();
        handleMessageUpdate();
        setDurationChoiceBoxes();
        loadAllScreens();

        // Set the Message Model's current user to be the one now. Needed in order to know which manager messages to load.

        // Then get all the user's (manager) messages.
        currentUsersMessages.setAll(DataModel.getInstance().getUsersMessages(currentUser));

        // Sort the messages by start time (date & time reported).
        sortMessagesByStartTime();
    }

    /**
     * Sort the manager message list by the start time (date & time reported).
     */
    private void sortMessagesByStartTime() {
        currentUsersMessages.sort(Comparator.comparing(Message::getMessageStartTime));
    }

    /**
     * Handle any incoming changes to the Message ObservableList and update the table.
     */
    private void handleMessageUpdate() {
        currentUsersMessages.addListener((ListChangeListener<Message>) c -> {

            // Reload screens & clear messages.
            //clearMessageFields();
            loadAllScreens();

            // Now assign the table.
            //comingMessages.setItems(currentUsersMessages);
        });
    }

    private void initializeTable() {
        comingMessages.setItems(currentUsersMessages);
        msgColumn.setCellValueFactory(u -> new ReadOnlyObjectWrapper<>(u.getValue().getMessage()));
        timeColumn.setCellValueFactory(u -> new ReadOnlyObjectWrapper(u.getValue().getMessageStartTime().toLocalTime()));
        dateColumn.setCellValueFactory(u -> new ReadOnlyObjectWrapper<>(u.getValue().getMessageStartTime().toLocalDate()));
    }

    private void setDurationChoiceBoxes() {
        hourBox.setItems(FXCollections.observableArrayList(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23));
        minuteBox.setItems(FXCollections.observableArrayList(
                0, 30));
        durationHoursChoice.setItems(FXCollections.observableArrayList(0, 1, 2, 3, 4, 5, 6, 7, 8));
        durationHoursChoice.setValue(0);
        hourBox.setValue(LocalTime.now().getHour());
        minuteBox.setValue(0);

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
        currentUser = LoginManager.getCurrentUser();
        if (currentUser.getUserRole() == UserType.Admin) {
            for (ScreenBit s : DataModel.getInstance().getScreenBits()) {
                makeScreen(s);
            }
        } else {
            // Add all screens.
            for (ScreenBit s : currentUser.getAssignedScreenBits()) {
                makeScreen(s);
            }
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
        check.setMouseTransparent(true);
        check.setIcon(FontAwesomeIcon.CHECK_CIRCLE_ALT);
        check.setFill(Paint.valueOf("#97CE68"));
        check.getStyleClass().add("SMButtons");
        check.setLayoutX(77.3);
        check.setLayoutY(20.66);
        check.setSize(String.valueOf(13.33));
        check.setVisible(false);

        MaterialDesignIconView desktop = new MaterialDesignIconView();
        desktop.setMouseTransparent(true);
        desktop.setIcon(MaterialDesignIcon.MONITOR);
        desktop.setFill(Paint.valueOf("#0d262e"));
        desktop.getStyleClass().add("SMButtons");
        desktop.setLayoutX(26);
        desktop.setLayoutY(68);
        desktop.setSize(String.valueOf(48));

        Label label = new Label();
        label.setMouseTransparent(true);
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

        // If no previous message is selected, create new and add it to the database.
        if (selectedMessage == null) {

            // Add the message to database.
            DataModel.getInstance().addMessage(currentUser, newMessage, selectedScreens);
        } else {
            var confirmUpdate = ConfirmationDialog.createConfirmationDialog("Are you sure you want to update the existing bug report?");
            if (confirmUpdate) DataModel.getInstance().updateMessage(selectedMessage, newMessage);
        }

        // Then reload all the updated messages.
        currentUsersMessages.setAll(DataModel.getInstance().getUsersMessages(currentUser));
        sortMessagesByStartTime();
        clearMessageFields();
    }

    private Message getMessage() {
        String message = messageArea.getText();
        Color color = colorPicker.getValue();
        LocalDateTime startTime = LocalDateTime.of(LocalDate.from(datePicker.getValue()), LocalTime.of(hourBox.getSelectionModel().getSelectedIndex(), minuteBox.getSelectionModel().getSelectedItem()));
        LocalDateTime endTime = startTime.plusHours(getDurationHours()).plusMinutes(getDurationMinutes());
        MessageType messageType = currentUser.getUserRole() == UserType.Manager ? MessageType.Manager : MessageType.Admin;

        Message newMessage = new Message(startTime, endTime, message, color, messageType);
        return newMessage;
    }

    /**
     * Load the selected Message's info.
     *
     * @param message The message to load.
     */
    private void loadSelectedMessage(Message message) {
        if (message != null) {
            messageArea.setText(message.getMessage());
            datePicker.setValue(message.getMessageStartTime().toLocalDate());
            hourBox.setValue(message.getMessageStartTime().getHour());
            minuteBox.setValue(message.getMessageStartTime().getMinute());
            colorPicker.setValue(message.getTextColor());
            setMessageDuration(message);

        }
    }

    private void setMessageDuration(Message message) {
        int slots = TimeSlotCalculator.calculateTimeSlots(message);
        durationHoursChoice.setValue((slots - (slots % 2)) / 2);
        durationMinutesChoice.setValue((slots % 2) * 30);
    }

    private long getDurationMinutes() {
        return durationMinutesChoice.getSelectionModel().getSelectedItem();
    }

    private long getDurationHours() {
        return durationHoursChoice.getSelectionModel().getSelectedItem();
    }

    private void clearMessageFields() {
        messageArea.clear();
        messageArea.setPromptText("Enter your message here...");
        colorPicker.setValue(Color.WHITE);
        datePicker.setValue(LocalDate.now());
        hourBox.setValue(LocalTime.now().getHour());
        minuteBox.setValue(0);
        durationHoursChoice.setValue(0);
        durationMinutesChoice.setValue(0);
    }

    @FXML
    private void handleSelectAll() {
        isAllSelected = !isAllSelected;

        for (ScreenBit s : DataModel.getInstance().getScreenBits()) {
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

    public void showSelectedMessage(MouseEvent mouseEvent) {

        if(selectedMessage != null && selectedMessage.getId() == comingMessages.getSelectionModel().getSelectedItem().getId()){
            comingMessages.getSelectionModel().clearSelection();
            selectedMessage = null;
        } else {
            selectedMessage = comingMessages.getSelectionModel().getSelectedItem();
        }

        if (selectedMessage != null) {

            // If the user has written text in message that don't match the selected message, ask for confirmation
            // to override the current data.
            if (!messageArea.getText().equals(selectedMessage.getMessage()) && !messageArea.getText().isEmpty()) {

                var confirmation = ConfirmationDialog.createConfirmationDialog("Do you really want to load the selected message? Your current message will be overridden.");
                if (confirmation) loadSelectedMessage(selectedMessage);
            } else if (messageArea.getText().isEmpty())
                loadSelectedMessage(selectedMessage);
        } else{
            clearMessageFields();
        }
    }

    public void handleDeleteMessage() {
        if (comingMessages.getSelectionModel().getSelectedItem() != null) {
            MessageModel.getInstance().deleteMessage(comingMessages.getSelectionModel().getSelectedItem());
        } else {
            WarningController.createWarning("Please select a message to delete!");
        }
    }
}
