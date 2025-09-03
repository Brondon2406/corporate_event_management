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

    public EventRoom findRoomById(int id) {
        if (id <= 0) {
            LOG.error("ID de salle invalide : {}", id);
            return null;
        }

        EventRoom room = roomService.findByRoomById(id);

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
}
