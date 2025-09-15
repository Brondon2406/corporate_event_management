package model.service.sql;

public class Query {

	public static final String CREATE_USER = "INSERT INTO users (name, first_name, email, password, role, fonction) VALUES (?,?,?,?,?,?)";

	public static final String GET_USER = "SELECT * FROM users WHERE email = ? AND password = ?";

	public static final String UPDATE_USER = "UPDATE users SET name = ?,first_name = ?, email = ?, password = ?, fonction = ? WHERE id = ?";

	public static final String CREATE_EVENT = "INSERT INTO events (title, date_debut, date_fin, type_event, format_event, moderator, tutor, id_planning, room_id, status) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

	public static final String UPDATE_EVENT = "UPDATE events SET title = ?, date_debut = ?, date_fin = ?, type_event = ?, format_event = ?, moderator = ?, room_id = ?, moderator = ? WHERE id = ?";

	public static final String DELETE_EVENT = "DELETE FROM events WHERE id = ?";

	public static final String GET_EVENT_BY_ID_AND_NAME = "SELECT * FROM events_room WHERE id = ? AND name = ?";

	public static final String GET_EVENT_BY_ID = "SELECT * FROM events WHERE id = ?";

	public static final String GET_USER_BY_ID = "SELECT id, name,first_name, email, role, fonction FROM users WHERE id = ?";

	public static final String DELETE_USER = "DELETE FROM users WHERE id = ?";

	public static final String DELETE_USER_FROM_EVENTUSERS ="DELETE FROM event_users WHERE user_id = ?";

	public static final String GET_ALL_EVENTS = "SELECT * FROM events";

	public static final String GET_EVENTROOM_BY_ID_AND_NAME = "SELECT * FROM event_rooms WHERE id = ? AND name = ?";

	public static final String GET_ALL_ACTIVE_ROOMS = "SELECT * FROM event_rooms WHERE active = TRUE";

	public static final String GET_EVENTROOM_BY_ID = "SELECT * FROM event_rooms WHERE id = ? AND active = true";

	public static final String GET_ALL_USERS = "SELECT * FROM users";

	public static final String CREATE_EVENT_USER = "INSERT INTO event_users (event_id, user_id) VALUES (?, ?)";

	public static final String CREATE_PLANNING = "INSERT INTO planning (motif, date_debut, date_fin,tutor_planning) VALUES (?,?,?,?) ";

	public static final String GET_PLANNING_BY_ID = "SELECT * FROM planning WHERE id = ?";;

	public static final String DELETE_PLANNING = "DELETE FROM planning WHERE id = ?";

	public static final String UPDATE_PLANNING = "UPDATE planning SET motif = ?, date_debut = ?, date_fin = ?, tutor_planning = ? WHERE id = ?";

	public static final String CREATE_EVENTROOM = "INSERT INTO event_rooms(name, capacity, active) VALUES (?, ?, ?)";

	public static final String UPDATE_EVENTROOM = "UPDATE event_rooms SET name = ?, capacity = ?, active = ? WHERE id = ?";

	public static final String DELETE_EVENTROOM = "DELETE FROM event_rooms WHERE id = ?";

	public static final String GET_ALL_PLANNING = "SELECT * FROM planning";

	public static final String GET_PLANNINGS_BY_PERIOD = "SELECT p.id, p.motif, p.date_debut, p.date_fin, u.id AS tutor_id, u.name AS tutor_name, u.first_name AS tutor_first_name FROM planning p LEFT JOIN users u ON p.tutor_planning = u.id WHERE p.date_debut >= ? AND p.date_fin <= ?";

	public static final String GET_ALL_EVENTROOMS = "SELECT * FROM event_rooms";

	public static final String GET_EVENTROOMS_BY_ID = "SELECT * FROM event_rooms WHERE id = ?";

	public static final String GET_USERS_BY_ROLE = "SELECT * FROM users WHERE Role = ?";

	public static final String DELETE_EVENTS_IN_PLANNING = "DELETE FROM events WHERE id_planning = ?";

	public static final String LINK_EVENT_WITH_INTERNAL_USERS = "INSERT INTO event_users (event_id, user_id, status_user) VALUES (?, ?, 'PENDING')";

	public static final String LINK_EVENT_WITH_EXTERNAL_USERS = "INSERT INTO event_users (event_id, external_email) VALUES (?, ?)";

	public static final String ADD_INTERNAL_USERS = "INSERT INTO event_users (event_id, user_id) VALUES (?, ?)";

	public static final String REMOVE_INTERNAL_USERS = "DELETE FROM event_users WHERE event_id = ? AND user_id = ?";

	public static final String ADD_EXTERNAL_USERS = "INSERT INTO event_users (event_id, external_email) VALUES (?, ?)";

	public static final String REMOVE_EXTERNAL_USERS = "DELETE FROM event_users WHERE event_id = ? AND external_email = ?";

	public static final String GET_ALL_EMAIL_TO_USER_INTERNEL = "SELECT u.email FROM users u "
			+ "JOIN event_users eu ON u.id = eu.user_id " + "WHERE eu.event_id = ?";

	public static final String GET_EVENTS_BY_STATUS = "SELECT * FROM events WHERE status = ?";

	public static final String UPDATE_EVENTS_STATUS = "UPDATE events SET status = ? WHERE id = ?";

	public static final String GET_ALL_EMAIL_TO_USER_EXTERNEL = "SELECT external_email FROM event_users WHERE event_id = ?";

	public static final String GET_ASSIGNED_EVENT = "SELECT e.id, e.title, e.date_debut, e.date_fin, e.format_event, "
			+ "e.room_id, e.status, e.moderator, e.tutor, e.id_planning " + "FROM events e "
			+ "JOIN event_users eu ON e.id = eu.event_id " + "WHERE eu.user_id = ?";

	public static final String GET_USERSTATUS_FOR_EVENT = "SELECT status_user FROM event_users WHERE user_id = ? AND event_id = ?";

	public static final String UPDATE_USERSTATUS_FOR_EVENT = "UPDATE event_users SET status_user = ? WHERE user_id = ? AND event_id = ?";

	public static final String GET_INTERNALUSERS_FOR_EVENT = "SELECT user_id FROM event_users WHERE event_id = ?";

	public static final String GET_EXTERNALUSERS_FOR_EVENT = "SELECT external_email FROM event_users WHERE event_id = ?";

	public static final String UPDATE_EXPIRED_EVENTS = "UPDATE events SET status = 'EXPIRED' "
			+ "WHERE date_fin < NOW() AND status <> 'EXPIRED'";


}