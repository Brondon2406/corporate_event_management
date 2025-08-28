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
import model.dto.Eventdto;
import model.entity.EventRoom;
import model.service.sql.Query;
import util.constants.Constants;
import model.service.EventService;

public class EventServiceImpl implements EventService {

	private static final Logger LOG = LogManager.getLogger(EventServiceImpl.class);
	Connection connection = DatabaseConnection.getInstance();

	@Override
	public Eventdto registerEvent(Eventdto event) {
		String query = Query.CREATE_EVENT;

		try (PreparedStatement ps = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {

			ps.setString(1, event.getTitle());
			ps.setObject(2, event.getDateDebut());
			ps.setObject(3, event.getDateFin());
			ps.setString(4, event.getTypeEvent());
			ps.setInt(5, event.getEventRoom().getId());

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

			Eventdto eventDTO = new Eventdto();
			eventDTO.setId(event.getId());
			eventDTO.setTitle(event.getTitle());
			eventDTO.setDateDebut(event.getDateDebut());
			eventDTO.setDateFin(event.getDateFin());
			eventDTO.setTypeEvent(event.getTypeEvent());
			eventDTO.setEventRoom(event.getEventRoom());

			LOG.info("Evenement créé avec succès : {}", event.getTitle());
			return eventDTO;

		} catch (SQLException e) {
			LOG.error(Constants.ERROR_CREATE_EVENT, e);
			return null;
		}
	}

	@Override
	public boolean updateEvent(Eventdto event) {
		String query = Query.UPDATE_EVENT;
		try (PreparedStatement ps = connection.prepareStatement(query)) {

			ps.setString(1, event.getTitle());
			ps.setObject(2, event.getDateDebut());
			ps.setObject(3, event.getDateFin());
			ps.setString(4, event.getTypeEvent());
			ps.setInt(5, event.getEventRoom().getId());
			ps.setInt(6, event.getId());

			int rows = ps.executeUpdate();
			return rows > 0;

		} catch (SQLException e) {
			LOG.error(Constants.ERROR_DURING_EVENT_SELECTION);
			return false;

		}
	}

	@Override
	public boolean deleteEvent(int eventId) {
		String query = Query.DELETE_EVENT;
		try (PreparedStatement ps = connection.prepareStatement(query)) {

			ps.setInt(1, eventId);
			return ps.executeUpdate() > 0;

		} catch (SQLException e) {
			LOG.error("Erreur deleteEvent: {}", e);
			return false;
		}
	}

	@Override
	public List<Eventdto> getAllEvents() {
		List<Eventdto> events = new ArrayList<>();
		String sql = "SELECT e.id, e.title, e.date_debut, e.date_fin, e.type_event, r.id AS room_id, r.name AS room_name "
				+ "FROM events e JOIN event_rooms r ON e.room_id = r.id";

		try (Connection conn = DatabaseConnection.getInstance();
				PreparedStatement ps = conn.prepareStatement(sql);
				ResultSet rs = ps.executeQuery()) {

			while (rs.next()) {
				EventRoom eventRoom = new EventRoom(rs.getInt("room_id"), rs.getString("room_name"),
						rs.getInt("room_capacity"));
				Eventdto event = new Eventdto(rs.getInt("id"), rs.getString("title"),
						rs.getObject("date_debut", java.time.LocalDateTime.class),
						rs.getObject("date_fin", java.time.LocalDateTime.class), rs.getString("type_event"), eventRoom);
				events.add(event);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return events;
	}
}