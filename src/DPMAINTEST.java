import BE.Department;
import BE.User;
import BE.UserType;
import BLL.DepartmentManager;
import GUI.Controller.DPT.DepartmentStageController;
import GUI.Controller.StageBuilder;
import GUI.Model.DataModel;
import GUI.Model.UserModel;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DPMAINTEST extends Application {

    public static void Main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        initStageBuilderStage(stage);

    }

    private void initDPTStage(Stage stage) throws java.io.IOException {
        DepartmentManager departmentManager = new DepartmentManager();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("GUI/View/DPT/DepartmentStage.fxml"));
        AnchorPane node = loader.load();
        DepartmentStageController con2 = loader.getController();
        for (Department department : departmentManager.getSuperDepartments())
            con2.addChildrenNode(department);

        Button b = new Button("Save");
        con2.getChildrenNodes().add(b);

        b.setOnAction((save) -> {
            con2.getDepartmentViewControllers().forEach(vc -> {
                vc.getAllSubDepartments().forEach(item -> {
                    List<User> users = new ArrayList<>(item.getUsers());
                    users.removeIf(u -> u.getUserName().isEmpty() || u.getUserRole() != UserType.Admin);
                    if (!users.isEmpty() && item.getManager() == null) {
                        item.setManager(users.get(0));
                        try {
                            DataModel.getInstance().addDepartment(item);

                            for (Department dpt : vc.getDepartment().getAllSubDepartments()) {
                                if (dpt.getSubDepartments().contains(item)) {
                                    DataModel.getInstance().addSubDepartment(dpt, item);
                                    break;
                                }
                            }
                        } catch (SQLException throwables) {
                            // Error on adding and updating department.
                        }
                    } else if (item.getManager() == null) {
                        User placeholderUser = new User();
                        placeholderUser.setUserName("admtest");
                        item.setManager(placeholderUser);
                    }
                });
                try {
                    UserModel.getInstance().updateUserDepartment(vc.getAllSubDepartments());
                }
                catch (SQLException throwables) {
                    // Error on update user department.
                }
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
