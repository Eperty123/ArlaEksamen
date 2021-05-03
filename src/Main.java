import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Main extends Application {
    private final int WIDTH = 800;
    private final int HEIGHT = 600;

    private double xOffset = 0;
    private double yOffset = 0;

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("GUI/View/ChartTestView.fxml"));
        primaryStage.setTitle("Login");
        primaryStage.setScene(new Scene(root, WIDTH, HEIGHT));
        primaryStage.initStyle(StageStyle.UNDECORATED);
        primaryStage.show();

        root.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                xOffset = event.getSceneX();
                yOffset = event.getSceneY();
            }
        });

        root.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                primaryStage.setX(event.getScreenX() - xOffset);
                primaryStage.setY(event.getScreenY() - yOffset);
                primaryStage.setOpacity(0.8f);
            }
        });

        root.setOnMouseDragExited((event) -> {
            primaryStage.setOpacity(1.0f);
        });

        root.setOnMouseReleased((event) -> {
            primaryStage.setOpacity(1.0f);
        });
    }


    public static void main(String[] args) {
        launch(args);
    }
}
