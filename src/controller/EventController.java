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

	public Eventdto createEvent(Eventdto eventdto) {
		Eventdto dto = eventService.createEvent(eventdto);
		if (dto == null) {
			LOG.error(Constants.EMPTY_EVENT_DTO);
			return null;
		}
		return dto;

	}

	public boolean updateEventController(Eventdto eventDTO, String newtitle, LocalDateTime newdateDebut,
			LocalDateTime newdateFin, String newTypeEvent) {
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

	public boolean linkUsersToEventController(int eventId, List<Integer> internalUsersIds, List<String> externalUsersEmails) {
		return eventService.linkUsersToEvent(eventId, internalUsersIds, externalUsersEmails);
	}
	
	public boolean updateParticipantsForEventController(int eventId, List<Integer> internalUsersToAdd,
			List<Integer> internalUsersToRemove, List<String> externalUsersToAdd, List<String> externalUsersToRemove) {
		return eventService.updateParticipantsForEvent(eventId, internalUsersToAdd, internalUsersToRemove, externalUsersToAdd, externalUsersToRemove);
	}

	public List<Integer> getInternalUsersForEvent(int eventId) {
		return eventService.getInternalUsersForEvent(eventId);
	}

	public List<String> getExternalUsersForEvent(int eventId) {
		return eventService.getExternalUsersForEvent(eventId);
	}

	

}
