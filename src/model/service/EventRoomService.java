package model.service;

import java.util.List;

import model.entity.EventRoom;

public interface EventRoomService {

	public EventRoom findByIdAndName(int id, String name);
	public List<EventRoom> getAllActiveRooms();
	public EventRoom findByRoomById(int id);
	
}