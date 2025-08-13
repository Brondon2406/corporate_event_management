package model.database;

import java.sql.Connection;
import java.sql.DriverManager;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import controller.utilities.MappingServiceImpl.MappingUserImpl;
import util.Constants;

public class DatabaseConnection {
	
	private static final Logger LOG = LogManager.getLogger(MappingUserImpl.class);
	
	private Connection con;
	private Connection SINGLETON;
	private static final String DB_USER = "";
	private static final String DB_USER_PASS = "";
	private static final String DB_URL = "";
	private static final String CLASS_FOR_NAME = "";
	
	static {
		try {
			Class.forName(CLASS_FOR_NAME);
		} catch (ClassNotFoundException e) {
			LOG.warn(
					String.format(Constants.ERROR_TO_CHARGE_CLASS_FOR_NAME, CLASS_FOR_NAME, e.getMessage()));
		}
	}
	
	public Connection getConnection() {
		try {
			con = DriverManager.getConnection(DB_URL, DB_USER, DB_USER_PASS);
			return con;
		}catch(Exception e) {
			LOG.warn(
					String.format(Constants.DATABASE_CONNECTION_ERROR, e.getMessage()));
			return null;
		}
	}
	
	public Connection getInstance() {
		if (SINGLETON == null) {
			SINGLETON = getConnection();
		}
		return SINGLETON;
	}
	

}
