import BLL.DepartmentManager;
import GUI.Controller.DPT.DepartmentStageController;
import GUI.Controller.DPT.DepartmentViewController;
import GUI.Controller.StageBuilder;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.io.IOException;

public class DPMAINTEST extends Application {
    DepartmentManager departmentManager = new DepartmentManager();

    public static void Main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("GUI/View/DPT/DepartmentStage.fxml"));
        AnchorPane node = loader.load();
        DepartmentStageController con2 = loader.getController();
        HBox hBox = con2.gethBox();
        departmentManager.getSuperDepartments().forEach(sd->{
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/GUI/View/DPT/DepartmentView.fxml"));
            try {
                hBox.getChildren().add(fxmlLoader.load());
                DepartmentViewController con = fxmlLoader.getController();
                con.setDepartment(sd);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        stage.setScene(new Scene(node));
        stage.show();

    }

    private void initStageBuilderStage(Stage stage) throws Exception {
        StageBuilder stageBuilder = new StageBuilder();
        Node node = stageBuilder.makeStage("H0.56{BarChart=\"src/Resources/BarChart_mockData.csv\"|}");

        BorderPane bp = new BorderPane(node);
        AnchorPane.setTopAnchor(bp, 0.0);
        AnchorPane.setRightAnchor(bp, 0.0);
        AnchorPane.setLeftAnchor(bp, 0.0);
        AnchorPane.setBottomAnchor(bp, 0.0);
        Button b = new Button("print");
        bp.setTop(b);
        b.setOnAction((v) -> System.out.println(stageBuilder.getRootController().getParentBuilderString()));
        //stageBuilder.getRootController().lockPanes();
        AnchorPane ap = new AnchorPane(bp);
        stage.setScene(new Scene(ap));
        stage.show();
    }
}
