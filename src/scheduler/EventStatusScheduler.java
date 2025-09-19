package scheduler;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import model.service.EventService;

/**
 * Planifie la vérification et la mise à jour du statut des événements.
 * Les événements expirés (date_fin < NOW) passent automatiquement en statut EXPIRÉ.
 */
public class EventStatusScheduler {

    /** Scheduler avec un seul thread. */
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    /** Référence vers le service des événements. */
    private final EventService eventService;

    /**
     * Constructeur : démarre automatiquement le scheduler.
     *
     * @param eventService service responsable de la gestion des événements
     */
    public EventStatusScheduler(EventService eventService) {
        this.eventService = eventService;
        startScheduler();
    }

    /**
     * Démarre le scheduler qui vérifie toutes les 30 secondes les événements expirés.
     */
    private void startScheduler() {
        scheduler.scheduleAtFixedRate(() -> {
            try {
                eventService.updateExpiredEvents();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }, 0, 30, TimeUnit.SECONDS); 
    }

    /**
     * Arrête proprement le scheduler.
     */
    public void stopScheduler() {
        scheduler.shutdown();
    }
}
