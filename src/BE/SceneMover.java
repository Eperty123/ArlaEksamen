package BE;

import javafx.scene.Node;
import javafx.scene.input.MouseButton;
import javafx.stage.Stage;

public class SceneMover {
    private double xOffset = 0;
    private double yOffset = 0;
    
    public void move(Stage stage, Node node){
        node.setOnMousePressed(event -> {
            if (event.getButton() == MouseButton.PRIMARY) {
                this.xOffset = event.getSceneX();
                this.yOffset = event.getSceneY();
            }
        });

        node.setOnMouseDragged(event -> {
            if (event.getButton() == MouseButton.PRIMARY) {
                stage.setX(event.getScreenX() - this.xOffset);
                stage.setY(event.getScreenY() - this.yOffset);
                stage.setOpacity(0.8f);
            }
        });

        node.setOnMouseDragExited((event) -> {
            stage.setOpacity(1.0f);
        });

        node.setOnMouseReleased((event) -> {
            stage.setOpacity(1.0f);
        });
    }
}
