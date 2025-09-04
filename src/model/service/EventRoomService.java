package model.service;

import java.util.List;

import model.dto.EventRoomdto;
import model.entity.EventRoom;


public interface EventRoomService {
	
	public EventRoomdto createEventRoom(EventRoom eventRoom);
	public boolean updateEventRoom ( EventRoomdto  EventRoomDTO);
	public boolean deleteEventRoom(int id);
	public EventRoomdto findByIdAndName(int id, String name);
	public List<EventRoomdto> getAllActiveRooms();
	public EventRoomdto findByRoomById(int id);
	
}