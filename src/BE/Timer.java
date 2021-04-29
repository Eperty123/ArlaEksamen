package sample;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.text.Text;

import java.net.URL;
import java.time.Duration;
import java.util.ResourceBundle;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Timer {
    private Node nodeToDisable;
    private String textBeforeTimer = "Time until you can try to log in:";
    private Duration timeoutDuration = Duration.ofSeconds(30);
    private TimeUnit timerSpeed = TimeUnit.SECONDS;
    private ScheduledExecutorService executor;
    private Text txt = new Text(String.format("%s %s...",textBeforeTimer,timeoutDuration.toSeconds()));
    Thread t = new Thread(() -> {
        Platform.runLater(new Thread(() -> {
            if (!timeoutDuration.isZero()) {
                timeoutDuration=timeoutDuration.minus(Duration.ofSeconds(1));
                txt.setText(String.format("%s %s...",textBeforeTimer,timeoutDuration.toSeconds()));
                if (nodeToDisable!=null && !nodeToDisable.isDisabled())
                    nodeToDisable.setDisable(true);
            } else {
                executor.shutdownNow();
                if (nodeToDisable!=null)
                nodeToDisable.setDisable(false);
            }
        }));
    });

    public Timer(){}

    public Text getTxt() {
        return txt;
    }

    public void startTimer() {
        if(executor!=null && !executor.isShutdown())
            executor.shutdown();
        executor = Executors.newSingleThreadScheduledExecutor();
        executor.scheduleWithFixedDelay(t, 0, 1, timerSpeed);
    }

    public Node getNodeToDisable() {
        return nodeToDisable;
    }

    public void setNodeToDisable(Node nodeToDisable) {
        this.nodeToDisable = nodeToDisable;
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
