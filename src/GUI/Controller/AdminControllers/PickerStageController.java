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

    /**
     * Initializes contextMenus
     * adds onAction on menuItems
     */
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

    private void lockPanes(PickerStageController pickerStageController){
        if(!pickerStageController.getControllers().isEmpty()){
        pickerStageController.getSplitPane().setDisable(true);
        }else{
        pickerStageController.getControllers().forEach(p->{lockPanes(p);
        p.getSplitPane().setDisable(true);
        });
        }
    }

    public void lockPanes(){
    lockPanes(parentPickerStageController);
    }

    public PickerStageController getParentPickerStageController() {
        return parentPickerStageController;
    }

    public void setParentPickerStageController(PickerStageController pickerStageController) {
        this.parentPickerStageController = pickerStageController;
    }

    /**
     * Splits the stage if the mouse button is left click
     *
     * @param mouseEvent
     */
    @FXML
    private void splitSceneHorizontally(MouseEvent mouseEvent) {
        if (mouseEvent.getButton() == MouseButton.PRIMARY)
            this.split(Orientation.HORIZONTAL);
    }

    /**
     * Splits the stage if the mouse button is left click
     *
     * @param mouseEvent
     */
    @FXML
    private void splitSceneVertically(MouseEvent mouseEvent) {
        if (mouseEvent.getButton() == MouseButton.PRIMARY)
            this.split(Orientation.VERTICAL);
    }

    /**
     * Splits the stage in the given orientation
     *
     * @param orientation the orientation
     */
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

    /**
     * Initializes a DataManagement instance and changes the content of the window if confirmed
     *
     * @param mouseEvent
     */
    @FXML
    private void changeContent(MouseEvent mouseEvent) {
        if (mouseEvent.getButton() == MouseButton.PRIMARY) {
            try {
                Stage stage = new Stage();
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUI/View/AdminViews/DataManagement.fxml"));
                AnchorPane pane = loader.load();
                stage.setTitle("Add content");
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

    /**
     * Gets the content of the PickerStageController
     *
     * @return
     */
    public Node getContent() {
        return getRoot().getCenter();
    }

    /**
     * Sets the content of the PickerStageController
     *
     * @param node           the node you want to set it to
     * @param accessibleText the builderString for the given node (ViewType="URL")
     */
    public void setContent(Node node, String accessibleText) {
        getRoot().setCenter(node);
        getContent().setAccessibleText(accessibleText);
    }

    /**
     * Sets the content without having to set the Accessible Text
     *
     * @param node The node you want it to show
     */
    public void setContent(Node node) {
        getRoot().setCenter(node);
    }

    /**
     * Gets the controllers int the PickerStageController
     *
     * @return The PickerStageControllers
     */
    public List<PickerStageController> getControllers() {
        return controllers;
    }

    /**
     * Gets the PickerStageController's splitPane
     *
     * @return A splitPane
     */
    public SplitPane getSplitPane() {
        return splitPane;
    }

    /**
     * Gets the root element of the PickerStageController
     *
     * @return A BorderPane
     */
    public BorderPane getRoot() {
        return root;
    }

    /**
     * Clears the borderPane and Controllers and resets the controller to the PickerStage
     */
    @FXML
    private void unSplit() {
        splitPane.getItems().clear();
        controllers.clear();
        setContent(picker, null);
    }

    /**
     * Flips the items in the splitPane
     */
    @FXML
    public void flipSplitPane() {
        if (splitPane.getItems().size() >= 2) {
            Node node = splitPane.getItems().get(0);
            splitPane.getItems().remove(node);
            splitPane.getItems().add(node);
        }
    }


    /**
     * This is especially made such that it returns the builderString for this and the splitPanes that it carries,
     * and the panes that the splitPanes carry, if any
     *
     * @return returns the builderString for this instance of the PickerStage
     */
    private String getBuilderString(PickerStageController pickerStageController) {
        List<PickerStageController> controllersOfInterest = pickerStageController.getControllers();
        if (controllersOfInterest.isEmpty())
            return getContent().getAccessibleText() == null ? "" : getContent().getAccessibleText();
        else {
            StringBuilder stringBuilder = new StringBuilder("");
            if (!splitPane.getDividers().isEmpty()) {
                stringBuilder.append(String.format("%s%.02f%n", splitPane.getOrientation().toString().charAt(0), splitPane.getDividers().get(0).getPosition()));
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
     * This is especially made such that it prints the builderString for the parent of this PickerStageController
     * and the splitPanes that it carries and the splitPanes that the splitPanes carry, if any.
     *
     * @return returns the builderString for this instance of the PickerStage
     */
    public String getParentBuilderString() {
        return getBuilderString(this.getParentPickerStageController());
    }
}
