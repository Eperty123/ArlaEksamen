package BE;

import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class SceneMover {
    private double xOffset = 0;
    private double yOffset = 0;
    
    public void move(Stage stage, Node pane){
        pane.setOnMousePressed(event -> {
            if (event.getButton().equals(MouseButton.PRIMARY)) {
                xOffset = event.getSceneX();
                yOffset = event.getSceneY();
            }
        });

        pane.setOnMouseDragged(event -> {
            if (event.getButton().equals(MouseButton.PRIMARY)) {
                stage.setX(event.getScreenX() - xOffset);
                stage.setY(event.getScreenY() - yOffset);
                stage.setOpacity(0.8f);
            }
        });

        pane.setOnMouseDragExited((event) -> {
            stage.setOpacity(1.0f);
        });

        pane.setOnMouseReleased((event) -> {
            stage.setOpacity(1.0f);
        });    
    }
}
