package controller;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import model.dto.EventRoomdto;
import model.entity.EventRoom;
import model.mapping.MappingService.MappingEventRoom;
import model.mapping.MappingServiceImpl.MappingEventRoomImpl;
import model.service.EventRoomService;
import model.service.implementation.EventRoomServiceImpl;
import util.constants.Constants;

public class EventRoomController {

	private static final Logger LOG = LogManager.getLogger(EventRoomController.class);

	private static EventRoomService roomService = new EventRoomServiceImpl();
	private MappingEventRoom mapper = new MappingEventRoomImpl();

	public boolean createRoomController(EventRoomdto eventRoomdto) {
		if (eventRoomdto == null) {
			LOG.error(Constants.EMPTY_ROOM_DTO);
			return false;
		}

		EventRoom room = mapper.convertEventRoomdtoToEventRoom(eventRoomdto);
		EventRoomdto dto = roomService.createEventRoom(room);

		if (dto != null) {
			LOG.info("Salle créée avec succès !");
			return true;
		} else {
			LOG.error(Constants.ERROR_DURING_ROOM_INSERTION);
			return false;
		}
	}

	public boolean updateRoomController(EventRoomdto eventRoomdto, String newName, int newCapacity, boolean newActive) {
		if (eventRoomdto == null) {
			LOG.error(Constants.EMPTY_ROOM_DTO);
			return false;
		}
		eventRoomdto.setName(newName);
		eventRoomdto.setCapacity(newCapacity);
		eventRoomdto.setActive(newActive);

		boolean success = roomService.updateEventRoom(eventRoomdto);
		if (success) {
			LOG.info("Salle mise à jour avec succès !");
		} else {
			LOG.error(Constants.ERROR_UPDATE_ROOM);
		}
		return success;
	}

	public boolean deleteRoomController(int roomId) {
		boolean success = roomService.deleteEventRoom(roomId);
		if (success) {
			LOG.info("Salle supprimée avec succès !");
		} else {
			LOG.error(Constants.ERROR_DELETE_ROOM + " ID=" + roomId);
		}
		return success;
	}

	public EventRoomdto getRoomByIdController(int roomId) {
		EventRoomdto dto = roomService.getRoomById(roomId);
		if (dto == null) {
			LOG.error(Constants.ERROR_GET_ROOM_BY_ID + " ID=" + roomId);
		}
		return dto;
	}

	public List<EventRoomdto> getAllRoomsController() {
		return roomService.getAllRooms();
	}

	public EventRoomdto findRoomById(int id) {
		if (id <= 0) {
			LOG.error("ID de salle invalide : {}", id);
			return null;
		}

		EventRoomdto room = roomService.findByRoomById(id);

		if (room != null) {
			LOG.info("Salle trouvée : {}", room.getName());
		} else {
			LOG.warn(Constants.NO_ROOM_FOUND);
		}

		return room;
	}

	public List<EventRoomdto> getAllActiveRooms() {
		List<EventRoomdto> rooms = roomService.getAllActiveRooms();

		if (rooms.isEmpty()) {
			LOG.warn(Constants.NO_ACTIVE_ROOMS);
		} else {
			LOG.info("{} salles actives trouvées.", rooms.size());
		}

		return rooms;
	}

}
