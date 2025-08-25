package model.service.sql;

public class Query {
	
	public static final String CREATE_USER = "INSERT INTO users (name, email, password, role, fonction) VALUES (?,?,?,?,?)";

	public static final String GET_USER ="SELECT * FROM users WHERE email = ? AND password = ?";


}
