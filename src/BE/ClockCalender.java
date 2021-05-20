package BE;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.control.Label;
import javafx.util.Duration;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ClockCalender {

    public static void initClock(Label dateTimeLabel) {
        Timeline dateTime = new Timeline(new KeyFrame(Duration.ZERO, e -> {
            DateTimeFormatter formatString = DateTimeFormatter.ofPattern("HH:mm:ss   |   dd/MM-yyyy");
            dateTimeLabel.setText(LocalDateTime.now().format(formatString));
        }), new KeyFrame(Duration.millis(500)));

        dateTime.setCycleCount(Animation.INDEFINITE);
        dateTime.play();
    }

}
