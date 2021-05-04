package GUI.Controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Orientation;
import javafx.scene.Node;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.BorderPane;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PickerStageController {
    @FXML
    private BorderPane root;
    private SplitPane splitPane = new SplitPane();
    private List<PickerStageController> controllers = new ArrayList<>();

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
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/Gui/View/PickerStage.fxml"));
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
        getRoot().setLeft(null);
        getRoot().setRight(null);
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
                if (controllers != null)
                    controllers.forEach(c -> {
                        stringBuilder.append(c.toString() + (controllers.indexOf(c) < controllers.size() - 1 ? "|" : ""));
                    });
                stringBuilder.append("}");
            }
            return stringBuilder.toString();
        }
    }

    private boolean huh = false;
    @FXML
    private void changeContent() {
        huh=!huh;
        System.out.println(huh?"such nothing":"much wow");
    }
}
