package BE;

import GUI.Controller.EmployeeCardController;
import GUI.Controller.InfoboardController;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIcon;
import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIconView;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeType;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

import javax.swing.border.Border;

public class InfoboardPaneFactory {
    private static Department currentDepartment;

    public static BorderPane createInfoBoard(Department department) {
        currentDepartment = department;

        BorderPane bp = new BorderPane();

        bp.setTop(createBar(currentDepartment));
        bp.setCenter(createFlowPane());


        return bp;
    }

    private static Pane createBar(Department currentDepartment) {
        Pane topPane = new Pane();
        topPane.setMinWidth(-1);
        topPane.setMinHeight(40);
        topPane.setMaxHeight(40);

        Rectangle bar = new Rectangle(0, 0, 300, 40);
        bar.setArcWidth(25);
        bar.setArcHeight(25);
        bar.setFill(Paint.valueOf("#154c5d"));

        Label title = new Label();
        title.setMouseTransparent(true);
        title.setText(currentDepartment.getName());
        title.setTextFill(Paint.valueOf("#FFFFFF"));
        title.setStyle("-fx-font-size: 16px;-fx-font-weight: bold; -fx-font-style: italic");
        title.setPrefSize(268, 25);
        title.setLayoutX(14);
        title.setLayoutY(8);
        title.setAlignment(Pos.TOP_LEFT);
        title.setContentDisplay(ContentDisplay.LEFT);
        title.setTextAlignment(TextAlignment.LEFT);

        topPane.getChildren().addAll(bar, title);

        BorderPane.setMargin(topPane, new Insets(25, 0, 0, 25));

        return topPane;
    }

    private static FlowPane createFlowPane() {
        FlowPane flowPane = new FlowPane();
        flowPane.setPrefSize(-1, -1);
        flowPane.setOrientation(Orientation.HORIZONTAL);

        for (User u : currentDepartment.getUsers()) {
                flowPane.getChildren().add(0, createUser(u));
                FlowPane.setMargin(createUser(u), new Insets(25, 25, 0, 25));
        }

        return flowPane;
    }

    private static Pane createUser(User u) {
        Pane newPane = new Pane();
        newPane.setPrefSize(150, 250);

        newPane.setAccessibleText(u.getFirstName());
        Rectangle newRectangle = new Rectangle(150, 250);
        newRectangle.setArcHeight(50);
        newRectangle.setArcWidth(50);
        newRectangle.setFill(Paint.valueOf("#154c5d"));
        newRectangle.getStyleClass().add("SMButtons");

        Circle newCircle = new Circle();
        newCircle.setMouseTransparent(true);
        newCircle.setRadius(50);
        newCircle.setLayoutX(75);
        newCircle.setLayoutY(64);

        System.out.println(u.getPhotoPath());
        ImageView image = new ImageView(u.getPhotoPath() == null || u.getPhotoPath().isEmpty() ? new Image("/Gui/Resources/defaultPerson.png") : new Image("/Gui/Resources/defaultPerson.png"));
        image.setMouseTransparent(true);
        image.setFitWidth(105);
        image.setFitHeight(105);
        image.setLayoutX(23);
        image.setLayoutY(12);

        Rectangle underBar = new Rectangle();
        underBar.setMouseTransparent(true);
        underBar.setArcWidth(5);
        underBar.setArcHeight(5);
        underBar.setWidth(120);
        underBar.setHeight(4);
        underBar.setLayoutX(15);
        underBar.setLayoutY(123);
        underBar.setFill(Paint.valueOf("#add8e5"));

        Label name = new Label();
        name.setMouseTransparent(true);
        name.getStyleClass().add("SMButtons");
        name.setText(u.getFirstName() + " " + u.getLastName());
        name.setTextFill(Paint.valueOf("#FFFFFF"));
        name.setStyle("-fx-font-size: 12px;-fx-font-weight: bold; -fx-font-style: italic");
        name.setPrefSize(139, 13);
        name.setLayoutX(6);
        name.setLayoutY(138);
        name.setAlignment(Pos.TOP_CENTER);
        name.setContentDisplay(ContentDisplay.CENTER);
        name.setTextAlignment(TextAlignment.CENTER);

        Label title = new Label();
        title.setMouseTransparent(true);
        title.getStyleClass().add("SMButtons");
        title.setText(u.getTitle());
        title.setTextFill(Paint.valueOf("#FFFFFF"));
        title.setStyle("-fx-font-size: 12px;-fx-font-weight: bold; -fx-font-style: italic");
        title.setPrefSize(139, 13);
        title.setLayoutX(6);
        title.setLayoutY(158);
        title.setAlignment(Pos.TOP_CENTER);
        title.setContentDisplay(ContentDisplay.CENTER);
        title.setTextAlignment(TextAlignment.CENTER);

        Label department = new Label();
        department.setMouseTransparent(true);
        department.getStyleClass().add("SMButtons");
        department.setText(currentDepartment.getName());
        department.setTextFill(Paint.valueOf("#FFFFFF"));
        department.setStyle("-fx-font-size: 12px;-fx-font-weight: bold; -fx-font-style: italic");
        department.setPrefSize(139, 13);
        department.setLayoutX(6);
        department.setLayoutY(178);
        department.setAlignment(Pos.TOP_CENTER);
        department.setContentDisplay(ContentDisplay.CENTER);
        department.setTextAlignment(TextAlignment.CENTER);

        Rectangle viewMoreButton = new Rectangle();
        viewMoreButton.getStyleClass().add("employeeButton");
        viewMoreButton.setArcHeight(15);
        viewMoreButton.setArcWidth(15);
        viewMoreButton.setWidth(120);
        viewMoreButton.setHeight(23);
        viewMoreButton.setLayoutY(215);
        viewMoreButton.setLayoutX(15);

        Label viewMoreLabel = new Label();
        viewMoreLabel.setMouseTransparent(true);
        viewMoreLabel.setText("View More");
        viewMoreLabel.setTextFill(Paint.valueOf("#154c5d"));
        viewMoreLabel.setStyle("-fx-font-size: 12px;-fx-font-weight: bold; -fx-font-style: italic");
        viewMoreLabel.setPrefSize(60, 17);
        viewMoreLabel.setLayoutX(47);
        viewMoreLabel.setLayoutY(218);
        viewMoreLabel.setAlignment(Pos.TOP_CENTER);
        viewMoreLabel.setContentDisplay(ContentDisplay.CENTER);
        viewMoreLabel.setTextAlignment(TextAlignment.CENTER);

        viewMoreButton.setOnMouseClicked(e -> {
            EmployeeCardController.OpenEmployeeCard(u);
        });

        newPane.getChildren().addAll(newRectangle, newCircle, image, underBar, name, title, department, viewMoreButton, viewMoreLabel);

        FlowPane.setMargin(newPane, new Insets(25, 25, 25, 25));
        return newPane;
    }

    public static Pane createDepartmentLabel(Department d, HBox hbox){
        Pane pane = new Pane();
        pane.setPrefWidth(-1);
        pane.setPrefHeight(30);

        Rectangle rectangle = new Rectangle();
        rectangle.setArcHeight(25);
        rectangle.setArcWidth(25);
        rectangle.setHeight(25);
        rectangle.setWidth(150);
        rectangle.setLayoutY(3);
        rectangle.setFill(Paint.valueOf("#154c5d"));
        rectangle.setStroke(Paint.valueOf("#ffffff"));
        rectangle.setStrokeWidth(1);
        rectangle.setStrokeType(StrokeType.OUTSIDE);

        Label deptname = new Label();
        deptname.setId("ID");
        deptname.setText(d.getName());
        deptname.setTextFill(Paint.valueOf("#ffffff"));
        deptname.setStyle("-fx-font-size: 12px;-fx-font-weight: bold; -fx-font-style: italic");
        deptname.setPrefSize(110,17);
        deptname.setLayoutX(14);
        deptname.setLayoutY(7);

        MaterialDesignIconView close = new MaterialDesignIconView();
        close.getStyleClass().add("removeUser");
        close.setFill(Paint.valueOf("#ffffff"));
        close.setLayoutX(128);
        close.setLayoutY(22);
        close.setGlyphName("CLOSE_CIRCLE_OUTLINE");
        close.setSize("16");

        close.setOnMouseClicked(e -> {
            hbox.getChildren().remove(pane);
        });

        pane.getChildren().addAll(rectangle,deptname,close);

        FlowPane.setMargin(pane,new Insets(0,0,0,20));

        return pane;
    }
}
