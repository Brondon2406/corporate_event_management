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

/**
 * Controller class responsible for handling operations related to events.
 * 
 * This class serves as the bridge between the view layer and the service layer,
 * exposing methods to create, update, delete, and retrieve events as well as
 * manage participants and event statuses.
 *
 * @author Severin Kengne 
 * @version 1.0
 */

public class EventController {
	private static final Logger LOG = LogManager.getLogger(EventController.class);
	private EventService eventService = new EventServiceImpl();
	
	/**
     * Creates a new event.
     *
     * @param eventdto The {@link Eventdto} containing event details.
     * @return The created {@link Eventdto}, or {@code null} if creation failed.
     */
	public Eventdto createEvent(Eventdto eventdto) {
		Eventdto dto = eventService.createEvent(eventdto);
		if (dto == null) {
			LOG.error(Constants.EMPTY_EVENT_DTO);
			return null;
		}
		return dto;

	}
	
	  /**
     * Updates the main details of an existing event.
     *
     * @param eventDTO    The event to update.
     * @param newtitle    The new title.
     * @param newdateDebut The new start date and time.
     * @param newdateFin   The new end date and time.
     * @param newTypeEvent The new type of event.
     * @return {@code true} if the update succeeded, otherwise {@code false}.
     */

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
	
	 /**
     * Updates the status of an event.
     *
     * @param eventDTO  The event to update.
     * @param newStatus The new status.
     * @return {@code true} if the update succeeded, otherwise {@code false}.
     */	
	public boolean updateEventStatusController(Eventdto eventDTO, StatusEvents newStatus) {
		if (eventDTO == null || newStatus == null) {
			return false;
		}
		return eventService.updateEventStatusService(eventDTO, newStatus);
	}
	
	  /**
     * Retrieves an event by its ID.
     *
     * @param id The event ID.
     * @return The {@link Eventdto} corresponding to the event, or {@code null} if not found.
     */
	public Eventdto getEventByIdController(int id) {
		return eventService.getEventById(id);
	}
	
	/**
     * Deletes an event by its ID.
     *
     * @param eventId The ID of the event to delete.
     * @return {@code true} if the deletion succeeded, otherwise {@code false}.
     */
	public boolean deleteEvent(int eventId) {
		return eventService.deleteEvent(eventId);
	}
	
	/**
     * Retrieves all events.
     *
     * @return A list of all {@link Eventdto}.
     */
	public List<Eventdto> getAllEvents() {
		return eventService.getAllEvents();
	}
	
	/**
     * Links users (internal and external) to an event.
     *
     * @param eventId            The event ID.
     * @param internalUsersIds   List of internal user IDs.
     * @param externalUsersEmails List of external user emails.
     * @return {@code true} if linking succeeded, otherwise {@code false}.
     */
	public boolean linkUsersToEventController(int eventId, List<Integer> internalUsersIds,
			List<String> externalUsersEmails) {
		return eventService.linkUsersToEvent(eventId, internalUsersIds, externalUsersEmails);
	}
	
	/**
     * Updates participants for an event (adding/removing internal and external users).
     *
     * @param eventId             The event ID.
     * @param internalUsersToAdd  List of internal user IDs to add.
     * @param internalUsersToRemove List of internal user IDs to remove.
     * @param externalUsersToAdd  List of external user emails to add.
     * @param externalUsersToRemove List of external user emails to remove.
     * @return {@code true} if update succeeded, otherwise {@code false}.
     */
	public boolean updateParticipantsForEventController(int eventId, List<Integer> internalUsersToAdd,
			List<Integer> internalUsersToRemove, List<String> externalUsersToAdd, List<String> externalUsersToRemove) {
		return eventService.updateParticipantsForEvent(eventId, internalUsersToAdd, internalUsersToRemove,
				externalUsersToAdd, externalUsersToRemove);
	}
	
	/**
     * Retrieves the list of internal users participating in an event.
     *
     * @param eventId The event ID.
     * @return A list of internal user IDs.
     */
	public List<Integer> getInternalUsersForEvent(int eventId) {
		return eventService.getInternalUsersForEvent(eventId);
	}
	
	/**
     * Retrieves the list of external users participating in an event.
     *
     * @param eventId The event ID.
     * @return A list of external user emails.
     */
	public List<String> getExternalUsersForEvent(int eventId) {
		return eventService.getExternalUsersForEvent(eventId);
	}
	

    /**
     * Retrieves events by their status.
     *
     * @param status The status filter.
     * @return A list of {@link Eventdto} matching the status, or an empty list if status is null.
     */
	public List<Eventdto> getEventsByStatusController(StatusEvents status) {
		if (status == null) {
			return new ArrayList<>();
		}
		return eventService.getEventsByStatusService(status);
	}
	
	/**
     * Sends a notification related to an event.
     *
     * @param eventId The event ID.
     * @param message The message to send.
     * @return {@code true} if sending succeeded, otherwise {@code false}.
     */
	public boolean sendNotificationController(int eventId, String message) {
		return eventService.sendNotificationService(eventId, message);
	}
	

	/**
     * Retrieves events assigned to a specific user.
     *
     * @param userId The user ID.
     * @return A list of {@link Eventdto} assigned to the user.
     */
	public List<Eventdto> getAssignedEventsController(int userId) {
		if (userId <= 0)
			return new ArrayList<>();
		return eventService.getAssignedEventsService(userId);
	}
	
	/**
     * Retrieves the participation status of a user for a given event.
     *
     * @param userId  The user ID.
     * @param eventId The event ID.
     * @return The participation status, or {@code null} if not found.
     */
	public ParticipationUserToEvent getUserStatusForEventController(int userId, int eventId) {
		return eventService.getUserStatusForEventService(userId, eventId);
	}

	/**
     * Updates the participation status of a user for an event.
     *
     * @param userId    The user ID.
     * @param eventId   The event ID.
     * @param newStatus The new participation status.
     * @return {@code true} if the update succeeded, otherwise {@code false}.
     */
	public boolean updateUserStatusForEventController(int userId, int eventId, ParticipationUserToEvent newStatus) {
		return eventService.updateUserStatusForEventService(userId, eventId, newStatus);
	}

	public void checkAndExpireEvents() {
		eventService.updateExpiredEvents();
	}

}
