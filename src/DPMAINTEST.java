import BE.Department;
import BE.User;
import GUI.Controller.DPT.DepartmentViewController;
import GUI.Controller.StageBuilder;
import com.jfoenix.controls.JFXTextField;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.time.LocalDateTime;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class DPMAINTEST extends Application {

    public static void Main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        Department department = new Department(-1,"SuperDepartment");
        department.getUsers().add(new User("doku","poker","denn062g@easv365.dk",112));

        for(int i = 0 ; i < 2; i++)
        {
            department.getSubDepartments().add(new Department(i,"subDepartment"));
        }
        department.getSubDepartments().get(0).getUsers().add(new User("Miku","QWERTY","MikuMain@SOMEDomain.com",69));
        department.getSubDepartments().get(1).getUsers().add(new User("C","Sharp","C_the_man@SOMEDomain.com",69420));

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/GUI/View/DPT/DepartmentView.fxml"));

        BorderPane borderPane = new BorderPane(fxmlLoader.load());
        DepartmentViewController con = fxmlLoader.getController();
        con.setDepartment(department);
        stage.setScene(new Scene(borderPane));
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
