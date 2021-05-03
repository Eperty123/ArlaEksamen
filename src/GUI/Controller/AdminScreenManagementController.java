package GUI.Controller;

import BE.Screen;
import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIcon;
import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIconView;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;


public class AdminScreenManagementController {

    @FXML
    private FlowPane root;

    public void handleNewScreen(){
        Screen s = new Screen("Screen");
        Pane newPane = new Pane();
        newPane.setPrefSize(150,150);

        Rectangle newRectangle = new Rectangle(150,150);
        newRectangle.setArcHeight(50);
        newRectangle.setArcWidth(50);
        newRectangle.setFill(Paint.valueOf("#154c5d"));
        newRectangle.getStyleClass().add("SMButtons");

        MaterialDesignIconView settings = new MaterialDesignIconView();
        settings.setIcon(MaterialDesignIcon.SETTINGS);
        settings.setFill(Paint.valueOf("#0d262e"));
        settings.getStyleClass().add("SMButtons");
        settings.setLayoutX(116);
        settings.setLayoutY(31);
        settings.setSize(String.valueOf(20));

        MaterialDesignIconView screen = new MaterialDesignIconView();
        screen.setIcon(MaterialDesignIcon.MONITOR);
        screen.setFill(Paint.valueOf("#0d262e"));
        screen.setStyle(".SMButtons");
        screen.setLayoutX(39);
        screen.setLayoutY(102);
        screen.setSize(String.valueOf(72));

        Label label = new Label();
        label.setText("Screen");
        label.setTextFill(Paint.valueOf("#FFFFFF"));
        label.setFont(new Font("System",16));
        label.setStyle("-fx-font-weight: bold; -fx-font-style: italic");
        label.setLayoutX(44);
        label.setLayoutY(111);
        label.setAlignment(Pos.TOP_CENTER);
        label.setTextAlignment(TextAlignment.CENTER);

        newPane.getChildren().addAll(newRectangle,settings,screen,label);

        root.getChildren().add(0,newPane);

        FlowPane.setMargin(newPane, new Insets(25,25,0,25));

        root.getChildren().get(0).setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                test(s);
            }
        });
    }

    private void test(Screen s){
        System.out.println(s.getName());
    }
}
