package model.service;

import java.util.List;

import model.dto.Eventdto;

public interface EventService {

	public Eventdto createEvent(Eventdto event);

	public boolean updateEvent(Eventdto event);

	public boolean deleteEvent(int eventId);

	public Eventdto getEventById(int id);

	public List<Eventdto> getAllEvents();

}
