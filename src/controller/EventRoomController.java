package controller;

import java.util.List;

import model.entity.EventRoom;
import model.service.EventRoomService;
import model.service.implementation.EventRoomServiceImpl;
import util.constants.Constants;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class EventRoomController {

    private static final Logger LOG = LogManager.getLogger(EventRoomController.class);

    private final EventRoomService roomService = new EventRoomServiceImpl();

    
    public EventRoom findRoomByIdAndName(int id, String name) {
        if (name == null || name.trim().isEmpty()) {
            LOG.error(Constants.EMPTY_ROOM_NAME);
            return null;
        }
        EventRoom room = roomService.findByIdAndName(id, name);
        if (room != null) {
            LOG.info("Salle trouvée : {}", room.getName());
        } else {
            LOG.warn(Constants.NO_ROOM_FOUND);
        }
        return room;
    }

    
    public List<EventRoom> getAllActiveRooms() {
        List<EventRoom> rooms = roomService.getAllActiveRooms();
        if (rooms.isEmpty()) {
            LOG.warn(Constants.NO_ACTIVE_ROOMS);
        } else {
            LOG.info("{} salles actives trouvées.", rooms.size());
        }
        return rooms;
    }

    
    public EventRoom findRoomById(int id) {
        EventRoom room = roomService.findByRoomById(id);
        if (room != null) {
            LOG.info("Salle trouvée avec ID {} : {}", id, room.getName());
        } else {
            LOG.warn(Constants.NO_ROOM_FOUND);
        }
        return room;
    }
}

