package controller;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import model.dto.Eventdto;
import model.entity.enumeration.ParticipationUserToEvent;
import model.entity.enumeration.StatusEvents;
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

	public boolean updateEventStatusController(Eventdto eventDTO, StatusEvents newStatus) {
		if (eventDTO == null || newStatus == null) {
			return false;
		}
		return eventService.updateEventStatusService(eventDTO, newStatus);
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

	public boolean linkUsersToEventController(int eventId, List<Integer> internalUsersIds,
			List<String> externalUsersEmails) {
		return eventService.linkUsersToEvent(eventId, internalUsersIds, externalUsersEmails);
	}

	public boolean updateParticipantsForEventController(int eventId, List<Integer> internalUsersToAdd,
			List<Integer> internalUsersToRemove, List<String> externalUsersToAdd, List<String> externalUsersToRemove) {
		return eventService.updateParticipantsForEvent(eventId, internalUsersToAdd, internalUsersToRemove,
				externalUsersToAdd, externalUsersToRemove);
	}

	public List<Integer> getInternalUsersForEvent(int eventId) {
		return eventService.getInternalUsersForEvent(eventId);
	}

	public List<String> getExternalUsersForEvent(int eventId) {
		return eventService.getExternalUsersForEvent(eventId);
	}

	public List<Eventdto> getEventsByStatusController(StatusEvents status) {
		if (status == null) {
			return new ArrayList<>();
		}
		return eventService.getEventsByStatusService(status);
	}
	
	public boolean sendNotificationController(int eventId, String message) {
	    return eventService.sendNotificationService(eventId, message);
	}

	
	public List<Eventdto> getAssignedEventsController(int userId) {
	    if (userId <= 0) return new ArrayList<>();
	    return eventService.getAssignedEventsService(userId);
	}

	public ParticipationUserToEvent getUserStatusForEventController(int userId, int eventId) {
	    return eventService.getUserStatusForEventService(userId, eventId);
	}
	
	public boolean updateUserStatusForEventController(int userId, int eventId, ParticipationUserToEvent newStatus) {
	    return eventService.updateUserStatusForEventService(userId, eventId, newStatus);
	}
	
	public void checkAndExpireEvents() {
	    eventService.updateExpiredEvents();
	}


	
}
