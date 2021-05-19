package GUI.Controller.AdminControllers;

import BE.MenuItemBit;
import GUI.Controller.PopupControllers.WarningController;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Orientation;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.control.SplitPane;
import javafx.scene.control.Tooltip;
import javafx.scene.input.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicLong;

public class PickerStageController implements Initializable {
    @FXML
    private GridPane picker;
    @FXML
    private BorderPane root;
    @FXML
    private AnchorPane ap;
    private SplitPane splitPane = new SplitPane();
    private List<PickerStageController> controllers = new ArrayList<>();
    private ContextMenu contextMenu = new ContextMenu();
    private PickerStageController parentPickerStageController;
    private PickerStageController closestParentPickerStageController;

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
        initContextMenu(ap, contextMenu);
        contextMenu.getItems().addAll(Arrays.asList(
                new MenuItemBit("Change content", (action) -> changeContent()).getMenuItem(),
                new MenuItemBit("Unsplit", (action) -> {
                    if (closestParentPickerStageController != null)
                        closestParentPickerStageController.unSplit();
                    else
                        unSplit();
                }).getMenuItem(),
                new MenuItemBit("Change Orientation", (action) -> {
                    if (closestParentPickerStageController != null) {
                        Orientation oldOrientation = closestParentPickerStageController.getSplitPane().getOrientation();
                        closestParentPickerStageController.getSplitPane().setOrientation(oldOrientation == Orientation.HORIZONTAL ? Orientation.VERTICAL : Orientation.HORIZONTAL);
                    } else {
                        Orientation oldOrientation = closestParentPickerStageController.getSplitPane().getOrientation();
                        splitPane.setOrientation(oldOrientation == Orientation.HORIZONTAL ? Orientation.VERTICAL : Orientation.HORIZONTAL);
                    }
                }).getMenuItem(),
                new MenuItemBit("Flip",(action) -> {
                    if (closestParentPickerStageController != null)
                        closestParentPickerStageController.flipSplitPane();
                    else
                        flipSplitPane();
                }).getMenuItem(),
                new SeparatorMenuItem(),
                new MenuItemBit("Reset",(action)->unSplit()).getMenuItem()));
    }

    private List<Node> getNodes(PickerStageController pickerStageController) {
        List<Node> temp = new ArrayList<>();
        if (pickerStageController.getControllers().isEmpty())
            temp.add(pickerStageController.getContent());
        else
            pickerStageController.getControllers().forEach(c -> temp.addAll(c.getNodes(c)));
        return temp;
    }

    private boolean isZoomed = false;
    private DoubleProperty scale = new SimpleDoubleProperty();

    private void initContextMenu(Node node, ContextMenu contextMenu) {
        node.setOnMouseClicked((MouseEvent mouseEvent) -> {
            if (parentPickerStageController==null||!parentPickerStageController.getRoot().isDisabled()) {
                if (!splitPane.isDisabled())
                    if (mouseEvent.getButton() == MouseButton.SECONDARY) {
                        if (contextMenu.isShowing())
                            contextMenu.hide();
                        contextMenu.show(node, mouseEvent.getScreenX(), mouseEvent.getScreenY());
                    } else
                        contextMenu.hide();
            } else {
                if (parentPickerStageController == this) {
                    AnchorPane anchorPane = parentPickerStageController.getAp();
                    if (mouseEvent.getButton() == MouseButton.PRIMARY && !isZoomed) {
                        isZoomed = true;
                        DoubleProperty invScale = new SimpleDoubleProperty();
                        scale.addListener((observableValue, number, t1) -> {
                            anchorPane.setScaleX(scale.get());
                            anchorPane.setScaleY(scale.get());
                            invScale.set(scale.get() * (1 - Math.pow(scale.get(), -1)));
                        });
                        scale.set(1.5);

                        AtomicLong x = new AtomicLong();
                        AtomicLong y = new AtomicLong();
                        anchorPane.onMousePressedProperty().set((MouseEvent mouseEvent1337) -> {
                            if (mouseEvent1337.getButton() == MouseButton.MIDDLE) {
                                x.set((long) mouseEvent1337.getSceneX());
                                y.set((long) mouseEvent1337.getSceneY());
                            }
                        });
                        anchorPane.onMouseDraggedProperty().set((MouseEvent mouseEvent1337) -> {
                            if (isZoomed && mouseEvent1337.getButton() == MouseButton.MIDDLE) {
                                if (Math.abs(mouseEvent1337.getSceneX() - x.get()) <= (anchorPane.getWidth() / 2) * invScale.get())
                                    anchorPane.setTranslateX(mouseEvent1337.getSceneX() - x.get());
                                if (Math.abs(mouseEvent1337.getSceneY() - y.get()) <= (anchorPane.getHeight() / 2) * invScale.get())
                                    anchorPane.setTranslateY(mouseEvent1337.getSceneY() - y.get());
                            }
                        });
                    } else if (mouseEvent.getButton() == MouseButton.SECONDARY) {
                        isZoomed = false;
                        anchorPane.setTranslateY(0);
                        anchorPane.setTranslateX(0);
                        scale.set(1);
                    }
                    if (mouseEvent.getButton() == MouseButton.PRIMARY && isZoomed) {
                        if (mouseEvent.isShiftDown() && scale.get() > 1) {
                            scale.set(scale.get() - 0.25);
                        } else
                            scale.set(scale.get() + 0.25);
                    }
                }
            }
        });
    }

    private void lockPanes(PickerStageController pickerStageController) {
        parentPickerStageController.getRoot().setDisable(true);
    }

    public void lockPanes() {
        lockPanes(parentPickerStageController);
    }

    public PickerStageController getClosestParentPickerStageController() {
        return closestParentPickerStageController;
    }

    public void setClosestParentPickerStageController(PickerStageController closestParentPickerStageController) {
        this.closestParentPickerStageController = closestParentPickerStageController;
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
                p.setClosestParentPickerStageController(this);
                controllers.add(p);
            }
            this.setContent(splitPane);
        } catch (IOException e) {
            WarningController.createWarning("Oh no! Something went wrong trying to load the " +
                    "screen picker view. It might be corrupted or lost.");
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
            Stage stage = changeContent();
            stage.setX(mouseEvent.getScreenX() - stage.getWidth() / 2);
            stage.setY(mouseEvent.getScreenY() - stage.getHeight() / 2);
        }
    }

    private Stage changeContent() {
        try {
            Stage stage = new Stage();
            stage.initOwner(root.getScene().getWindow());
            stage.setAlwaysOnTop(true);
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUI/View/PopUpViews/DataManagement.fxml"));
            AnchorPane pane = loader.load();
            stage.setTitle("Add content");
            DataManagementController dataManagementController = loader.getController();

            dataManagementController.setPickerStageController(this);
            dataManagementController.setStage(stage);
            Scene scene = new Scene(pane);
            stage.setScene(scene);
            stage.initStyle(StageStyle.TRANSPARENT);
            stage.show();
            return stage;
        } catch (IOException e) {
            WarningController.createWarning("Oh no! Something went wrong trying to load the " +
                    "screen picker view. It might be corrupted or lost.");
        }
        return null;
    }

    /**
     * Gets the content of the PickerStageController
     *
     * @return
     */
    public Node getContent() {
        return root.getCenter();
    }

    /**
     * Sets the content of the PickerStageController
     *
     * @param node           the node you want to set it to
     * @param accessibleText the builderString for the given node (ViewType="URL")
     */
    public void setContent(Node node, String accessibleText) {
        setContent(node);
        getContent().setAccessibleText(accessibleText);
    }

    /**
     * Sets the content without having to set the Accessible Text
     *
     * @param node The node you want it to show
     */
    public void setContent(Node node) {
        root.setCenter(node);
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

    public AnchorPane getAp() {
        return ap;
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
            PickerStageController p = controllers.get(0);
            controllers.remove(p);
            controllers.add(p);
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
                stringBuilder.append(String.format("%n%s%.02f", splitPane.getOrientation().toString().charAt(0), splitPane.getDividers().get(0).getPosition()));
                stringBuilder.append("{");
                controllersOfInterest.forEach(c -> {
                    stringBuilder.append(c.getBuilderString()).append(controllersOfInterest.indexOf(c) < controllersOfInterest.size() - 1 ? "|" : "");
                });
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
