package model.service.sql;

public class Query {

	public static final String CREATE_USER = "INSERT INTO users (name, first_name, email, password, role, fonction) VALUES (?,?,?,?,?,?)";

	public static final String GET_USER = "SELECT * FROM users WHERE email = ? AND password = ?";

	public static final String UPDATE_USER = "UPDATE users SET name = ?,first_name = ?, email = ?, password = ? WHERE id = ?";

	public static final String CREATE_EVENT = "INSERT INTO events (title, date_debut, date_fin, type_event, format_event, moderator, tutor, id_planning, room_id) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

	public static final String UPDATE_EVENT = "UPDATE events SET title = ?, date_debut = ?, date_fin = ?, type_event = ?, room_id = ? WHERE id = ?";

	public static final String DELETE_EVENT = "DELETE FROM events WHERE id = ?";

	public static final String SELECT_EVENT_BY_ID_AND_NAME = "SELECT * FROM events_room WHERE id = ? AND name = ?";

	public static final String SELECT_EVENT_BY_ID = "SELECT * FROM events WHERE id = ?";

	public static final String SELECT_USER_BY_ID = "SELECT id, name,first_name, email, role, fonction FROM users WHERE id = ?";

	public static final String DELETE_USER = "DELETE FROM users WHERE id = ?";

	public static final String GET_ALL_EVENTS = "SELECT title, date_debut, date_fin, type_event, format_event, moderator, tutor, id_planning, room_id FROM events";

	public static final String SELECT_EVENTROOM_BY_ID_AND_NAME = "SELECT * FROM event_rooms WHERE id = ? AND name = ?";

	public static final String SELECT_ALL_ACTIVE_ROOMS = "SELECT * FROM event_rooms WHERE active = TRUE";

	public static final String SELECT_EVENTROOM_BY_ID = "SELECT * FROM event_rooms WHERE id = ? AND active = true";

	public static final String GET_ALL_USERS = "SELECT id, first_name, name, email, role, fonction FROM users";

	public static final String INSERT_EVENT_USER = "INSERT INTO event_users (event_id, user_id) VALUES (?, ?)";

	public static final String CREATE_PLANNING = "INSERT INTO planning (motif, date_debut, date_fin,tutor_planning) VALUES (?,?,?,?) ";

	public static final String SELECT_PLANNING_BY_ID = "SELECT * FROM planning WHERE id = ?";;

	public static final String DELETE_PLANNING = "DELETE FROM planning WHERE id = ?";

	public static final String UPDATE_PLANNING = "UPDATE planning SET motif = ?, date_debut = ?, date_fin = ?, tutor_planning = ? WHERE id = ?";

	public static final String CREATE_EVENTROOM = "INSERT INTO event_rooms(name, capacity, active) VALUES (?, ?, ?)";

	public static final String UPDATE_EVENTROOM = "UPDATE event_rooms SET name = ?, capacity = ? WHERE id = ?";

	public static final String DELETE_EVENTROOM = "DELETE FROM event_rooms WHERE id = ?";

	public static final String SELECT_ALL_PLANNING = "SELECT * FROM planning";

	public static final String SELECT_PLANNINGS_BY_PERIOD = "SELECT p.id, p.motif, p.date_debut, p.date_fin, u.id AS tutor_id, u.name AS tutor_name, u.first_name AS tutor_first_name FROM planning p LEFT JOIN users u ON p.tutor_planning = u.id WHERE p.date_debut >= ? AND p.date_fin <= ?";

		public static final String SELECT_ALL_EVENTROOMS = "SELECT * FROM event_rooms";

	public static final String SELECT_EVENTROOMS_BY_ID = "SELECT * FROM event_rooms WHERE id = ?";

	public static final String SELECT_ALL_ROOMS = "SELECT * FROM event_rooms";

	public static final String DELETE_EVENTS_IN_PLANNING = "DELETE FROM events WHERE id_planning = ?";

}