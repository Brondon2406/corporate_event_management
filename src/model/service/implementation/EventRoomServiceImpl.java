package model.service.implementation;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import model.database.DatabaseConnection;
import model.entity.EventRoom;
import model.service.EventRoomService;
import model.service.sql.Query;
import util.constants.Constants;

public class EventRoomServiceImpl implements EventRoomService {

    private static final Logger LOG = LogManager.getLogger(EventRoomServiceImpl.class);
    private final Connection connection = DatabaseConnection.getInstance();

    @Override
    public EventRoom findByRoomById(int id) {
        String query = Query.SELECT_EVENTROOM_BY_ID;

        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    EventRoom room = new EventRoom();
                    room.setId(rs.getInt("id"));
                    room.setName(rs.getString("name"));
                    room.setCapacity(rs.getInt("capacity"));
                    room.setActive(rs.getBoolean("active"));
                    return room; 
                }
            }
        } catch (SQLException e) {
            LOG.error(Constants.NO_ROOM_FOUND + " " + e.getMessage(), e);
        }

        return null; // si salle non trouvée
    }

    @Override
    public EventRoom findByIdAndName(int id, String name) {
        String query = Query.SELECT_EVENTROOM_BY_ID_AND_NAME;

        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    EventRoom room = new EventRoom();
                    room.setId(rs.getInt("id"));
                    room.setName(rs.getString("name"));
                    room.setCapacity(rs.getInt("capacity"));
                    room.setActive(rs.getBoolean("active"));
                    return room;
                }
            }
        } catch (SQLException e) {
            LOG.error("Erreur SQL lors de la récupération de la salle", e);
        }

        return null;
    }

    @Override
    public List<EventRoom> getAllActiveRooms() {
        String query = Query.SELECT_ALL_ACTIVE_ROOMS;
        List<EventRoom> rooms = new ArrayList<>();

        try (PreparedStatement ps = connection.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                EventRoom room = new EventRoom();
                room.setId(rs.getInt("id"));
                room.setName(rs.getString("name"));
                room.setCapacity(rs.getInt("capacity"));
                room.setActive(rs.getBoolean("active"));
                rooms.add(room);
            }

        } catch (SQLException e) {
            LOG.error(Constants.NO_ACTIVE_ROOMS + " " + e.getMessage(), e);
        }

        return rooms;
    }
}
