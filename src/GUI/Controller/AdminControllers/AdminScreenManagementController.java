package GUI.Controller.AdminControllers;

import BE.SceneMover;
import BE.ScreenBit;
import GUI.Controller.PopupControllers.ConfirmationDialog;
import GUI.Model.ScreenModel;
import GUI.Model.UserModel;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIcon;
import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIconView;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;


public class AdminScreenManagementController implements Initializable {
    @FXML
    private FlowPane root;

    private final SceneMover sceneMover = new SceneMover();
    private final ScreenModel screenModel = ScreenModel.getInstance();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadAllScreens();
    }

    /**
     * Load all the available screens.
     */
    private void loadAllScreens() {
        for (ScreenBit s : screenModel.getAllScreenBits()) {
            handleNewScreen(s);
        }
    }

    private void handleNewScreen(ScreenBit screenBit) {
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

        FontAwesomeIconView remover = new FontAwesomeIconView();
        remover.setIcon(FontAwesomeIcon.REMOVE);
        remover.setFill(Paint.valueOf("#0d262e"));
        remover.getStyleClass().add("SMButtons");
        remover.setLayoutX(20);
        remover.setLayoutY(31);
        remover.setSize(String.valueOf(22));

        MaterialDesignIconView desktop = new MaterialDesignIconView();
        desktop.setIcon(MaterialDesignIcon.MONITOR);
        desktop.setFill(Paint.valueOf("#0d262e"));
        desktop.getStyleClass().add("SMButtons");
        desktop.setLayoutX(39);
        desktop.setLayoutY(102);
        desktop.setSize(String.valueOf(72));

        Label label = new Label();
        label.setText(screenBit.getName());
        label.setTextFill(Paint.valueOf("#FFFFFF"));
        label.setFont(new Font("System", 16));
        label.setStyle("-fx-font-weight: bold; -fx-font-style: italic");
        label.setPrefSize(133, 25);
        label.setLayoutX(9);
        label.setLayoutY(111);
        label.setAlignment(Pos.TOP_CENTER);
        label.setContentDisplay(ContentDisplay.CENTER);
        label.setTextAlignment(TextAlignment.CENTER);

        newPane.getChildren().addAll(newRectangle, settings, remover, desktop, label);

        root.getChildren().add(0, newPane);

        FlowPane.setMargin(newPane, new Insets(25, 25, 0, 25));

        desktop.setOnMouseClicked(mouseEvent -> {
            try {
                handleScreenCreator(screenBit);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        newRectangle.setOnMouseClicked(mouseEvent -> {
            try {
                handleScreenCreator(screenBit);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        settings.setOnMouseClicked(mouseEvent -> {
            try {
                handleEditScreen(screenBit);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        remover.setOnMouseClicked(mouseEvent -> {
            try {
                handleRemove(screenBit);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    private void handleScreenCreator(ScreenBit screenBit) throws Exception {
        Stage pickerDashboard = new Stage();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/GUI/VIEW/AdminViews/PickerDashboard.fxml"));

        AnchorPane root = loader.load();
        PickerDashboardController pickerDashboardController = loader.getController();

        pickerDashboardController.setTitle(screenBit.getName());
        pickerDashboardController.init(screenBit);
        Scene pickerScene = new Scene(root);

        BorderPane borderPane = (BorderPane) root.getChildren().get(0);
        Node bar = borderPane.getTop();
        sceneMover.move(pickerDashboard, bar);

        pickerDashboard.initStyle(StageStyle.UNDECORATED);
        pickerDashboard.setScene(pickerScene);
        pickerDashboard.show();
    }


    public void handleCreateScreen() throws IOException {
        NewScreenDialog screenDialog = new NewScreenDialog("Test");

        Optional<String> result = screenDialog.showAndWait();

        if (result.isPresent()) {

            // Add the new screen to database.
            screenModel.addScreenBit(new ScreenBit(result.get(), ""));

            handleNewScreen(new ScreenBit(result.get()));
        }
    }

    public void handleEditScreen(ScreenBit screenBit) throws IOException {
        Stage editScreenStage = new Stage();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/GUI/VIEW/AdminViews/EditScreen.fxml"));

        Parent root = (Parent) loader.load();
        EditScreenController editScreenController = loader.getController();
        editScreenController.setScreen(screenBit);
        editScreenController.setData(UserModel.getInstance().getAllUsers());

        Node bar = root.getChildrenUnmodifiable().get(0);
        Scene editScreenScene = new Scene(root);
        sceneMover.move(editScreenStage,bar);
        //applyScreenDrag(editScreenStage, editScreenScene);

        editScreenStage.initStyle(StageStyle.UNDECORATED);
        editScreenStage.setScene(editScreenScene);
        editScreenStage.show();
    }

    private void handleRemove(ScreenBit screenBit) throws IOException {
        String text = "Are you sure you want to delete " + screenBit.getName() + " screen? " +
                "This action is irreversibel";
        ConfirmationDialog confirmationDialog = new ConfirmationDialog(text);

        Optional<Boolean> result = confirmationDialog.showAndWait();
        if (result.isPresent()) {
            if (result.get()) {
                screenModel.deleteScreenBit(screenBit);
            }
        }
    }
}
