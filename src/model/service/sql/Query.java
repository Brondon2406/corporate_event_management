package model.service.sql;

public class Query {

	public static final String CREATE_USER = "INSERT INTO users (name, email, password, role, fonction) VALUES (?,?,?,?,?)";

	public static final String GET_USER = "SELECT * FROM users WHERE email = ? AND password = ?";

	public static final String UPDATE_USER = "UPDATE users SET name = ?, email = ?, password = ? WHERE id = ?";

	public static final String CREATE_EVENT = "INSERT INTO events (title, date_debut, date_fin, type_event, room_id) VALUES (?, ?, ?, ?, ?)";

	public static final String UPDATE_EVENT = "UPDATE events SET title = ?, date_debut = ?, date_fin = ?, type_event = ?, room_id = ? WHERE id = ?";

	public static final String DELETE_EVENT = "DELETE FROM events WHERE id = ?";
	
	public static final String SELECT_EVENT_BY_ID_AND_NAME = "SELECT * FROM event_rooms WHERE id = ? AND name = ?";
	
	public static final String SELECT_EVENT_BY_ID = "SELECT * FROM events WHERE id = ?";
	
	public static final String SELECT_USER_BY_ID = "SELECT id, name, email, role, fonction FROM users WHERE id = ?";
	
	public static final String  DELETE_USER = "DELETE FROM users WHERE id = ?";
	
	public static final String GET_ALL_EVENTS = "SELECT e.id, e.title, e.date_debut, e.date_fin, e.type_event, r.id AS room_id, r.name AS room_name "
			+ "FROM events e JOIN event_rooms r ON e.room_id = r.id";
}
