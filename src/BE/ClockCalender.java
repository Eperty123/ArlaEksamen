package BE;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.control.Label;
import javafx.util.Duration;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public final class ClockCalender {

    /**
     * This method is used to animate the labels displaying date and time on the GUI. The format can me modified
     * by editing the formatString, and the refresh rate can be changed using the Duration.millis().
     * @param dateTimeLabel label to be updated.
     */
    public static void initClock(Label dateTimeLabel) {
        Timeline dateTime = new Timeline(new KeyFrame(Duration.ZERO, e -> {
            DateTimeFormatter formatString = DateTimeFormatter.ofPattern("HH:mm:ss   |   dd/MM-yyyy");
            dateTimeLabel.setText(LocalDateTime.now().format(formatString));
        }), new KeyFrame(Duration.millis(500)));

        dateTime.setCycleCount(Animation.INDEFINITE);
        dateTime.play();
    }

}
