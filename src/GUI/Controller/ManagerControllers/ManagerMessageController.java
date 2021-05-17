package GUI.Controller.ManagerControllers;

import BE.*;
import BLL.LoginManager;
import GUI.Controller.PopupControllers.ConfirmationDialog;
import GUI.Model.MessageModel;
import com.jfoenix.controls.JFXColorPicker;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTimePicker;
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


    private User currentUser;

    private List<ScreenBit> selectedScreens = new ArrayList<>();
    private MessageModel messageModel = MessageModel.getInstance();
    private ObservableList<Message> currentUsersMessages = FXCollections.observableArrayList();
    private Message selectedMessage;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        timePicker.set24HourView(true);

        initializeTable();
        handleMessageUpdate();
        setDurationChoiceBoxes();
        loadAllScreens();

        // Set the Message Model's current user to be the one now. Needed in order to know which manager messages to load.
        messageModel.loadUserMessages(currentUser);

        // Then get all the user's (manager) messages.
        currentUsersMessages.setAll(messageModel.getAllUserMessages());

        // Sort the messages by start time (date & time reported).
        sortMessagesByStartTime();

        System.out.println(String.format("Manager message count: %d", currentUsersMessages.size()));
    }

    /**
     * Sort the manager message list by the start time (date & time reported).
     */
    private void sortMessagesByStartTime() {
        currentUsersMessages.sort(Comparator.comparing(Message::getMessageStartTime));
    }

    /**
     * Handle any incoming changes to the User ObservableList and update the table.
     */
    private void handleMessageUpdate() {
        currentUsersMessages.addListener((ListChangeListener<Message>) c -> {

            // Reload screens & clear messages.
            clearMessageFields();
            loadAllScreens();

            // Now assign the table.
            comingMessages.setItems(currentUsersMessages);
        });
    }

    private void initializeTable() {
        comingMessages.setItems(currentUsersMessages);
        msgColumn.setCellValueFactory(u -> new ReadOnlyObjectWrapper<>(u.getValue().getMessage()));
        timeColumn.setCellValueFactory(u -> new ReadOnlyObjectWrapper(u.getValue().getMessageStartTime().toLocalTime()));
        dateColumn.setCellValueFactory(u -> new ReadOnlyObjectWrapper<>(u.getValue().getMessageStartTime().toLocalDate()));
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
        currentUser = LoginManager.getCurrentUser();
        System.out.println(currentUser.getAssignedScreen());
        // Add all screens.
        for (ScreenBit s : currentUser.getAssignedScreen()) {
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

        // If no previous message is selected, create new and add it to the database.
        if (selectedMessage == null) {

            // Add the message to database.
            messageModel.addMessage(currentUser, newMessage, selectedScreens);

            // Also add the message to the ObservableList to simulate an update.
            currentUsersMessages.add(newMessage);
        } else {
            messageModel.updateMessage(selectedMessage, newMessage);
        }

        sortMessagesByStartTime();
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

    /**
     * Load the selected Message's info.
     *
     * @param message The message to load.
     */
    private void loadSelectedMessage(Message message) {
        if (message != null) {
            messageArea.setText(message.getMessage());
            datePicker.setValue(message.getMessageStartTime().toLocalDate());
            timePicker.setValue(message.getMessageStartTime().toLocalTime());
            colorPicker.setValue(message.getTextColor());
        }
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
        colorPicker.setValue(Color.RED);
        datePicker.setValue(null);
        timePicker.setValue(null);
        durationHoursChoice.setValue(0);
        durationMinutesChoice.setValue(0);
    }

    public void showSelectedMessage(MouseEvent mouseEvent) {

        selectedMessage = comingMessages.getSelectionModel().getSelectedItem();
        if (selectedMessage != null) {
            // If the user has written text in message that don't match the selected message, ask for confirmation
            // to override the current data.
            if (!messageArea.getText().equals(selectedMessage.getMessage()) && !messageArea.getText().isEmpty()) {

                var confirmation = ConfirmationDialog.createConfirmationDialog("Do you really want to load the selected message? Your current message will be overridden.");
                if (confirmation) loadSelectedMessage(selectedMessage);
            } else if (messageArea.getText().isEmpty())
                loadSelectedMessage(selectedMessage);
        }
    }
}
