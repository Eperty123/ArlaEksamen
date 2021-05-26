package GUI.Controller;

import BE.Department;
import BE.User;
import GUI.Model.DepartmentModel;
import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIcon;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.time.Duration;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class EmployeeCardController {
    @FXML
    private AnchorPane root;
    @FXML
    private ImageView img;
    @FXML
    private Label name;
    @FXML
    private Label title;
    @FXML
    private Label department;
    @FXML
    private Label phone;
    @FXML
    private Label mail;

    private static Duration countDownDuration = Duration.ofMinutes(5);
    private TimeUnit timerSpeed = TimeUnit.SECONDS;
    private ScheduledExecutorService executor;

    Thread t = new Thread(() -> {
        Platform.runLater(new Thread(() -> {
            if (!countDownDuration.isZero()) {
                countDownDuration = countDownDuration.minus(Duration.ofSeconds(1));
            } else {
                executor.shutdownNow();
                handleClose();
            }
        }));
    });

    /**
     * Starts a new scheduled executor with the given timer speed
     */
    public void startTimer() {
        if (executor != null && !executor.isShutdown())
            executor.shutdown();
        executor = Executors.newSingleThreadScheduledExecutor();
        executor.scheduleWithFixedDelay(t, 0, 1, timerSpeed);
    }

    public static void setCountdownDuration(Duration duration) {
        countDownDuration = duration;
    }

    public void setData(User u) {
        img.setImage(u.getPhotoPath() == null ? new Image("/GUI/Resources/defaultPerson.png") : new Image(u.getPhotoPath()));
        name.setText(u.getFirstName() + " " + u.getLastName());
        title.setText(u.getTitle() != null ? u.getTitle() : "None");
        for (Department d : DepartmentModel.getInstance().getAllDepartments()) {
            for (User user : d.getUsers()) {
                if (user == u) {
                    department.setText(d.getName());
                    break;
                }
                break;
            }
        }

        phone.setText(!String.valueOf(u.getPhone()).isEmpty() ? String.valueOf(u.getPhone()) : "None");
        mail.setText(!u.getEmail().isEmpty() ? u.getEmail() : "None");
    }

    public void handleClose() {
        executor.shutdownNow();
        Stage stage = (Stage) root.getScene().getWindow();
        stage.close();
    }

    /**
     * Create a Employee card vew on the specified User.
     *
     * @return Returns the created EmployeeCardController instance.
     */
    public static EmployeeCardController OpenEmployeeCard(User u) {
        Stage stage1 = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(EmployeeCardController.class.getResource("/GUI/View/PopUpViews/EmployeeCard.fxml"));

        try {
            stage1.setScene(new Scene(fxmlLoader.load()));
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }

        EmployeeCardController employeeCardController = fxmlLoader.getController();
        employeeCardController.setData(u);
        employeeCardController.startTimer();
        stage1.initStyle(StageStyle.TRANSPARENT);
        stage1.show();
        stage1.setAlwaysOnTop(true);
        return employeeCardController;
    }
}
