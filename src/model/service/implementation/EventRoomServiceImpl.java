package model.service.implementation;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import model.database.DatabaseConnection;
import model.dto.EventRoomdto;
import model.entity.EventRoom;
import model.service.EventRoomService;
import model.service.sql.Query;
import util.constants.Constants;

public class EventRoomServiceImpl implements EventRoomService {

	private static final Logger LOG = LogManager.getLogger(EventRoomServiceImpl.class);
	private final Connection connection = DatabaseConnection.getInstance();

	@Override
	public EventRoomdto createEventRoom(EventRoom eventRoom) {
		String query = Query.CREATE_EVENTROOM;

		try  {
			PreparedStatement ps = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
			ps.setString(1, eventRoom.getName());
			ps.setInt(2, eventRoom.getCapacity());
			ps.setBoolean(3, eventRoom.isActive());
			ps.setBoolean(4, true);

			int result = ps.executeUpdate();
			if (result <= 0) {
				LOG.error(Constants.ERROR_DURING_EVENTROOM_INSERTION);
				return null;
			}

			try (ResultSet rs = ps.getGeneratedKeys()) {
				if (rs.next()) {
					eventRoom.setId(rs.getInt(1));
				}
			}

			EventRoomdto eventRoomDTO = new EventRoomdto();
			eventRoomDTO.setId(eventRoom.getId());
			eventRoomDTO.setName(eventRoom.getName());
			eventRoomDTO.setCapacity(eventRoom.getCapacity());
			eventRoomDTO.setActive(true);
			
			LOG.info("Utilisateur créé avec succès : {}", eventRoom.getName());
			return eventRoomDTO;

		} catch (SQLException e) {
			LOG.error(Constants.ERROR_CREATE_EVENTROOM +"EventRoom= "+ eventRoom, e);
			return null;
		}
	}

	@Override
	public boolean updateEventRoom(EventRoomdto EventRoomDTO) {
		String query = Query.UPDATE_EVENTROOM;
		try {
			PreparedStatement ps = connection.prepareStatement(query);

			ps.setString(1, EventRoomDTO.getName());
			ps.setInt(2, EventRoomDTO.getId());
			ps.setInt(3, EventRoomDTO.getCapacity());

			int rows = ps.executeUpdate();
			return rows > 0;
		} catch (SQLException e) {
			LOG.error(Constants.ERROR_UPDATE_EVENTROOM + " EventRoomDTO=" + EventRoomDTO, e);
			return false;
		}
	}


	@Override
	public boolean deleteEventRoom(int id) {
		String query = Query.DELETE_EVENTROOM;
		try {
			PreparedStatement ps = connection.prepareStatement(query);
			ps.setInt(1, id);
			int rows = ps.executeUpdate();

			if (rows > 0) {
				LOG.info("Utilisateur avec ID {} supprimé avec succès", id);
				return true;
			} else {
				LOG.warn(Constants.NO_PLANNING_FOUND, id);
				return false;
			}
		} catch (SQLException e) {
			LOG.error(Constants.ERROR_DELETE_EVENTROOM+ " id=" + id, e);
			return false;
		}
	}

	@Override
	public EventRoomdto findByRoomById(int id) {
		String query = Query.SELECT_EVENTROOM_BY_ID;
		EventRoomdto eventRoom = null;

		try (PreparedStatement ps = connection.prepareStatement(query)) {
			ps.setInt(1, id);

			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					eventRoom  = new EventRoomdto();
					eventRoom.setId(rs.getInt("id"));
					eventRoom.setName(rs.getString("name"));
					eventRoom.setCapacity(rs.getInt("capacity"));
					eventRoom.setActive(rs.getBoolean("active"));
					return eventRoom;
				}
			}
		} catch (SQLException e) {
			LOG.error(Constants.NO_ROOM_FOUND + " ID= " + e.getMessage(), e);
		}

		return eventRoom;
	}

	@Override
	public EventRoomdto findByIdAndName(int id, String name) {
		String query = Query.SELECT_EVENTROOM_BY_ID_AND_NAME;

		try (PreparedStatement ps = connection.prepareStatement(query)) {
			ps.setInt(1, id);

			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					EventRoomdto room = new EventRoomdto();
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
	public List<EventRoomdto> getAllActiveRooms() {
		String query = Query.SELECT_ALL_ACTIVE_ROOMS;
		List<EventRoomdto> rooms = new ArrayList<>();

		try (PreparedStatement ps = connection.prepareStatement(query); ResultSet rs = ps.executeQuery()) {

			while (rs.next()) {
				EventRoom room = new EventRoom();
				room.setId(rs.getInt("id"));
				room.setName(rs.getString("name"));
				room.setCapacity(rs.getInt("capacity"));
				room.setActive(rs.getBoolean("active"));
			}

		} catch (SQLException e) {
			LOG.error(Constants.NO_ACTIVE_ROOMS + " " + e.getMessage(), e);
		}

		return rooms;
	}

}
