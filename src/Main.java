import BE.SceneMover;
import GUI.Controller.LoginController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("GUI/View/Login.fxml"));
        Parent root = loader.load();
        LoginController controller = loader.getController();
        primaryStage.setTitle("Login");
        int WIDTH = 800;
        int HEIGHT = 600;
        primaryStage.setScene(new Scene(root, WIDTH, HEIGHT));
        primaryStage.initStyle(StageStyle.UNDECORATED);
        primaryStage.getIcons().addAll(
                new Image("/GUI/Resources/AppIcons/icon16x16.png"),
                new Image("/GUI/Resources/AppIcons/icon24x24.png"),
                new Image("/GUI/Resources/AppIcons/icon32x32.png"),
                new Image("/GUI/Resources/AppIcons/icon48x48.png"),
                new Image("/GUI/Resources/AppIcons/icon64x64.png"));
        primaryStage.show();

        Node bar = root.getChildrenUnmodifiable().get(root.getChildrenUnmodifiable().size() - 2);
        SceneMover sceneMover = new SceneMover();
        sceneMover.move(primaryStage, bar);
    }


    public static void main(String[] args) {
        launch(args);
    }
}
