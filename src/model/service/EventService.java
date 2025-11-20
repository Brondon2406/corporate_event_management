package model.service;

import java.util.List;

import model.dto.Eventdto;
import model.entity.enumeration.ParticipationUserToEvent;
import model.entity.enumeration.StatusEvents;

public interface EventService {

	public Eventdto createEvent(Eventdto eventdto);

	public boolean updateEvent(Eventdto event);

	public boolean deleteEvent(int eventId);

	public Eventdto getEventById(int id);

	public List<Eventdto> getAllEvents();

	public boolean linkUsersToEvent(int eventId, List<Integer> internalUsersIds, List<String> externalUsersEmails);

	public boolean updateParticipantsForEvent(int eventId, List<Integer> internalUsersToAdd,
			List<Integer> internalUsersToRemove, List<String> externalUsersToAdd, List<String> externalUsersToRemove);

	public List<String> getExternalUsersForEvent(int eventId);

	public List<Integer> getInternalUsersForEvent(int eventId);

	public boolean updateEventStatusService(Eventdto eventDTO, StatusEvents newStatus);

	public List<Eventdto> getEventsByStatusService(StatusEvents status);

	public boolean sendNotificationService(int eventId, String message);

	boolean updateUserStatusForEventService(int userId, int eventId, ParticipationUserToEvent newStatus);

	ParticipationUserToEvent getUserStatusForEventService(int userId, int eventId);

	List<Eventdto> getAssignedEventsService(int userId);

	void updateExpiredEvents();

}
