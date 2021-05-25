package GUI.Controller.HRControllers;

import BE.Department;
import BE.SceneMover;
import BE.Searcher;
import GUI.Controller.PopupControllers.WarningController;
import GUI.Model.DepartmentModel;
import com.jfoenix.controls.JFXTextField;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.collections.ListChangeListener;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.TextAlignment;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class HRDepartmentController implements Initializable {
    @FXML
    private FlowPane flowpane;
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private JFXTextField txtSearch;

    private ArrayList<Department> selectedDepartments = new ArrayList<>();
    private SceneMover sceneMover = new SceneMover();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
        loadAllDepartments();
        autofitSize();
        handleDepartmentUpdate();
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
    private void loadAllDepartments() {
        // Remove all nodes.
        flowpane.getChildren().clear();

        // Add all screens.
        for (Department d : DepartmentModel.getInstance().getAllDepartments()) {
            handleNewDepartment(d);
        }
    }

    private void handleNewDepartment(Department d) {
        Pane newPane = new Pane();
        newPane.setPrefSize(150, 150);

        newPane.setAccessibleText(d.getName());
        Rectangle newRectangle = new Rectangle(150, 150);
        newRectangle.setArcHeight(50);
        newRectangle.setArcWidth(50);
        newRectangle.setFill(Paint.valueOf("#154c5d"));
        newRectangle.getStyleClass().add("SMButtons");

        FontAwesomeIconView check = new FontAwesomeIconView();
        check.setMouseTransparent(true);
        check.setIcon(FontAwesomeIcon.CHECK_CIRCLE_ALT);
        check.setFill(Paint.valueOf("#97CE68"));
        check.getStyleClass().add("SMButtons");
        check.setLayoutX(116);
        check.setLayoutY(31);
        check.setSize(String.valueOf(20));
        check.setVisible(false);

        FontAwesomeIconView remover = new FontAwesomeIconView();
        remover.setIcon(FontAwesomeIcon.REMOVE);
        remover.setFill(Paint.valueOf("#0d262e"));
        remover.getStyleClass().add("SMButtons");
        remover.setLayoutX(20);
        remover.setLayoutY(31);
        remover.setSize(String.valueOf(22));

        FontAwesomeIconView office = new FontAwesomeIconView();
        office.setMouseTransparent(true);
        office.setIcon(FontAwesomeIcon.BUILDING_ALT);
        office.setFill(Paint.valueOf("#0d262e"));
        office.getStyleClass().add("SMButtons");
        office.setLayoutX(47);
        office.setLayoutY(102);
        office.setSize(String.valueOf(72));
        Label label = new Label();
        label.setMouseTransparent(true);
        label.setText(d.getName());
        label.setTextFill(Paint.valueOf("#FFFFFF"));
        label.setStyle("-fx-font-size: 16px;-fx-font-weight: bold; -fx-font-style: italic");
        label.setPrefSize(133, 25);
        label.setLayoutX(9);
        label.setLayoutY(111);
        label.setAlignment(Pos.TOP_CENTER);
        label.setContentDisplay(ContentDisplay.CENTER);
        label.setTextAlignment(TextAlignment.CENTER);

        newPane.getChildren().addAll(newRectangle, check, remover, office, label);

        flowpane.getChildren().add(0, newPane);

        FlowPane.setMargin(newPane, new Insets(25, 25, 0, 25));

        office.setOnMouseClicked(mouseEvent -> {
            if (!selectedDepartments.contains(d)) {
                selectedDepartments.add(d);
            } else {
                selectedDepartments.remove(d);
            }
            check.setVisible(!check.isVisible());
        });

        newRectangle.setOnMouseClicked(mouseEvent -> {
            if (!selectedDepartments.contains(d)) {
                selectedDepartments.add(d);
            } else {
                selectedDepartments.remove(d);
            }
            check.setVisible(!check.isVisible());
            System.out.println(selectedDepartments);
        });

        check.setOnMouseClicked(mouseEvent -> {
            if (!selectedDepartments.contains(d)) {
                selectedDepartments.add(d);
            } else {
                selectedDepartments.remove(d);
            }
            check.setVisible(!check.isVisible());
        });

        remover.setOnMouseClicked(mouseEvent -> {
            handleRemove(d);
        });
    }

    private void handleRemove(Department d) {
        DepartmentModel.getInstance().deleteDepartment(d);
    }

    public void handleSearchUser(){
        if (!txtSearch.getText().isEmpty() || !txtSearch.getText().isBlank()) {
            flowpane.getChildren().clear();
            for (Department d : Searcher.searchDepartments(DepartmentModel.getInstance().getAllDepartments(), txtSearch.getText())){
                handleNewDepartment(d);
            }
        } else{
            loadAllDepartments();
        }
    }

    /**
     * Handle any incoming changes to the User ObservableList and update the table.
     */
    private void handleDepartmentUpdate() {
        DepartmentModel.getInstance().getAllDepartments().addListener((ListChangeListener<Department>) c -> {
            //System.out.println("Called");
            loadAllDepartments();
        });
    }
    /**
     * Displays the add Employee screen.
     *
     * @throws IOException if it cannot find the FXML file.
     */
    public void handleImportUsers() throws IOException {
        System.out.println("IMPORT");
        loadAllDepartments();
    }


    public void handleExportUsers() throws IOException {
        if (!selectedDepartments.isEmpty()) {
            System.out.println("EXPORT");
            loadAllDepartments();
        } else {
            WarningController.createWarning("No departments selected! Please select the departments you want to export");
        }
    }


    public void handleExportPhonelist(){
        if (!selectedDepartments.isEmpty()) {
            System.out.println("EXPORT PHONE");
            loadAllDepartments();
        } else {
            WarningController.createWarning("No departments selected! Please select the departments you want to export");
        }
    }
}
