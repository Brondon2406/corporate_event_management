package model.service.implementation;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import model.database.DatabaseConnection;
import model.dto.Eventdto;
import model.entity.EventRoom;
import model.service.EventService;
import model.service.sql.Query;
import util.constants.Constants;

public class EventServiceImpl implements EventService {

	private static final Logger LOG = LogManager.getLogger(EventServiceImpl.class);
	Connection connection = DatabaseConnection.getInstance();

	@Override
	public Eventdto createEvent(Eventdto event) {

		if (event == null || event.getEventRoom() == null) {
			throw new IllegalArgumentException("Données de l'événement invalides. Salle manquante.");
		}

		String query = Query.CREATE_EVENT;

		try {
			if (event.getEventRoom() == null) {
				LOG.error("Aucune salle sélectionnée pour l'événement !");
				throw new IllegalArgumentException("La salle d'événement ne peut pas être null.");
			}
			PreparedStatement ps = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
			ps.setString(1, event.getTitle());
			ps.setObject(2, event.getDateDebut());
			ps.setObject(3, event.getDateFin());
			ps.setString(4, event.getTypeEvent());
			ps.setString(5, event.getFormat());
			ps.setString(6, event.getModerator());
			ps.setString(7, event.getTutor());
			ps.setInt(8, event.getIdPlanning());
			ps.setInt(9, event.getEventRoom().getId());

			int result = ps.executeUpdate();
			if (result <= 0) {
				LOG.error(Constants.ERROR_DURING_EVENT_INSERTION);
				return null;
			}

			try (ResultSet rs = ps.getGeneratedKeys()) {
				if (rs.next()) {
					event.setId(rs.getInt(1));
				}
			}

			LOG.info("Événement créé avec succès : {}", event.getTitle());
			return event;

		} catch (SQLException e) {
			LOG.error(Constants.ERROR_CREATE_EVENT, e);
			return null;
		}
	}

	@Override
	public boolean linkUsersToEvent(int eventId, List<Integer> userIds) {
		if (userIds == null || userIds.isEmpty())
			return true; // Rien à faire

		String query = Query.INSERT_EVENT_USER;

		try (PreparedStatement ps = connection.prepareStatement(query)) {
			for (Integer userId : userIds) {
				ps.setInt(1, eventId);
				ps.setInt(2, userId);
				ps.addBatch();
			}

			int[] results = ps.executeBatch();
			LOG.info("Participants liés à l'événement ID {} : {}", eventId, results.length);
			return true;

		} catch (SQLException e) {
			LOG.error("Erreur lors de l'insertion des participants pour l'événement ID " + eventId, e);
			return false;
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
			ps.setString(7, event.getTutor());
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
		List<Eventdto> events = new ArrayList<>();
		String query = Query.SELECT_EVENT_BY_ID;
		Eventdto eventdto = null;
		try (PreparedStatement ps = connection.prepareStatement(query)) {

			ps.setInt(1, id);

			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					EventRoom eventRoom = new EventRoom(rs.getInt("room_id"), rs.getString("room_name"),
							rs.getInt("room_capacity"), rs.getBoolean("room_active"));

					Eventdto event = new Eventdto(rs.getInt("id"), rs.getString("title"),
							rs.getObject("date_debut", LocalDateTime.class),
							rs.getObject("date_fin", LocalDateTime.class), rs.getString("type_event"),
							rs.getString("format"), rs.getString("moderator"), rs.getString("tutor"), eventRoom

					);
					events.add(event);
				}
			}
		} catch (SQLException e) {
			LOG.error(Constants.ERROR_GET_EVENT_BY_ID, e);
		}
		return eventdto;
	}

	@Override
	public List<Eventdto> getAllEvents() {
		List<Eventdto> events = new ArrayList<>();
		String query = Query.GET_ALL_EVENTS;

		try (PreparedStatement ps = connection.prepareStatement(query); ResultSet rs = ps.executeQuery()) {

			while (rs.next()) {
				EventRoom eventroom = new EventRoom(rs.getInt("room_id"), rs.getString("room_name"),
						rs.getInt("room_capacity"), rs.getBoolean("room_active"));

				Eventdto event = new Eventdto(rs.getInt("id"), rs.getString("title"),
						rs.getObject("date_debut", java.time.LocalDateTime.class),
						rs.getObject("date_fin", java.time.LocalDateTime.class), rs.getString("type_event"),
						rs.getString("format"), rs.getString("moderator"), rs.getString("tutor"), eventroom

				);

				events.add(event);
			}

		} catch (SQLException e) {
			LOG.error(Constants.ERROR_GET_ALL_EVENTS, e);
		}
		return events;
	}

	@Override
	public boolean createEventWithUsers(Eventdto event, List<Integer> participantIds) {
		try {
			Eventdto createdEvent = createEvent(event); 
			if (createdEvent == null)
				return false;

			int eventId = createdEvent.getId();

			String query = "INSERT INTO event_users (event_id, user_id) VALUES (?, ?)";
			try (PreparedStatement ps = connection.prepareStatement(query)) {
				for (Integer userId : participantIds) {
					ps.setInt(1, eventId);
					ps.setInt(2, userId);
					ps.addBatch();
				}
				ps.executeBatch();
			}

			return true;
		} catch (SQLException e) {
			LOG.error("Erreur lors de l'ajout des participants à l'événement", e);
			return false;
		}
	}

}
