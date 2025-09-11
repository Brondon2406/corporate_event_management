package model.service;

import java.util.List;

import model.dto.Eventdto;

public interface EventService {

	public Eventdto createEvent(Eventdto eventdto);

	public boolean updateEvent(Eventdto event);

	public boolean deleteEvent(int eventId);

	public Eventdto getEventById(int id);

	public List<Eventdto> getAllEvents();

	boolean linkUsersToEvent(int eventId, List<Integer> internalUsersIds, List<String> externalUsersEmails);

	boolean updateParticipantsForEvent(int eventId, List<Integer> internalUsersToAdd,
			List<Integer> internalUsersToRemove, List<String> externalUsersToAdd, List<String> externalUsersToRemove);

	List<String> getExternalUsersForEvent(int eventId);

	List<Integer> getInternalUsersForEvent(int eventId);




}
