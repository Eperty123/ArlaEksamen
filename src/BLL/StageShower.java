package BLL;

import BE.SceneMover;
import BLL.LoginManager;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class StageShower {

    public void handleLogout(Stage dashboardStage, SceneMover sceneMover) throws IOException {
        Stage stage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/GUI/VIEW/Login.fxml"));

        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Login");
        stage.getIcons().addAll(
                new Image("/GUI/Resources/AppIcons/icon16x16.png"),
                new Image("/GUI/Resources/AppIcons/icon24x24.png"),
                new Image("/GUI/Resources/AppIcons/icon32x32.png"),
                new Image("/GUI/Resources/AppIcons/icon48x48.png"),
                new Image("/GUI/Resources/AppIcons/icon64x64.png"));
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setScene(scene);
        stage.show();

        BorderPane borderPane = (BorderPane) stage.getScene().getRoot();

        sceneMover.move(stage, borderPane.getTop());

        LoginManager.setCurrentUser(null);
        dashboardStage.close();
    }

    public void showScene(Stage stage, Parent root, SceneMover sceneMover) {
        Scene scene = new Scene(root);
        sceneMover.move(stage, scene.getRoot());

        stage.getIcons().addAll(
                new Image("/GUI/Resources/AppIcons/icon16x16.png"),
                new Image("/GUI/Resources/AppIcons/icon24x24.png"),
                new Image("/GUI/Resources/AppIcons/icon32x32.png"),
                new Image("/GUI/Resources/AppIcons/icon48x48.png"),
                new Image("/GUI/Resources/AppIcons/icon64x64.png"));
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setScene(scene);
        stage.show();
    }

    public void showScene(Stage stage, Parent root, SceneMover sceneMover,Node moverNode) {
        Scene scene = new Scene(root);
        sceneMover.move(stage, moverNode);

        stage.getIcons().addAll(
                new Image("/GUI/Resources/AppIcons/icon16x16.png"),
                new Image("/GUI/Resources/AppIcons/icon24x24.png"),
                new Image("/GUI/Resources/AppIcons/icon32x32.png"),
                new Image("/GUI/Resources/AppIcons/icon48x48.png"),
                new Image("/GUI/Resources/AppIcons/icon64x64.png"));
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setScene(scene);
        stage.show();
    }

}
