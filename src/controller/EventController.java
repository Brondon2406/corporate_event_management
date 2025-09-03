package controller;

import java.time.LocalDateTime;
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



	public boolean createEvent(Eventdto event) {	
		if (event.getEventRoom() == null) {
			LOG.error(Constants.EMPTY_EVENT_DTO);
			return eventService.createEvent(event) != null;
		}
	   
			Eventdto dto = eventService.createEvent(event);

		if (dto != null) {
			LOG.info("Événement créé avec succès !");
			return true;
		} else {
			LOG.error(Constants.ERROR_DURING_EVENT_INSERTION);
			return false;
		}
	}
		

	public boolean updateEventController(Eventdto eventDTO,  String newtitle, LocalDateTime newdateDebut, LocalDateTime newdateFin, String newTypeEvent) {
		eventDTO.setTitle(newtitle);
		eventDTO.setDateDebut(newdateDebut);
		eventDTO.setDateFin(newdateFin);
		eventDTO.setTypeEvent(newTypeEvent);		
		
		
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
	
	public boolean createEventWithUsers(Eventdto event, List<Integer> participantIds) {
	    return eventService.createEventWithUsers(event, participantIds);
	}


}
