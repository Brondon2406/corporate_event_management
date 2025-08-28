package model.service.sql;

public class Query {

	public static final String CREATE_USER = "INSERT INTO users (name, email, password, role, fonction) VALUES (?,?,?,?,?)";

	public static final String GET_USER = "SELECT * FROM users WHERE email = ? AND password = ?";

	public static final String UPDATE_USER = "UPDATE users SET name = ?, email = ?, password = ? WHERE id = ?";

	public static final String CREATE_EVENT = "INSERT INTO events (title, date_debut, date_fin, type_event, room_id) VALUES (?, ?, ?, ?, ?)";

	public static final String UPDATE_EVENT = "UPDATE events SET title = ?, date_debut = ?, date_fin = ?, type_event = ?, room_id = ? WHERE id = ?";

	public static final String DELETE_EVENT = "DELETE FROM events WHERE id = ?";
}
