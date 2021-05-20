package BE;

import com.jfoenix.controls.JFXTextField;
import javafx.application.Platform;

import java.time.LocalDateTime;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class Clock extends JFXTextField {
    private final LocalDateTime now = LocalDateTime.now();
    private final AtomicInteger hours = new AtomicInteger(now.getHour());;
    private final AtomicInteger minutes = new AtomicInteger(now.getMinute());;
    private final AtomicInteger seconds = new AtomicInteger(now.getSecond()-1);

    public Clock() {
        super();
        ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
        executor.scheduleAtFixedRate(new Thread(() -> {
            if (seconds.incrementAndGet() % 60 == 0) {
                seconds.set(0);
                if (minutes.incrementAndGet() % 60 == 0) {
                    minutes.set(0);
                    if (hours.incrementAndGet() % 24 == 0) {
                        hours.set(0);
                    }
                }
            }
            Platform.runLater(new Thread(()->super.setText(String.format("%02d:%02d:%02d",hours.get(),minutes.get(),seconds.get()))));
        }), 0, 1, TimeUnit.SECONDS);
    }
}
