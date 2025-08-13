package model.service.implementation;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import controller.dto.Userdto;
import model.database.DatabaseConnection;
import model.service.UserService;

public class UserServiceImpl implements UserService{
	private static final Logger LOG = LogManager.getLogger(UserServiceImpl.class);
	private final DatabaseConnection db;
	
	public UserServiceImpl(DatabaseConnection db) {
		this.db = db;
	}

	@Override
	public Userdto createUser(Userdto userDTO) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Userdto updateUser(Userdto userDTO) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String deleteUser(int userId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Userdto getUserById(int userId) {
		// TODO Auto-generated method stub
		return null;
	}

	
}
