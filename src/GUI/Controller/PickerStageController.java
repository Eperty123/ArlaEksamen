package GUI.Controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Orientation;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.apache.poi.ss.formula.functions.T;
import org.junit.platform.engine.discovery.FileSelector;

import java.io.File;
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

    public PickerStageController() {
    }

    public void splitSceneHorizontally() {
        this.split(Orientation.HORIZONTAL);
    }

    public void splitSceneVertically() {
        this.split(Orientation.VERTICAL);
    }

    public void split(Orientation orientation) {
        try {
            splitPane.setOrientation(orientation);
            for (int i = 0; i < 2; i++) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUI/View/PickerStage.fxml"));
                splitPane.getItems().add(loader.load());
                PickerStageController p = loader.getController();
                controllers.add(p);
            }
            this.setContent(splitPane);
        } catch (IOException e) {
            e.printStackTrace();
        }
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

    @Override
    public String toString() {
        if (controllers == null)
            return "content";
        else {
            StringBuilder stringBuilder = new StringBuilder(""
            );
            if (!splitPane.getDividers().isEmpty()) {
                stringBuilder.append(String.format("%s%.02f", splitPane.getOrientation().toString().charAt(0), splitPane.getDividers().get(0).getPosition()));
                stringBuilder.append("{");
                if (!controllers.isEmpty()) {
                    if (this.getSplitPane().getOrientation().equals(Orientation.HORIZONTAL)) {
                        controllers.sort(Comparator.comparingDouble(con -> con.getRoot().getLayoutX()));
                    } else
                        controllers.sort(Comparator.comparingDouble(con -> con.getRoot().getLayoutY()));
                    controllers.forEach(c -> {
                        stringBuilder.append(c.toString() + (controllers.indexOf(c) < controllers.size() - 1 ? "|" : ""));
                    });
                }
                stringBuilder.append("}");
            }
            return stringBuilder.toString();
        }
    }

    private boolean huh = false;

    @FXML
    private void changeContent(MouseEvent mouseEvent) {
        TextField text = new TextField();
        Stage stage = new Stage();
        BorderPane borderPane = new BorderPane();
        Scene scene = new Scene(borderPane);
        Button fileChooserButton = new Button("Pick A File locally");
        borderPane.setCenter(text);
        borderPane.setTop(fileChooserButton);
        fileChooserButton.setOnAction((v)->{
            FileChooser fileChooser = new FileChooser();
            File file = fileChooser.showOpenDialog(stage.getOwner());
            if(file!=null)
                text.setText(file.getAbsolutePath());
        });
        text.setPrefWidth(200);
        text.setMaxWidth(100);
        text.setMaxWidth(Double.MAX_VALUE);
        text.textProperty().addListener((observableValue, s, t1) -> {
            showRelevantStageModifier(t1, borderPane);
        });
        stage.setX(mouseEvent.getScreenX());
        stage.setY(mouseEvent.getScreenY());
        stage.setScene(scene);
        stage.show();

    }

    private void showRelevantStageModifier(String string, BorderPane borderPane) {
        String[] strings = string.split("\\.");
        if (strings.length != 0) {
            String urlType = strings[strings.length - 1].strip();
            switch (urlType) {
                case "csv" -> System.out.println("csv");
                case "xlsx" -> System.out.println("xlsx");
                case "https" -> System.out.println("https");
            }
        }
    }

    @FXML
    private void unSplit() {
        splitPane = new SplitPane();
        controllers.clear();
        setContent(picker);
    }

    @FXML
    private void flipSplitPane() {
        if (splitPane.getItems().size() >= 2) {
            Node node = splitPane.getItems().get(0);
            splitPane.getItems().remove(node);
            splitPane.getItems().add(node);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        contextMenu.getItems().add(unsplitItem);
        contextMenu.getItems().add(flipItem);
        splitPane.setOnMouseClicked((MouseEvent mouseEvent) -> {
            if (mouseEvent.getButton() == MouseButton.SECONDARY) {
                contextMenu.show(splitPane, mouseEvent.getScreenX(), mouseEvent.getScreenY());
            } else
                contextMenu.hide();
        });
        unsplitItem.onActionProperty().set((v) -> unSplit());
        flipItem.onActionProperty().set((b) -> flipSplitPane());
    }
}
