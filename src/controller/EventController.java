package controller;

import java.time.LocalDateTime;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import model.dto.Eventdto;
import model.entity.EventRoom;
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

		Eventdto dto = eventService.createEvent(eventdto);

		if (dto != null) {
			LOG.info("Événement créé avec succès !");
			return true;
		} else {
			LOG.error(Constants.ERROR_DURING_EVENT_INSERTION);
			return false;
		}
	}

	public boolean updateEventController(Eventdto eventDTO,  String newtitle, LocalDateTime newdateDebut, LocalDateTime newdateFin, EventRoom neweventRoom) {
		eventDTO.setTitle(newtitle);
		eventDTO.setDateDebut(newdateDebut);
		eventDTO.setDateFin(newdateFin);
		eventDTO.setEventRoom(neweventRoom);		
		
		
		boolean success = eventService.updateEvent(eventDTO);

		if (success) {
			LOG.info("Événement  mis à jour avec succès !");
			return true;
		} else {
			LOG.error(Constants.ERROR_DURING_EVENT_SELECTION);
			return false;
		}

	}

	public Eventdto getEventByIdController(int id) {
		return eventService.getEventById(id);
	}

	public boolean deleteEvent(int eventId) {
		return eventService.deleteEvent(eventId);
	}

	public List<Eventdto> getAllEvents() {
		return eventService.getAllEvents();
	}
}
