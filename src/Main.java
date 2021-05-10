import BE.SceneMover;
import GUI.Controller.LoginController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Main extends Application {
    private final int WIDTH = 800;
    private final int HEIGHT = 600;

    private double xOffset = 0;
    private double yOffset = 0;
    private boolean maximize = false;

    @Override
    public void start(Stage primaryStage) throws Exception{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("GUI/View/Login.fxml"));
        Parent root = loader.load();
        LoginController controller = loader.getController();
        controller.getMaximize().setOnMouseClicked((v)->{
            maximize=!maximize;
            primaryStage.setMaximized(maximize);
        });
        primaryStage.setTitle("Login");
        primaryStage.setScene(new Scene(root, WIDTH, HEIGHT));
        primaryStage.initStyle(StageStyle.UNDECORATED);
        primaryStage.show();

        Node bar = root.getChildrenUnmodifiable().get(root.getChildrenUnmodifiable().size()-2);
        SceneMover sceneMover = new SceneMover();
        sceneMover.move(primaryStage,bar);
    }


    public static void main(String[] args) {
        launch(args);
    }
}
