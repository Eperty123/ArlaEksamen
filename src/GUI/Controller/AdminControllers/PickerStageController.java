package GUI.Controller.AdminControllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Orientation;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.ResourceBundle;

public class PickerStageController implements Initializable {
    @FXML
    private GridPane picker;
    @FXML
    private BorderPane root;
    private SplitPane splitPane = new SplitPane();
    private List<PickerStageController> controllers = new ArrayList<>();
    private ContextMenu contextMenu = new ContextMenu();
    private MenuItem unsplitItem = new MenuItem("unsplit this");
    private MenuItem flipItem = new MenuItem("flip this");
    private PickerStageController parentPickerStageController;

    public PickerStageController() {
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        init();
    }

    private void init() {
        contextMenu.getItems().add(unsplitItem);
        contextMenu.getItems().add(flipItem);
        splitPane.setOnMouseClicked((MouseEvent mouseEvent) -> {
            if (mouseEvent.getButton() == MouseButton.SECONDARY) {
                if (contextMenu.isShowing())
                    contextMenu.hide();
                contextMenu.show(splitPane, mouseEvent.getScreenX(), mouseEvent.getScreenY());
            } else
                contextMenu.hide();
        });
        unsplitItem.onActionProperty().set((v) -> unSplit());
        flipItem.onActionProperty().set((b) -> flipSplitPane());
    }

    public PickerStageController getParentPickerStageController() {
        return parentPickerStageController;
    }

    public void setParentPickerStageController(PickerStageController pickerStageController) {
        this.parentPickerStageController = pickerStageController;
    }

    @FXML
    private void splitSceneHorizontally(MouseEvent mouseEvent) {
        if (mouseEvent.getButton() == MouseButton.PRIMARY)
            this.split(Orientation.HORIZONTAL);
    }

    @FXML
    private void splitSceneVertically(MouseEvent mouseEvent) {
        if (mouseEvent.getButton() == MouseButton.PRIMARY)
            this.split(Orientation.VERTICAL);
    }

    public void split(Orientation orientation) {
        try {
            splitPane.setOrientation(orientation);
            for (int i = 0; i < 2; i++) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUI/View/AdminViews/PickerStage.fxml"));
                splitPane.getItems().add(loader.load());
                PickerStageController p = loader.getController();
                controllers.add(p);
            }
            this.setContent(splitPane);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void changeContent(MouseEvent mouseEvent) {
        if (mouseEvent.getButton() == MouseButton.PRIMARY) {
            try {
                Stage stage = new Stage();
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUI/View/AdminViews/DataManagement.fxml"));
                AnchorPane pane = loader.load();
                DataManagementController dataManagementController = loader.getController();
                dataManagementController.setPickerStageController(this);
                dataManagementController.setStage(stage);
                Scene scene = new Scene(pane);

                stage.setX(mouseEvent.getScreenX());
                stage.setY(mouseEvent.getScreenY());
                stage.setScene(scene);
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public Node getContent(){
        return getRoot().getCenter();
    }

    public void setContent(Node node, String accessibleText) {
        getRoot().setCenter(node);
        getContent().setAccessibleText(accessibleText);
    }

    public void setContent(Node node) {
        getRoot().setCenter(node);
    }

    public List<PickerStageController> getControllers() {
        return controllers;
    }

    public SplitPane getSplitPane() {
        return splitPane;
    }

    public BorderPane getRoot() {
        return root;
    }

    @FXML
    private void unSplit() {
        splitPane.getItems().clear();
        controllers.clear();
        setContent(picker,null);
    }

    @FXML
    public void flipSplitPane() {
        if (splitPane.getItems().size() >= 2) {
            Node node = splitPane.getItems().get(0);
            splitPane.getItems().remove(node);
            splitPane.getItems().add(node);
        }
    }


    /**
     * This is especially made such that it prints the builderString for this and the splitPanes that it carries,
     * and the panes that the splitPanes carry, if any
     *
     * @return returns the builderString for this instance of the PickerStage
     */
    private String getBuilderString(PickerStageController pickerStageController) {
        List<PickerStageController> controllersOfInterest = pickerStageController.getControllers();
        if (controllersOfInterest.isEmpty())
            return getContent().getAccessibleText()==null?"":getContent().getAccessibleText();
        else {
            StringBuilder stringBuilder = new StringBuilder("");
            if (!splitPane.getDividers().isEmpty()) {
                stringBuilder.append(String.format("%n%s%.02f", splitPane.getOrientation().toString().charAt(0), splitPane.getDividers().get(0).getPosition()));
                stringBuilder.append("{");
                if (!controllersOfInterest.isEmpty()) {
                    if (!this.getSplitPane().getOrientation().equals(Orientation.HORIZONTAL)) {
                        controllersOfInterest.sort(Comparator.comparingDouble(con -> con.getRoot().getLayoutX()));
                    } else
                        controllersOfInterest.sort(Comparator.comparingDouble(con -> con.getRoot().getLayoutY()));
                    controllersOfInterest.forEach(c -> {
                        stringBuilder.append(c.getBuilderString()).append(controllersOfInterest.indexOf(c) < controllersOfInterest.size() - 1 ? "|" : "");
                    });
                }
                stringBuilder.append("}");
            }
            return stringBuilder.toString().replaceAll(",", ".");
        }
    }


    /**
     * This is especially made such that it prints the builderString for this and the splitPanes that it carries,
     * and the panes that the splitPanes carry, if any
     *
     * @return returns the builderString for this instance of the PickerStage
     */
    public String getBuilderString() {
        return getBuilderString(this);
    }

    /**
     * This is especially made such that it prints the builderString for this and the splitPanes that it carries,
     * and the panes that the splitPanes carry, if any
     *
     * @return returns the builderString for this instance of the PickerStage
     */
    public String getParentBuilderString() {
        return getBuilderString(this.getParentPickerStageController());
    }
}
