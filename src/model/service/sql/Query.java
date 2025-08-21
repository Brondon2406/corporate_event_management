package model.service.sql;

public class Query {
	
	public static final String CREATE_USER = "INSERT INTO users (name, email, password, role, fonction) VALUES (?,?,?,?,?)";

	public static final String GET_USER ="SELECT id, email, password FROM corporate_event_management_system WHERE email = ? AND password = ?";
			

}
