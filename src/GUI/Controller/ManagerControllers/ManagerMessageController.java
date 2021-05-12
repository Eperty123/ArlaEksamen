package GUI.Controller.ManagerControllers;

import BE.ScreenBit;
import BE.User;
import BLL.LoginManager;
import GUI.Model.ScreenModel;
import com.jfoenix.controls.JFXColorPicker;
import com.jfoenix.controls.JFXTimePicker;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIcon;
import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIconView;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.TextAlignment;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ManagerMessageController implements Initializable {
    @FXML
    private JFXTimePicker timePicker;
    @FXML
    private JFXColorPicker colorPicker;
    @FXML
    private FlowPane screenContainer;

    private User currentUser;

    private List<ScreenBit> selectedScreens = new ArrayList<>();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        currentUser = LoginManager.getCurrentUser();
        timePicker.set24HourView(true);

        loadAllScreens();
    }

    private void loadAllScreens() {
        // Remove all nodes.
        screenContainer.getChildren().clear();

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

    }

    public void handleCancel() {

    }
}
