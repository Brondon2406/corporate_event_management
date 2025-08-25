package model.service.implementation;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import model.dto.Userdto;
import model.mapping.MappingService.MappingUser;
import model.database.DatabaseConnection;
import model.entity.Users;
import model.service.UserService;

public class UserServiceImpl implements UserService{
	private static final Logger LOG = LogManager.getLogger(UserServiceImpl.class);
	private final DatabaseConnection db;
	private final MappingUser mapper;
	
	public UserServiceImpl(DatabaseConnection db,  MappingUser mapper) {
		this.db = db;
		this.mapper = mapper;	
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

	@Override
	public Userdto createUser(Users user) {
		// TODO Auto-generated method stub
		return null;
	}

	
}
