package model.database;

import java.sql.Connection;
import java.sql.DriverManager;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import controller.utilities.MappingServiceImpl.MappingUserImpl;
import util.Constants;

public class DatabaseConnection {
	
	
	private static final Logger LOG = LogManager.getLogger(MappingUserImpl.class);
	
	private static Connection con;
	private static Connection SINGLETON;
	private static final String DB_USER = "root";
	private static final String DB_USER_PASS = "";
	private static final String DB_URL = "jdbc:mysql://localhost:3307/corporate_event_management_systeme";
	private static final String CLASS_FOR_NAME = "com.mysql.cj.jdbc.Driver";
	
	static {
		try {
			Class.forName(CLASS_FOR_NAME);
		} catch (ClassNotFoundException e) {
			LOG.warn(
					String.format(Constants.ERROR_TO_CHARGE_CLASS_FOR_NAME, CLASS_FOR_NAME, e.getMessage()));
		}
	}
	
	private static Connection getConnection() {
		try {
			con = DriverManager.getConnection(DB_URL, DB_USER, DB_USER_PASS);
			return con;
		}catch(Exception e) {
			LOG.warn(
					String.format(Constants.DATABASE_CONNECTION_ERROR, e.getMessage()));
			return null;
		}
	}
	
	public static Connection getInstance() {
		if (SINGLETON == null) {
			SINGLETON = getConnection();
		}
		return SINGLETON;
	}
	
	
}
