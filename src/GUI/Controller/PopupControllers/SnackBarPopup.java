package GUI.Controller.PopupControllers;

import com.jfoenix.controls.JFXSnackbar;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.util.Duration;

public class SnackBarPopup {

    private Pane pane;
    private String title;
    private String text;
    private Double duration;

    public SnackBarPopup(Pane pane, String title, String text, Double duration) {
        setPane(pane);
        setTitle(title);
        setText(text);
        setDuration(duration);
    }

    public Pane getPane() {
        return pane;
    }

    public void setPane(Pane pane) {
        this.pane = pane;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Double getDuration() {
        return duration;
    }

    public void setDuration(Double duration) {
        this.duration = duration;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


    public JFXSnackbar showSnackBar() {
        Text contentTitle = new Text(title);
        Text msg = new Text(text);

        // Create a new Pane for the content of the SnackBar.
        BorderPane contentPane = new BorderPane();

        var padding = new Insets(5, 5, 5, 5);
        contentPane.setPadding(padding);

        //Makes it follow the panes width
//        pane.widthProperty().addListener((observableValue, bounds, t1) -> {
//            contentPane.setPrefWidth(t1.doubleValue());
//        });
//        //Makes it follow the panes height
//        pane.heightProperty().addListener((observableValue, bounds, t1) -> {
//            contentPane.setPrefHeight(t1.doubleValue());
//        });

        //sets initial height and width
        contentPane.setPrefWidth(pane.getWidth() / 8);
        contentPane.setPrefHeight(pane.getHeight() / 8);

        // Add styles.
        msg.getStyleClass().add("snackbar-text");
        contentTitle.getStyleClass().add("snackbar-text-title");
        contentPane.getStyleClass().add("snackbar-bg");

        contentPane.setTop(contentTitle);
        contentPane.setCenter(msg);

        BorderPane.setAlignment(contentTitle, Pos.TOP_CENTER);
        BorderPane.setAlignment(msg, Pos.CENTER);

        JFXSnackbar snackBar = new JFXSnackbar(pane);
        JFXSnackbar.SnackbarEvent snackbarEvent = new JFXSnackbar.SnackbarEvent(contentPane, Duration.seconds(duration), null);
        snackBar.enqueue(snackbarEvent);
        return snackBar;
    }

    public static SnackBarPopup createSnackBarPopup(Pane pane, String title, String text, Double duration) {
        return new SnackBarPopup(pane, title, text, duration);
    }
}
