package GUI.Controller.HRControllers;

import BE.SceneMover;
import BE.Searcher;
import BE.User;
import GUI.Controller.CrudControllers.AddEmployeeController;
import GUI.Controller.CrudControllers.EditEmployeeController;
import GUI.Controller.CrudControllers.RemoveEmployeeController;
import GUI.Controller.EmployeeCardController;
import GUI.Controller.PopupControllers.WarningController;
import GUI.Model.UserModel;
import com.jfoenix.controls.JFXTextField;
import com.mysql.cj.xdevapi.Warning;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIcon;
import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIconView;
import javafx.collections.ListChangeListener;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class HRUserManagementController implements Initializable {
    @FXML
    private FlowPane flowpane;
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private JFXTextField txtSearch;

    private ArrayList<User> selectedUsers = new ArrayList<>();
    private UserModel userModel = new UserModel();
    private SceneMover sceneMover = new SceneMover();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
        loadAllUsers();
        autofitSize();
        handleUserUpdate();
    }

    private void autofitSize() {
        flowpane.setPrefWrapLength(scrollPane.getWidth());
        scrollPane.widthProperty().addListener((observable, t, t1) -> {
            flowpane.setPrefWidth(t1.doubleValue());
            flowpane.setMaxWidth(t1.doubleValue());
            flowpane.setMinWidth(t1.doubleValue());
            flowpane.setPrefWrapLength(t1.doubleValue());
        });
        scrollPane.heightProperty().addListener(((observableValue, number, t1) -> {
            flowpane.setMaxHeight(t1.doubleValue());
            flowpane.setMinHeight(t1.doubleValue());
            flowpane.setPrefHeight(t1.doubleValue());
        }));
    }

    /**
     * Load all the available screens.
     */
    private void loadAllUsers() {
        // Remove all nodes.
        flowpane.getChildren().clear();

        // Add all screens.
        for (User u : UserModel.getInstance().getAllUsers()) {
            handleNewUser(u);
        }
    }

    private void handleNewUser(User u) {
        Pane newPane = new Pane();
        newPane.setPrefSize(150, 250);

        newPane.setAccessibleText(u.getFirstName());
        Rectangle newRectangle = new Rectangle(150, 250);
        newRectangle.setArcHeight(50);
        newRectangle.setArcWidth(50);
        newRectangle.setFill(Paint.valueOf("#154c5d"));
        newRectangle.getStyleClass().add("SMButtons");

        FontAwesomeIconView check = new FontAwesomeIconView();
        check.setMouseTransparent(true);
        check.setId("Check");
        check.setIcon(FontAwesomeIcon.CHECK_CIRCLE_ALT);
        check.setFill(Paint.valueOf("#97CE68"));
        check.getStyleClass().add("SMButtons");
        check.setLayoutX(128);
        check.setLayoutY(26);
        check.setSize(String.valueOf(16));
        check.setVisible(false);

        Circle newCircle = new Circle();
        newCircle.setMouseTransparent(true);
        newCircle.setRadius(50);
        newCircle.setLayoutX(75);
        newCircle.setLayoutY(64);

        ImageView image = new ImageView(new Image("/Gui/Resources/defaultPerson.png"));
        image.setMouseTransparent(true);
        image.setFitWidth(105);
        image.setFitHeight(105);
        image.setLayoutX(23);
        image.setLayoutY(12);

        Rectangle underBar = new Rectangle();
        underBar.setMouseTransparent(true);
        underBar.setArcWidth(5);
        underBar.setArcHeight(5);
        underBar.setWidth(120);
        underBar.setHeight(4);
        underBar.setLayoutX(15);
        underBar.setLayoutY(123);
        underBar.setFill(Paint.valueOf("#add8e5"));

        Label name = new Label();
        name.setMouseTransparent(true);
        name.getStyleClass().add("SMButtons");
        name.setText(u.getFirstName() + " " + u.getLastName());
        name.setTextFill(Paint.valueOf("#FFFFFF"));
        name.setStyle("-fx-font-size: 12px;-fx-font-weight: bold; -fx-font-style: italic");
        name.setPrefSize(139, 13);
        name.setLayoutX(6);
        name.setLayoutY(138);
        name.setAlignment(Pos.TOP_CENTER);
        name.setContentDisplay(ContentDisplay.CENTER);
        name.setTextAlignment(TextAlignment.CENTER);

        Label title = new Label();
        title.setMouseTransparent(true);
        title.getStyleClass().add("SMButtons");
        title.setText("Title");
        title.setTextFill(Paint.valueOf("#FFFFFF"));
        title.setStyle("-fx-font-size: 12px;-fx-font-weight: bold; -fx-font-style: italic");
        title.setPrefSize(139, 13);
        title.setLayoutX(6);
        title.setLayoutY(158);
        title.setAlignment(Pos.TOP_CENTER);
        title.setContentDisplay(ContentDisplay.CENTER);
        title.setTextAlignment(TextAlignment.CENTER);

        Label department = new Label();
        department.setMouseTransparent(true);
        department.getStyleClass().add("SMButtons");
        department.setText("Department");
        department.setTextFill(Paint.valueOf("#FFFFFF"));
        department.setStyle("-fx-font-size: 12px;-fx-font-weight: bold; -fx-font-style: italic");
        department.setPrefSize(139, 13);
        department.setLayoutX(6);
        department.setLayoutY(178);
        department.setAlignment(Pos.TOP_CENTER);
        department.setContentDisplay(ContentDisplay.CENTER);
        department.setTextAlignment(TextAlignment.CENTER);

        Rectangle viewMoreButton = new Rectangle();
        viewMoreButton.getStyleClass().add("employeeButton");
        viewMoreButton.setArcHeight(15);
        viewMoreButton.setArcWidth(15);
        viewMoreButton.setWidth(120);
        viewMoreButton.setHeight(23);
        viewMoreButton.setLayoutY(215);
        viewMoreButton.setLayoutX(15);

        Label viewMoreLabel = new Label();
        viewMoreLabel.setMouseTransparent(true);
        viewMoreLabel.setText("View More");
        viewMoreLabel.setTextFill(Paint.valueOf("#154c5d"));
        viewMoreLabel.setStyle("-fx-font-size: 12px;-fx-font-weight: bold; -fx-font-style: italic");
        viewMoreLabel.setPrefSize(60, 17);
        viewMoreLabel.setLayoutX(47);
        viewMoreLabel.setLayoutY(218);
        viewMoreLabel.setAlignment(Pos.TOP_CENTER);
        viewMoreLabel.setContentDisplay(ContentDisplay.CENTER);
        viewMoreLabel.setTextAlignment(TextAlignment.CENTER);

        newPane.getChildren().addAll(newRectangle, check, newCircle, image, underBar, name, title, department, viewMoreButton, viewMoreLabel);

        flowpane.getChildren().add(0, newPane);

        flowpane.setMargin(newPane, new Insets(25, 25, 0, 25));

        check.setOnMouseClicked(mouseEvent -> {
            if (!selectedUsers.contains(u)) {
                selectedUsers.add(u);
            } else {
                selectedUsers.remove(u);
            }
            check.setVisible(!check.isVisible());
        });

        newRectangle.setOnMouseClicked(mouseEvent -> {
            if (!selectedUsers.contains(u)) {
                selectedUsers.add(u);
            } else {
                selectedUsers.remove(u);
            }
            check.setVisible(!check.isVisible());
        });

        newCircle.setOnMouseClicked(mouseEvent -> {
            if (!selectedUsers.contains(u)) {
                selectedUsers.add(u);
            } else {
                selectedUsers.remove(u);
            }
            check.setVisible(!check.isVisible());
        });

        image.setOnMouseClicked(mouseEvent -> {
            if (!selectedUsers.contains(u)) {
                selectedUsers.add(u);
            } else {
                selectedUsers.remove(u);
            }
            check.setVisible(!check.isVisible());
        });

        underBar.setOnMouseClicked(mouseEvent -> {
            if (!selectedUsers.contains(u)) {
                selectedUsers.add(u);
            } else {
                selectedUsers.remove(u);
            }
            check.setVisible(!check.isVisible());
        });

        name.setOnMouseClicked(mouseEvent -> {
            if (!selectedUsers.contains(u)) {
                selectedUsers.add(u);
            } else {
                selectedUsers.remove(u);
            }
            check.setVisible(!check.isVisible());
        });

        title.setOnMouseClicked(mouseEvent -> {
            if (!selectedUsers.contains(u)) {
                selectedUsers.add(u);
            } else {
                selectedUsers.remove(u);
            }
            check.setVisible(!check.isVisible());
        });

        department.setOnMouseClicked(mouseEvent -> {
            if (!selectedUsers.contains(u)) {
                selectedUsers.add(u);
            } else {
                selectedUsers.remove(u);
            }
            check.setVisible(!check.isVisible());
        });

        viewMoreButton.setOnMouseClicked(e -> {
            EmployeeCardController.OpenEmployeeCard(u);
        });

        viewMoreLabel.setOnMouseClicked(e -> {
            EmployeeCardController.OpenEmployeeCard(u);
        });
    }

    public void handleSearchUser(){
        if (!txtSearch.getText().isEmpty() || !txtSearch.getText().isBlank()) {
            flowpane.getChildren().clear();
            for (User u : Searcher.searchUsers(UserModel.getInstance().getAllUsers(), txtSearch.getText())){
                handleNewUser(u);
            }
        } else{
            loadAllUsers();
        }
    }

    /**
     * Handle any incoming changes to the User ObservableList and update the table.
     */
    private void handleUserUpdate() {
        UserModel.getInstance().getAllUsers().addListener((ListChangeListener<User>) c -> {
            //System.out.println("Called");
            loadAllUsers();
        });
    }
    /**
     * Displays the add Employee screen.
     *
     * @throws IOException if it cannot find the FXML file.
     */
    public void handleImportUsers() throws IOException {
        System.out.println("IMPORT");
        loadAllUsers();
    }


    public void handleExportUsers() throws IOException {
        if (!selectedUsers.isEmpty()) {
            System.out.println("EXPORT");
            loadAllUsers();
        } else {
            WarningController.createWarning("No users selected! Please select the users you want to export");
        }
    }


    public void handleExportPhonelist() throws IOException {
        if (!selectedUsers.isEmpty()) {
            System.out.println("EXPORT PHONE");
            loadAllUsers();
        } else {
            WarningController.createWarning("No users selected! Please select the users you want to export");
        }
    }
}
