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
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
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
    private final SplitPane splitPane = new SplitPane();
    private final List<PickerStageController> controllers = new ArrayList<>();
    private final ContextMenu contextMenu = new ContextMenu();
    private PickerStageController parentPickerStageController;
    private PickerStageController closestParentPickerStageController;
    private boolean isZoomed = false;
    private final DoubleProperty scale = new SimpleDoubleProperty();

    /**
     * Calls the init method
     *
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        init();
    }

    /**
     * Initializes contextMenus
     * adds onAction to menuItems
     */
    private void init() {
        initContextMenu(ap, contextMenu);
        contextMenu.getItems().addAll(Arrays.asList(
                //Opens the change content window when clicking the Change content menuItem
                new MenuItemBit("Change content", (action) -> changeContent()).getMenuItem(),
                //Unsplits the current window when clicking the Unsplit menuItem
                new MenuItemBit("Unsplit", (action) -> {
                    if (closestParentPickerStageController != null)
                        closestParentPickerStageController.unSplit();
                    else
                        unSplit();
                }).getMenuItem(),
                //Changes the orientation of the current window, when clicking the Change orientation menuItem
                new MenuItemBit("Change Orientation", (action) -> {
                    if (closestParentPickerStageController != null) {
                        Orientation oldOrientation = closestParentPickerStageController.getSplitPane().getOrientation();
                        closestParentPickerStageController.getSplitPane().setOrientation(oldOrientation == Orientation.HORIZONTAL ? Orientation.VERTICAL : Orientation.HORIZONTAL);
                    } else {
                        Orientation oldOrientation = closestParentPickerStageController.getSplitPane().getOrientation();
                        splitPane.setOrientation(oldOrientation == Orientation.HORIZONTAL ? Orientation.VERTICAL : Orientation.HORIZONTAL);
                    }
                }).getMenuItem(),
                //FLips the current window, when clicking the Flip menuItem
                new MenuItemBit("Flip", (action) -> {
                    if (closestParentPickerStageController != null)
                        closestParentPickerStageController.flipSplitPane();
                    else
                        flipSplitPane();
                }).getMenuItem(),
                new SeparatorMenuItem(),
                //Resets the current window, when clicking the Reset menuItem
                new MenuItemBit("Reset", (action) -> unSplit()).getMenuItem()));
    }

    /**
     * Gets all the nodes held within the pickerStageController
     *
     * @param pickerStageController the pickerStageController you want to nodes off
     * @return The nodes held within the current controller
     */
    private List<Node> getNodes(PickerStageController pickerStageController) {
        List<Node> temp = new ArrayList<>();
        if (pickerStageController.getControllers().isEmpty())
            temp.add(pickerStageController.getContent());
        else
            pickerStageController.getControllers().forEach(stageController -> temp.addAll(stageController.getNodes(stageController)));
        return temp;
    }

    /**
     * Initializes the contexMenus in the given node
     *
     * @param node        the node you want the context menu to initialize in
     * @param contextMenu tht context menu you want to initialize
     */
    private void initContextMenu(Node node, ContextMenu contextMenu) {
        node.setOnMouseClicked((MouseEvent mouseEvent) -> {
            //Shows the original contextMenu if the stage is enabled
            if (parentPickerStageController == null || !parentPickerStageController.getRoot().isDisabled()) {
                if (!splitPane.isDisabled())
                    if (mouseEvent.getButton() == MouseButton.SECONDARY) {
                        if (contextMenu.isShowing())
                            contextMenu.hide();
                        contextMenu.show(node, mouseEvent.getScreenX(), mouseEvent.getScreenY());
                    } else
                        contextMenu.hide();
            }
            //Otherwise it allows the user to zoom using the left click button, zoom out with leftclick +shift
            // and stop zooming with right click
            // and it allows the user to move the zoomed screen with the middle mouse button
            else {
                //this ensures that this will only be called by one controller
                if (parentPickerStageController == this) {
                    AnchorPane anchorPane = parentPickerStageController.getAp();
                    //Zooms on left click
                    if (mouseEvent.getButton() == MouseButton.PRIMARY && !isZoomed) {
                        isZoomed = true;
                        DoubleProperty invScale = new SimpleDoubleProperty();
                        scale.addListener((observableValue, number, t1) -> {
                            anchorPane.setScaleX(scale.get());
                            anchorPane.setScaleY(scale.get());
                            invScale.set(scale.get() * (1 - Math.pow(scale.get(), -1)));
                        });
                        scale.set(1.5);

                        //moves on middle button drag
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
                        //Stops the zoom
                        isZoomed = false;
                        anchorPane.setTranslateY(0);
                        anchorPane.setTranslateX(0);
                        scale.set(1);
                    }
                    if (mouseEvent.getButton() == MouseButton.PRIMARY && isZoomed) {
                        //Zooms additionally to the initial zoom, and zooms out
                        if (mouseEvent.isShiftDown() && scale.get() > 1) {
                            scale.set(scale.get() - 0.25);
                        } else
                            scale.set(scale.get() + 0.25);
                    }
                }
            }
        });
    }

    /**
     * Locks the panes within the given controller
     *
     * @param pickerStageController
     */
    private void lockPanes(PickerStageController pickerStageController) {
        pickerStageController.getRoot().setDisable(true);
    }

    /**
     * Locks the parentPickerController meaning that all the children nodes also get locked
     */
    public void lockPanes() {
        lockPanes(parentPickerStageController);
    }

    /**
     * Gets the closestParentPickerStage, this is mostly used for flipping windows and such, since it is not always
     * the actual PickerStage you click you want to flip, but the closest parent.
     *
     * @return the closest parent picker stage
     */
    public PickerStageController getClosestParentPickerStageController() {
        return closestParentPickerStageController;
    }

    /**
     * Sets the closest parent picker stage controller
     *
     * @param closestParentPickerStageController
     */
    public void setClosestParentPickerStageController(PickerStageController closestParentPickerStageController) {
        this.closestParentPickerStageController = closestParentPickerStageController;
    }

    /**
     * Gets the parentPickerstageController
     *
     * @return the parentPickerstageController
     */
    public PickerStageController getParentPickerStageController() {
        return parentPickerStageController;
    }

    /**
     * Sets the parentPickerStageController
     *
     * @param pickerStageController the new parentPickerstageController
     */
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
            e.printStackTrace();
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

    /**
     * Initializes a change content window
     *
     * @return the stage of the window
     */
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
            e.printStackTrace();
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
        //root.setStyle("src/GUI/Resources/Styles.css");
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
