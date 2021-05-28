package BE;

import GUI.Model.DataModel;
import GUI.Model.SettingsModel;
import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.control.Label;

import javax.xml.crypto.Data;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Timer {
    private final List<Node> nodesToDisable = new ArrayList<>();
    private String textBeforeTimer = "Time until you can try to log in:";
    private Duration timeoutDuration = Duration.ofSeconds(5);
    private Duration countDownDuration = this.timeoutDuration;
    private final TimeUnit timerSpeed = TimeUnit.SECONDS;
    private ScheduledExecutorService executor;
    private final Label timerLabel = new Label(String.format("%s %s...", this.textBeforeTimer, this.timeoutDuration.toSeconds()));
    //A thread that counts down fot the duration timer
    Thread t = new Thread(() -> {
        Platform.runLater(new Thread(() -> {
            if (!this.countDownDuration.isZero()) {
                this.countDownDuration = this.countDownDuration.minus(Duration.ofSeconds(1));
                this.timerLabel.setText(String.format("%s %s...", this.textBeforeTimer, this.countDownDuration.toSeconds()));
                if (!this.nodesToDisable.isEmpty())
                    this.nodesToDisable.forEach(node -> {
                        if (!node.isDisabled())
                            node.setDisable(true);
                    });
            } else {
                this.executor.shutdownNow();
                if (!this.nodesToDisable.isEmpty())
                    this.nodesToDisable.forEach(node -> {
                        if (node.isDisabled())
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
        String time = DataModel.getInstance().getSettingByType(SettingsType.WRONG_PASS_FREEZE_DURATION).getAttribute();
        Long l = Long.valueOf(time);

        setTimeoutDuration(Duration.ofSeconds(l));
    }

    /**
     * Get the field that shows how much time is left
     *
     * @return
     */
    public Label getTimerLabel() {
        return this.timerLabel;
    }

    /**
     * Starts a new scheduled executor with the given timer speed
     */
    public void startTimer() {
        if (this.executor != null && !this.executor.isShutdown())
            this.executor.shutdown();
        this.countDownDuration = this.timeoutDuration;
        this.executor = Executors.newSingleThreadScheduledExecutor();
        this.executor.scheduleWithFixedDelay(this.t, 0, 1, this.timerSpeed);
    }

    /**
     * adds a node to the nodes that gets disabled while the timer is going
     *
     * @param node
     */
    public void addNodeToDisable(Node node) {
        this.nodesToDisable.add(node);
    }

    public String getTextBeforeTimer() {
        return this.textBeforeTimer;
    }

    public void setTextBeforeTimer(String textBeforeTimer) {
        this.textBeforeTimer = textBeforeTimer;
    }

    public Duration getTimeoutDuration() {
        return this.timeoutDuration;
    }

    public void setTimeoutDuration(Duration timeoutDuration) {
        this.timeoutDuration = timeoutDuration;
        this.countDownDuration = timeoutDuration;
    }
}
