package model.service;

import java.util.List;

import model.dto.Eventdto;

public interface EventService {
	public Eventdto registerEvent(Eventdto event);

	public boolean deleteEvent(int eventId);

	public boolean updateEvent(Eventdto event);

	public List<Eventdto> getAllEvents();

}
