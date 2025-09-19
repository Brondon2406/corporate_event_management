package model.service.implementation;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.time.StopWatch;
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
		StopWatch watch = new StopWatch();
		watch.start();
		String query = Query.CREATE_EVENTROOM;

		try {
			PreparedStatement ps = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
			ps.setString(1, eventRoom.getName());
			ps.setInt(2, eventRoom.getCapacity());
			ps.setBoolean(3, true);

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

			EventRoomdto eventRoomdto = new EventRoomdto();
			eventRoomdto.setId(eventRoom.getId());
			eventRoomdto.setName(eventRoom.getName());
			eventRoomdto.setCapacity(eventRoom.getCapacity());
			eventRoomdto.setActive(true);

			LOG.info("Utilisateur créé avec succès : {}", eventRoom.getName());
			return eventRoomdto;

		} catch (SQLException e) {
			LOG.error(Constants.ERROR_CREATE_EVENTROOM, e);
			return null;
		} finally {
			watch.stop();
			LOG.info(" createEventRoom execution time [{}] : {} ms", eventRoom.getName(),
					watch.getTime(TimeUnit.MILLISECONDS));
		}
	}

	@Override
	public boolean updateEventRoom(EventRoomdto eventRoomdto) {
		StopWatch watch = new StopWatch();
		watch.start();
		String query = Query.UPDATE_EVENTROOM;
		try {
			PreparedStatement ps = connection.prepareStatement(query);

			ps.setString(1, eventRoomdto.getName());
			ps.setInt(2, eventRoomdto.getCapacity());
			ps.setBoolean(3, eventRoomdto.isActive());
			ps.setInt(4, eventRoomdto.getId());

			int rows = ps.executeUpdate();
			return rows > 0;
		} catch (SQLException e) {
			LOG.error(Constants.ERROR_UPDATE_EVENTROOM, e);
			return false;
		} finally {
			watch.stop();
			LOG.info("Execution time updateEventRoom [id={}] : {} ms", eventRoomdto.getId(),
					watch.getTime(TimeUnit.MILLISECONDS));
		}
	}

	@Override
	public boolean deleteEventRoom(int id) {
		StopWatch watch = new StopWatch();
		watch.start();
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
			LOG.error(Constants.ERROR_DELETE_EVENTROOM + " id=" + id, e);
			return false;
		} finally {
			watch.stop();
			LOG.info("Execution time deleteEventRoom [id={}] : {} ms", id, watch.getTime(TimeUnit.MILLISECONDS));
		}
	}

	@Override
	public EventRoomdto findByRoomById(int id) {
		StopWatch watch = new StopWatch();
		watch.start();
		String query = Query.GET_EVENTROOM_BY_ID;
		EventRoomdto eventRoom = null;

		try (PreparedStatement ps = connection.prepareStatement(query)) {
			ps.setInt(1, id);

			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					eventRoom = new EventRoomdto();
					eventRoom.setId(rs.getInt("id"));
					eventRoom.setName(rs.getString("name"));
					eventRoom.setCapacity(rs.getInt("capacity"));
					eventRoom.setActive(rs.getBoolean("active"));
					return eventRoom;
				}
			}
		} catch (SQLException e) {
			LOG.error(Constants.NO_ROOM_FOUND, e);
		} finally {
			watch.stop();
			LOG.info("Execution time findByRoomById [id={}] : {} ms", id, watch.getTime(TimeUnit.MILLISECONDS));
		}

		return eventRoom;
	}

	@Override
	public EventRoomdto findByIdAndName(int id, String name) {
		StopWatch watch = new StopWatch();
		watch.start();
		String query = Query.GET_EVENTROOM_BY_ID_AND_NAME;

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
			LOG.error(Constants.ERROR_DURING_EVENTROOM_SELECTION, e);
		} finally {
			watch.stop();
			LOG.info("Execution time findByIdAndName [id={}, name={}] : {} ms", id, name,
					watch.getTime(TimeUnit.MILLISECONDS));
		}

		return null;
	}

	@Override
	public List<EventRoomdto> getAllActiveRooms() {
		StopWatch watch = new StopWatch();
		watch.start();
		String query = Query.GET_ALL_ACTIVE_ROOMS;
		List<EventRoomdto> rooms = new ArrayList<>();

		try {
			PreparedStatement ps = connection.prepareStatement(query);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				EventRoomdto room = new EventRoomdto();
				room.setId(rs.getInt("id"));
				room.setName(rs.getString("name"));
				room.setCapacity(rs.getInt("capacity"));
				room.setActive(rs.getBoolean("active"));
				rooms.add(room);
			}

		} catch (SQLException e) {
			LOG.error(Constants.NO_ACTIVE_ROOMS, e);
		} finally {
			watch.stop();
			LOG.info("Execution time getAllActiveRooms : {} ms", watch.getTime(TimeUnit.MILLISECONDS));
		}

		return rooms;
	}

	@Override
	public List<EventRoomdto> getAllRooms() {
		StopWatch watch = new StopWatch();
		watch.start();
		String query = Query.GET_ALL_EVENTROOMS;
		List<EventRoomdto> rooms = new ArrayList<>();

		try {
			PreparedStatement ps = connection.prepareStatement(query);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				EventRoomdto room = new EventRoomdto();
				room.setId(rs.getInt("id"));
				room.setName(rs.getString("name"));
				room.setCapacity(rs.getInt("capacity"));
				room.setActive(rs.getBoolean("active"));
				rooms.add(room);
			}

		} catch (SQLException e) {
			LOG.error(Constants.ERROR_GET_ALL_ROOMS, e);
		} finally {
			watch.stop();
			LOG.info("Execution time getAllRooms : {} ms", watch.getTime(TimeUnit.MILLISECONDS));
		}

		return rooms;
	}

	@Override
	public EventRoomdto getRoomById(int roomId) {
		StopWatch watch = new StopWatch();
		watch.start();
		String query = Query.GET_EVENTROOM_BY_ID;
		EventRoomdto room = null;

		try (PreparedStatement ps = connection.prepareStatement(query)) {
			ps.setInt(1, roomId);

			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					room = new EventRoomdto();
					room.setId(rs.getInt("id"));
					room.setName(rs.getString("name"));
					room.setCapacity(rs.getInt("capacity"));
					room.setActive(rs.getBoolean("active"));
				}
			}

		} catch (SQLException e) {
			LOG.error(Constants.ERROR_GET_ROOM_BY_ID, e);
		} finally {
			watch.stop();
			LOG.info("Execution time getRoomById [id={}] : {} ms", roomId, watch.getTime(TimeUnit.MILLISECONDS));
		}

		return room;
	}

}
