package model.service.scheduler;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class EventStatusScheduler {

    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(2);

	public void start(long expireIntervalSeconds, long releaseIntervalSeconds) {
        scheduler.scheduleAtFixedRate(
            new ExpireEventsTask(),
            0,
            expireIntervalSeconds,
            TimeUnit.MINUTES
        );

        scheduler.scheduleAtFixedRate(
            new ReleaseRoomsTask(),
            0,
            releaseIntervalSeconds,
            TimeUnit.MINUTES
        );

        System.out.println("EventStatusScheduler démarré : expiration et libération activées.");
    }

    public void stop() {
        scheduler.shutdown();
        System.out.println("EventStatusScheduler arrêté.");
    }
}
