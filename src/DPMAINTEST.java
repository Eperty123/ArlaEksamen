import BLL.DepartmentManager;
import DAL.DepartmentDAL;
import GUI.Controller.DPT.DepartmentStageController;
import GUI.Controller.StageBuilder;
import GUI.Model.DepartmentModel;
import GUI.Model.UserModel;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.util.ArrayList;

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
        con2.addChildrenNode(departmentManager.getSuperDepartment());

        Button b = new Button("Save");
        con2.getChildrenNodes().add(b);

        b.setOnAction((save)->{
            con2.getDepartmentViewControllers().forEach(vc->{
                UserModel.getInstance().updateUserDepartment(vc.getSubDepartments());
            });
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
