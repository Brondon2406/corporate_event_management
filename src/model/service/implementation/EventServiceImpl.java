package model.service.implementation;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.time.StopWatch;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import model.database.DatabaseConnection;
import model.dto.EventRoomdto;
import model.dto.Eventdto;
import model.dto.Planningdto;
import model.entity.enumeration.ParticipationUserToEvent;
import model.entity.enumeration.StatusEvents;
import model.service.EventService;
import model.service.sql.Query;
import util.constants.Constants;

public class EventServiceImpl implements EventService {

	private static final Logger LOG = LogManager.getLogger(EventServiceImpl.class);
	Connection connection = DatabaseConnection.getInstance();

	@Override
	public Eventdto createEvent(Eventdto events) {
		StopWatch watch = new StopWatch();
		watch.start();
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
		} finally {
			watch.stop();
			LOG.info("Time to execute createEvent: {} ms", watch.getTime(TimeUnit.MILLISECONDS));
		}
	}

	@Override
	public boolean updateEvent(Eventdto event) {
		StopWatch watch = new StopWatch();
		watch.start();
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
		} finally {
			watch.stop();
			LOG.info("Time to execute updateEvent: {} ms", watch.getTime(TimeUnit.MILLISECONDS));
		}
	}

	@Override
	public boolean deleteEvent(int eventId) {
		StopWatch watch = new StopWatch();
		watch.start();
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
		} finally {
			watch.stop();
			LOG.info("Time to execute deleteEvent: {} ms", watch.getTime(TimeUnit.MILLISECONDS));
		}
	}

	@Override
	public Eventdto getEventById(int id) {
		StopWatch watch = new StopWatch();
		watch.start();
		String query = Query.GET_EVENT_BY_ID;
		Eventdto event = null;

		try (PreparedStatement ps = connection.prepareStatement(query)) {
			ps.setInt(1, id);

			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					event = new Eventdto();
					event.setId(rs.getInt("id"));
					event.setTitle(rs.getString("title"));
					event.setDateDebut(rs.getTimestamp("date_debut").toLocalDateTime());
					event.setDateFin(rs.getTimestamp("date_fin").toLocalDateTime());
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
					Planningdto idplanning = new Planningdto();
					idplanning.setId(planningId);

				}
			}
		} catch (SQLException e) {
			LOG.error(Constants.ERROR_GET_EVENT_BY_ID, e);
		} finally {
			watch.stop();
			LOG.info("Time to execute getEventById: {} ms", watch.getTime(TimeUnit.MILLISECONDS));
		}

		return event;
	}

	@Override
	public List<Eventdto> getAllEvents() {
		StopWatch watch = new StopWatch();
		watch.start();
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
		} finally {
			watch.stop();
			LOG.info("Time to execute getAllEvents: {} ms", watch.getTime(TimeUnit.MILLISECONDS));
		}
		return eventdto;
	}

	@Override
	public boolean linkUsersToEvent(int eventId, List<Integer> internalUsersIds, List<String> externalUsersEmails) {
		StopWatch watch = new StopWatch();
		watch.start();
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
			LOG.error(Constants.ERROR_DURING_USERS_INSERTION_EVENT, eventId, e);
			return false;
		} finally {
			watch.stop();
			LOG.info("Time to execute linkUsersToEvent: {} ms", watch.getTime(TimeUnit.MILLISECONDS));
		}
	}

	@Override
	public List<Integer> getInternalUsersForEvent(int eventId) {
		StopWatch watch = new StopWatch();
		watch.start();
		List<Integer> internalUsers = new ArrayList<>();
		String query = Query.GET_INTERNALUSERS_FOR_EVENT;

		try (PreparedStatement ps = connection.prepareStatement(query)) {
			ps.setInt(1, eventId);
			try (ResultSet rs = ps.executeQuery()) {
				while (rs.next()) {
					internalUsers.add(rs.getInt("user_id"));
				}
			}
		} catch (SQLException e) {
			LOG.error(Constants.ERROR_DURING_GET_INTERNALUSERS_FOR_EVENT, eventId, e);
		} finally {
			watch.stop();
			LOG.info("Time to execute getInternalUsersForEvent: {} ms", watch.getTime(TimeUnit.MILLISECONDS));
		}
		return internalUsers;
	}

	@Override
	public List<String> getExternalUsersForEvent(int eventId) {
		StopWatch watch = new StopWatch();
		watch.start();
		List<String> externalUsers = new ArrayList<>();
		String query = Query.GET_EXTERNALUSERS_FOR_EVENT;

		try (PreparedStatement ps = connection.prepareStatement(query)) {
			ps.setInt(1, eventId);
			try (ResultSet rs = ps.executeQuery()) {
				while (rs.next()) {
					externalUsers.add(rs.getString("external_email"));
				}
			}
		} catch (SQLException e) {
			LOG.error(Constants.ERROR_DURING_GET_EXTERNALUSERS_FOR_EVENT, eventId, e);
		} finally {
			watch.stop();
			LOG.info("Time to execute getExternalUsersForEvent: {} ms", watch.getTime(TimeUnit.MILLISECONDS));
		}
		return externalUsers;
	}

	@Override
	public boolean updateParticipantsForEvent(int eventId, List<Integer> internalUsersToAdd,
			List<Integer> internalUsersToRemove, List<String> externalUsersToAdd, List<String> externalUsersToRemove) {
		StopWatch watch = new StopWatch();
		watch.start();

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
			LOG.error(Constants.ERROR_DURING_UPDATE_USERS_OF_EVENT, eventId, e);
			return false;
		} finally {
			watch.stop();
			LOG.info("Time to execute updateParticipantsForEvent: {} ms", watch.getTime(TimeUnit.MILLISECONDS));
		}
	}

	@Override
	public boolean updateEventStatusService(Eventdto eventDTO, StatusEvents newStatus) {
		StopWatch watch = new StopWatch();
		watch.start();
		if (eventDTO == null || newStatus == null) {
			return false;
		}

		String query = Query.UPDATE_EVENTS_STATUS;
		try (PreparedStatement ps = connection.prepareStatement(query)) {

			ps.setString(1, newStatus.name());
			ps.setInt(2, eventDTO.getId());

			int rowsUpdated = ps.executeUpdate();
			if (rowsUpdated > 0) {
				return true;
			}

		} catch (SQLException e) {
			LOG.error(Constants.ERROR_UPDATE_STATUS_OF_EVENT, eventDTO.getId(), e);
		} finally {
			watch.stop();
			LOG.info("Time to execute updateEventStatusService: {} ms", watch.getTime(TimeUnit.MILLISECONDS));
		}

		return false;
	}

	@Override
	public List<Eventdto> getEventsByStatusService(StatusEvents status) {
		StopWatch watch = new StopWatch();
		watch.start();
		List<Eventdto> events = new ArrayList<>();
		String query = Query.GET_EVENTS_BY_STATUS;

		try (PreparedStatement ps = connection.prepareStatement(query)) {
			ps.setString(1, status.name());
			try (ResultSet rs = ps.executeQuery()) {
				while (rs.next()) {
					Eventdto event = new Eventdto();
					event.setId(rs.getInt("id"));
					event.setTitle(rs.getString("title"));
					event.setDateDebut((LocalDateTime) rs.getObject("date_debut"));
					event.setDateFin((LocalDateTime) rs.getObject("date_fin"));
					event.setDateFin((LocalDateTime) rs.getObject("date_fin"));
					event.setFormat(rs.getString("format_event"));
					int eventRoomId = rs.getInt("room_id");
					EventRoomdto eventRoom = new EventRoomdto();
					eventRoom.setId(eventRoomId);
					event.setStatus(rs.getString("status"));
					event.setModerator(rs.getString("moderator"));
					event.setTutor(rs.getString("tutor"));
					int planningId = rs.getInt("id_planning");
					Planningdto idplanning = new Planningdto();
					idplanning.setId(planningId);
					events.add(event);
				}

			}
		} catch (SQLException e) {
			LOG.error(Constants.ERROR_UPDATE_EVENT_WITH_STATUS_, status, e);
		} finally {
			watch.stop();
			LOG.info("Time to execute getEventsByStatusService: {} ms", watch.getTime(TimeUnit.MILLISECONDS));
		}

		return events;
	}

	@Override
	public boolean sendNotificationService(int eventId, String message) {
		StopWatch watch = new StopWatch();
		watch.start();
		List<String> recipients = new ArrayList<>();

		String queryInternal = Query.GET_ALL_EMAIL_TO_USER_INTERNEL;
		try (PreparedStatement ps = connection.prepareStatement(queryInternal)) {
			ps.setInt(1, eventId);
			try (ResultSet rs = ps.executeQuery()) {
				while (rs.next()) {
					recipients.add(rs.getString("email"));
				}
			}
		} catch (SQLException e) {
			LOG.error(Constants.ERROR_DURING_GET_INTERNALUSERS_FOR_EVENT, eventId, e);
			return false;
		}

		String queryExternal = Query.GET_ALL_EMAIL_TO_USER_EXTERNEL;
		try (PreparedStatement ps = connection.prepareStatement(queryExternal)) {
			ps.setInt(1, eventId);
			try (ResultSet rs = ps.executeQuery()) {
				while (rs.next()) {
					recipients.add(rs.getString("external_email"));
				}
			}
		} catch (SQLException e) {
			LOG.error(Constants.ERROR_DURING_GET_EXTERNALUSERS_FOR_EVENT, eventId, e);
			return false;
		} finally {
			watch.stop();
			LOG.info("Time to execute sendNotificationService: {} ms", watch.getTime(TimeUnit.MILLISECONDS));

		}

		for (String email : recipients) {
			LOG.info("Notification envoyée à " + email + " : " + message);
			LOG.info(" Email envoyé à " + email + " : " + message);
		}
		return true;

	}

	@Override
	public List<Eventdto> getAssignedEventsService(int userId) {
		StopWatch watch = new StopWatch();
		watch.start();
		List<Eventdto> events = new ArrayList<>();
		String query = Query.GET_ASSIGNED_EVENT;

		try (PreparedStatement ps = connection.prepareStatement(query)) {
			ps.setInt(1, userId);
			try (ResultSet rs = ps.executeQuery()) {
				while (rs.next()) {
					Eventdto event = new Eventdto();
					event.setId(rs.getInt("id"));
					event.setTitle(rs.getString("title"));
					event.setDateDebut((LocalDateTime) rs.getObject("date_debut"));
					event.setDateFin((LocalDateTime) rs.getObject("date_fin"));
					event.setDateFin((LocalDateTime) rs.getObject("date_fin"));
					event.setFormat(rs.getString("format_event"));
					int eventRoomId = rs.getInt("room_id");
					EventRoomdto eventRoom = new EventRoomdto();
					eventRoom.setId(eventRoomId);
					event.setStatus(rs.getString("status"));
					event.setModerator(rs.getString("moderator"));
					event.setTutor(rs.getString("tutor"));
					int planningId = rs.getInt("id_planning");
					Planningdto idplanning = new Planningdto();
					idplanning.setId(planningId);
					events.add(event);
				}
			}
		} catch (SQLException e) {
			LOG.error(Constants.ERROR_DURING_GET_EVENT_ASSIGNED_FOR_USERS, userId, e);
		} finally {
			watch.stop();
			LOG.info("Time to execute getAssignedEventsService: {} ms", watch.getTime(TimeUnit.MILLISECONDS));
		}

		return events;
	}

	@Override
	public ParticipationUserToEvent getUserStatusForEventService(int userId, int eventId) {
		StopWatch watch = new StopWatch();
		watch.start();
		String query = Query.GET_USERSTATUS_FOR_EVENT;
		try (PreparedStatement ps = connection.prepareStatement(query)) {
			ps.setInt(1, userId);
			ps.setInt(2, eventId);
			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					return ParticipationUserToEvent.valueOf(rs.getString("status_user"));
				}
			}
		} catch (SQLException e) {
			LOG.error("Erreur récupération statut user " + userId + " pour event " + eventId, e);
		} finally {
			watch.stop();
			LOG.info("Time to execute getUserStatusForEventService: {} ms", watch.getTime(TimeUnit.MILLISECONDS));
		}
		return ParticipationUserToEvent.PENDING;
	}

	@Override
	public boolean updateUserStatusForEventService(int userId, int eventId, ParticipationUserToEvent newStatus) {
		StopWatch watch = new StopWatch();
		watch.start();
		String query = Query.UPDATE_USERSTATUS_FOR_EVENT;
		try (PreparedStatement ps = connection.prepareStatement(query)) {
			ps.setString(1, newStatus.name());
			ps.setInt(2, userId);
			ps.setInt(3, eventId);
			return ps.executeUpdate() > 0;
		} catch (SQLException e) {
			LOG.error("Erreur mise à jour statut user " + userId + " pour event " + eventId, e);
			return false;
		} finally {
			watch.stop();
			LOG.info("Time to execute updateUserStatusForEventService: {} ms", watch.getTime(TimeUnit.MILLISECONDS));
		}
	}

	@Override
	public void updateExpiredEvents() {
		String query = Query.UPDATE_EXPIRED_EVENTS;
		try (PreparedStatement ps = connection.prepareStatement(query)) {
			int updated = ps.executeUpdate();
			LOG.info(updated + " événements passés en statut EXPIRÉ.");
		} catch (SQLException e) {
			LOG.error("Erreur lors de la mise à jour des événements expirés", e);
		}
	}

}
