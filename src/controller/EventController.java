package controller;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import model.dto.Eventdto;

import model.service.EventService;
import model.service.implementation.EventServiceImpl;
import util.constants.Constants;

public class EventController {
	private static final Logger LOG = LogManager.getLogger(EventController.class);
	private EventService eventService = new EventServiceImpl();

	public boolean EventCreatController(Eventdto eventdto) {
		if (eventdto == null) {
			LOG.error(Constants.EMPTY_EVENT_DTO);
			return false;
		}

		Eventdto dto = eventService.registerEvent(eventdto);

		if (dto != null) {
			LOG.info("Événement créé avec succès !");
			return true;
		} else {
			LOG.error(Constants.ERROR_DURING_EVENT_INSERTION);
			return false;
		}
	}

	public boolean updateEventController(Eventdto event) {
		if (event == null) {
			LOG.error(Constants.EMPTY_EVENT_DTO);
			return false;
		}
		boolean success = eventService.updateEvent(event);

		if (success) {
			LOG.info("Événement créé avec succès !");
			return true;
		} else {
			LOG.error(Constants.ERROR_DURING_EVENT_SELECTION);
			return false;
		}

	}

	public boolean deleteEvent(int eventId) {
		return eventService.deleteEvent(eventId);
	}

	public List<Eventdto> getAllEvents() {
		return eventService.getAllEvents();
	}
}
