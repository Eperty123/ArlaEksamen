import GUI.Controller.StageBuilder;
import javafx.application.Application;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class DPMAINTEST extends Application {

    public static void Main(String[]args){ launch(args);}

    @Override
    public void start(Stage stage) throws Exception {
        StageBuilder stageBuilder = new StageBuilder();
        Node node = stageBuilder.makeStage("H0.50{}");

        BorderPane bp = new BorderPane(node);
        AnchorPane.setTopAnchor(bp,0.0);
        AnchorPane.setRightAnchor(bp,0.0);
        AnchorPane.setLeftAnchor(bp,0.0);
        AnchorPane.setBottomAnchor(bp,0.0);
        Button b = new Button("print");
        bp.setTop(b);
        b.setOnAction((v)-> System.out.println(stageBuilder.getRootController().getParentBuilderString()));

        AnchorPane ap = new AnchorPane(bp);
        stage.setScene(new Scene(ap));
        stage.show();
    }
}
