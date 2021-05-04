package GUI.Controller;

import BE.Screen;
import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIcon;
import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIconView;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;


public class AdminScreenManagementController {
    @FXML
    private FlowPane root;

    private double xOffset = 0;
    private double yOffset = 0;

    public void handleNewScreen() {
        Screen s = new Screen("Screen");
        Pane newPane = new Pane();
        newPane.setPrefSize(150, 150);

        Rectangle newRectangle = new Rectangle(150, 150);
        newRectangle.setArcHeight(50);
        newRectangle.setArcWidth(50);
        newRectangle.setFill(Paint.valueOf("#154c5d"));
        newRectangle.getStyleClass().add("SMButtons");

        MaterialDesignIconView settings = new MaterialDesignIconView();
        settings.setIcon(MaterialDesignIcon.SETTINGS);
        settings.setFill(Paint.valueOf("#0d262e"));
        settings.getStyleClass().add("SMButtons");
        settings.setLayoutX(116);
        settings.setLayoutY(31);
        settings.setSize(String.valueOf(20));

        MaterialDesignIconView screen = new MaterialDesignIconView();
        screen.setIcon(MaterialDesignIcon.MONITOR);
        screen.setFill(Paint.valueOf("#0d262e"));
        screen.setStyle(".SMButtons");
        screen.setLayoutX(39);
        screen.setLayoutY(102);
        screen.setSize(String.valueOf(72));

        Label label = new Label();
        label.setText("Screen");
        label.setTextFill(Paint.valueOf("#FFFFFF"));
        label.setFont(new Font("System", 16));
        label.setStyle("-fx-font-weight: bold; -fx-font-style: italic");
        label.setLayoutX(44);
        label.setLayoutY(111);
        label.setAlignment(Pos.TOP_CENTER);
        label.setTextAlignment(TextAlignment.CENTER);

        newPane.getChildren().addAll(newRectangle, settings, screen, label);

        root.getChildren().add(0, newPane);

        FlowPane.setMargin(newPane, new Insets(25, 25, 0, 25));

        screen.setOnMouseClicked(mouseEvent -> {
            try {
                handleScreenCreator(s);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        newRectangle.setOnMouseClicked(mouseEvent -> {
            try {
                handleScreenCreator(s);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        settings.setOnMouseClicked(mouseEvent -> {
            handleEditScreen();
        });
    }

    private void handleScreenCreator(Screen s) throws IOException {
        Stage pickerDashboard = new Stage();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/GUI/VIEW/PickerDashboard.fxml"));

        Parent root = (Parent) loader.load();
        PickerDashboardController pickerDashboardController = loader.getController();

        Scene pickerScene = new Scene(root);

        pickerScene.setOnMousePressed(event -> {
            xOffset = event.getSceneX();
            yOffset = event.getSceneY();
        });

        pickerScene.setOnMouseDragged(event -> {
            pickerDashboard.setX(event.getScreenX() - xOffset);
            pickerDashboard.setY(event.getScreenY() - yOffset);
            pickerDashboard.setOpacity(0.8f);
        });

        pickerScene.setOnMouseDragExited((event) -> {
            pickerDashboard.setOpacity(1.0f);
        });

        pickerScene.setOnMouseReleased((event) -> {
            pickerDashboard.setOpacity(1.0f);
        });

        pickerDashboard.initStyle(StageStyle.UNDECORATED);
        pickerDashboard.setScene(pickerScene);
        pickerDashboard.show();
    }

    public void handleEditScreen() {
        //TODO lav fxml til edit screen.
        System.out.println("test");
    }
}
