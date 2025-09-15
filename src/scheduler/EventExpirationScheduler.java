package scheduler;

import controller.EventController;

public class EventExpirationScheduler extends Thread {

	    private final EventController eventController;

	    public EventExpirationScheduler(EventController eventController) {
	        this.eventController = eventController;
	    }

	    @Override
	    public void run() {
	        while (true) {
	            try {
	                eventController.checkAndExpireEvents();

	                Thread.sleep(60 * 1000);

	            } catch (Exception e) {
	                e.printStackTrace();
	            }
	        }
	    }
	}
