package model.service.implementation;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import model.database.DatabaseConnection;
import model.dto.EventRoomdto;
import model.dto.Eventdto;
import model.dto.Planningdto;
import model.service.EventService;
import model.service.sql.Query;
import util.constants.Constants;

public class EventServiceImpl implements EventService {

	private static final Logger LOG = LogManager.getLogger(EventServiceImpl.class);
	Connection connection = DatabaseConnection.getInstance();

	@Override
	public Eventdto createEvent(Eventdto events) {
		String query = Query.CREATE_EVENT;

		try {

			PreparedStatement ps = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
			ps.setString(1, events.getTitle());
			ps.setObject(2, events.getDateDebut());
			ps.setObject(3, events.getDateFin());
			ps.setString(4, events.getTypeEvent());
			ps.setString(5, events.getFormat());
			ps.setString(6, events.getModerator());
			ps.setString(7, events.getTutor());
			ps.setInt(8, events.getIdPlanning().getId());
			ps.setInt(9, events.getEventRoom().getId());
			ps.setString(10, events.getStatus());

			int result = ps.executeUpdate();
			if (result <= 0) {
				LOG.error(Constants.ERROR_DURING_EVENT_INSERTION);
				return null;
			}

			try (ResultSet rs = ps.getGeneratedKeys()) {
				if (rs.next()) {
					events.setId(rs.getInt(1));
				}
			}
			Eventdto eventdto = new Eventdto();
			eventdto.setId(events.getId());
			eventdto.setDateDebut(events.getDateDebut());
			eventdto.setDateFin(events.getDateFin());
			eventdto.setTypeEvent(events.getTypeEvent());
			eventdto.setFormat(events.getFormat());
			eventdto.setModerator(events.getModerator());
			eventdto.setTutor(events.getTutor());
			eventdto.setIdPlanning(events.getIdPlanning());
			eventdto.setEventRoom(events.getEventRoom());
			eventdto.setUsers(events.getUsers());
			eventdto.setStatus(events.getStatus());

			LOG.info("Événement créé avec succès : {}", eventdto.getTitle());
			return eventdto;

		} catch (SQLException e) {
			LOG.error(Constants.ERROR_CREATE_EVENT, e);
			return null;
		}
	}

	@Override
	public boolean updateEvent(Eventdto event) {
		String query = Query.UPDATE_EVENT;
		try {
			PreparedStatement ps = connection.prepareStatement(query);

			ps.setString(1, event.getTitle());
			ps.setObject(2, event.getDateDebut());
			ps.setObject(3, event.getDateFin());
			ps.setString(4, event.getTypeEvent());
			ps.setString(5, event.getFormat());
			ps.setString(6, event.getModerator());
			ps.setInt(8, event.getEventRoom().getId());

			int rows = ps.executeUpdate();
			return rows > 0;

		} catch (SQLException e) {
			LOG.error(Constants.ERROR_UPDATE_EVENT, e);
			return false;
		}
	}

	@Override
	public boolean deleteEvent(int eventId) {
		String query = Query.DELETE_EVENT;
		try (PreparedStatement ps = connection.prepareStatement(query)) {

			ps.setInt(1, eventId);
			int rows = ps.executeUpdate();

			if (rows > 0) {
				LOG.info("Événement avec ID {} supprimé avec succès", eventId);
				return true;
			} else {
				LOG.warn(Constants.NO_EVENT_FOUND);
				return false;
			}

		} catch (SQLException e) {
			LOG.error(Constants.ERROR_DELETE_EVENT, eventId, e);
			return false;
		}
	}

	@Override
	public Eventdto getEventById(int id) {
		String query = Query.GET_EVENT_BY_ID;
		Eventdto events = null;
		try (PreparedStatement ps = connection.prepareStatement(query)) {
			ps.setInt(1, id);

			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					Eventdto event = new Eventdto();
					event.setId(rs.getInt("id"));
					event.setTitle(rs.getString("title"));
					event.setDateDebut((LocalDateTime) rs.getObject("date_debut"));
					event.setDateFin((LocalDateTime) rs.getObject("date_fin"));
					event.setTypeEvent(rs.getString("type_event"));
					event.setFormat(rs.getString("format"));
					int eventRoomId = rs.getInt("room_id");
					EventRoomdto eventRoom = new EventRoomdto();
					eventRoom.setId(eventRoomId);
					event.setStatus(rs.getString("status"));
					event.setModerator(rs.getString("moderator"));
					event.setTutor(rs.getString("tutor"));
					int planningId = rs.getInt("id_planning");
					Planningdto idplanning = new Planningdto();
					idplanning.setId(planningId);

				}

			}

		} catch (SQLException e) {
			LOG.error(Constants.ERROR_GET_EVENT_BY_ID, e);
		}
		return events;
	}

	@Override
	public List<Eventdto> getAllEvents() {
		String query = Query.GET_ALL_EVENTS;
		List<Eventdto> eventdto = new ArrayList<>();

		try (PreparedStatement ps = connection.prepareStatement(query); ResultSet rs = ps.executeQuery()) {

			while (rs.next()) {
				Eventdto event = new Eventdto();
				event.setId(rs.getInt("id"));
				event.setTitle(rs.getString("title"));
				event.setDateDebut((LocalDateTime) rs.getObject("date_debut"));
				event.setDateFin((LocalDateTime) rs.getObject("date_fin"));
				event.setTypeEvent(rs.getString("type_event"));
				event.setFormat(rs.getString("format_event"));

				int eventRoomId = rs.getInt("room_id");
				EventRoomdto eventRoom = new EventRoomdto();
				eventRoom.setId(eventRoomId);
				event.setEventRoom(eventRoom);

				event.setStatus(rs.getString("status"));
				event.setModerator(rs.getString("moderator"));
				event.setTutor(rs.getString("tutor"));

				int planningId = rs.getInt("id_planning");
				Planningdto idPlanning = new Planningdto();
				idPlanning.setId(planningId);
				event.setIdPlanning(idPlanning);

				eventdto.add(event);
			}

		} catch (SQLException e) {
			LOG.error(Constants.ERROR_GET_ALL_EVENTS, e);
		}
		return eventdto;
	}

	@Override
	public boolean linkUsersToEvent(int eventId, List<Integer> internalUsersIds, List<String> externalUsersEmails) {
		String query = Query.LINK_EVENT_WITH_INTERNAL_USERS;

		
		try (PreparedStatement ps = connection.prepareStatement(query)) {
			for (Integer userId : internalUsersIds) {
				ps.setInt(1, eventId);
				ps.setInt(2, userId);
				ps.addBatch();
			}

			int[] results = ps.executeBatch();

			String queryExternal = Query.LINK_EVENT_WITH_EXTERNAL_USERS;
			try (PreparedStatement psExternal = connection.prepareStatement(queryExternal)) {
				for (String email : externalUsersEmails) {
					psExternal.setInt(1, eventId);
					psExternal.setString(2, email);
					psExternal.addBatch();
				}
				psExternal.executeBatch();
			}
			LOG.info("Participants liés à l'événement ID {} : {}", eventId, results.length);
			return true;

		} catch (SQLException e) {
			LOG.error("Erreur lors de l'insertion des participants pour l'événement ID " + eventId, e);
			return false;
		}
	}
	
	@Override
	public List<Integer> getInternalUsersForEvent(int eventId) {
	    List<Integer> internalUsers = new ArrayList<>();
	    String query = "SELECT user_id FROM event_users WHERE event_id = ?";

	    try (PreparedStatement ps = connection.prepareStatement(query)) {
	        ps.setInt(1, eventId);
	        try (ResultSet rs = ps.executeQuery()) {
	            while (rs.next()) {
	                internalUsers.add(rs.getInt("user_id"));
	            }
	        }
	    } catch (SQLException e) {
	        LOG.error("Erreur lors de la récupération des utilisateurs internes pour l'événement ID " + eventId, e);
	    }
	    return internalUsers;
	}

	@Override
	public List<String> getExternalUsersForEvent(int eventId) {
	    List<String> externalUsers = new ArrayList<>();
	    String query = "SELECT external_email FROM event_users WHERE event_id = ?";

	    try (PreparedStatement ps = connection.prepareStatement(query)) {
	        ps.setInt(1, eventId);
	        try (ResultSet rs = ps.executeQuery()) {
	            while (rs.next()) {
	                externalUsers.add(rs.getString("external_email"));
	            }
	        }
	    } catch (SQLException e) {
	        LOG.error("Erreur lors de la récupération des utilisateurs externes pour l'événement ID " + eventId, e);
	    }
	    return externalUsers;
	}


	@Override
	public boolean updateParticipantsForEvent(int eventId, List<Integer> internalUsersToAdd,
			List<Integer> internalUsersToRemove, List<String> externalUsersToAdd, List<String> externalUsersToRemove) {

		try {

			String addInternalQuery = Query.ADD_INTERNAL_USERS;
			try (PreparedStatement psAdd = connection.prepareStatement(addInternalQuery)) {
				for (Integer userId : internalUsersToAdd) {
					psAdd.setInt(1, eventId);
					psAdd.setInt(2, userId);
					psAdd.addBatch();
				}
				psAdd.executeBatch();
			}

			String removeInternalQuery = Query.REMOVE_INTERNAL_USERS;
			try (PreparedStatement psRemove = connection.prepareStatement(removeInternalQuery)) {
				for (Integer userId : internalUsersToRemove) {
					psRemove.setInt(1, eventId);
					psRemove.setInt(2, userId);
					psRemove.addBatch();
				}
				psRemove.executeBatch();
			}

			String addExternalQuery = Query.ADD_EXTERNAL_USERS;
			try (PreparedStatement psAddExt = connection.prepareStatement(addExternalQuery)) {
				for (String email : externalUsersToAdd) {
					psAddExt.setInt(1, eventId);
					psAddExt.setString(2, email);
					psAddExt.addBatch();
				}
				psAddExt.executeBatch();
			}

			String removeExternalQuery = Query.REMOVE_EXTERNAL_USERS;
			try (PreparedStatement psRemoveExt = connection.prepareStatement(removeExternalQuery)) {
				for (String email : externalUsersToRemove) {
					psRemoveExt.setInt(1, eventId);
					psRemoveExt.setString(2, email);
					psRemoveExt.addBatch();
				}
				psRemoveExt.executeBatch();
			}

			LOG.info("Mise à jour des participants pour l'événement ID {}", eventId);
			return true;

		} catch (SQLException e) {
			LOG.error("Erreur lors de la mise à jour des participants pour l'événement ID " + eventId, e);
			return false;
		}
	}

}
