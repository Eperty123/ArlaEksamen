package BE;

import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.control.Label;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Timer {
    private List<Node> nodesToDisable = new ArrayList<>();
    private String textBeforeTimer = "Time until you can try to log in:";
    private Duration timeoutDuration = Duration.ofSeconds(30);
    private TimeUnit timerSpeed = TimeUnit.SECONDS;
    private ScheduledExecutorService executor;
    private Label timerLabel = new Label(String.format("%s %s...", textBeforeTimer, timeoutDuration.toSeconds()));
    //A thread that counts down fot the duration timer
    Thread t = new Thread(() -> {
        Platform.runLater(new Thread(() -> {
            if (!timeoutDuration.isZero()) {
                timeoutDuration = timeoutDuration.minus(Duration.ofSeconds(1));
                timerLabel.setText(String.format("%s %s...", textBeforeTimer, timeoutDuration.toSeconds()));
                if (!nodesToDisable.isEmpty())
                    nodesToDisable.forEach(node -> {
                        if (!node.isDisabled())
                            node.setDisable(true);
                    });
            } else {
                executor.shutdownNow();
                if (!nodesToDisable.isEmpty())
                    nodesToDisable.forEach(node -> {
                        if (!node.isDisabled())
                            node.setDisable(false);
                    });
            }
        }));
    });

    /**
     * Creates a new instance of the timer
     * per standard it has duration of 30 seconds
     */
    public Timer() {
    }

    /**
     * Get the field that shows how much time is left
     *
     * @return
     */
    public Label getTimerLabel() {
        return timerLabel;
    }

    /**
     * Starts a new scheduled executor with the given timer speed
     */
    public void startTimer() {
        if (executor != null && !executor.isShutdown())
            executor.shutdown();
        executor = Executors.newSingleThreadScheduledExecutor();
        executor.scheduleWithFixedDelay(t, 0, 1, timerSpeed);
    }

    /**
     * adds a node to the nodes that gets disabled while the timer is going
     *
     * @param node
     */
    public void addNodeToDisable(Node node) {
        nodesToDisable.add(node);
    }

    public String getTextBeforeTimer() {
        return textBeforeTimer;
    }

    public void setTextBeforeTimer(String textBeforeTimer) {
        this.textBeforeTimer = textBeforeTimer;
    }

    public Duration getTimeoutDuration() {
        return timeoutDuration;
    }

    public void setTimeoutDuration(Duration timeoutDuration) {
        this.timeoutDuration = timeoutDuration;
    }
}
