package model.service.implementation;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import controller.dto.Userdto;
import controller.utilities.MappingService.MappingUser;
import controller.utilities.MappingServiceImpl.MappingUserImpl;
import model.database.DatabaseConnection;
import model.entity.Users;
import model.service.AuthenticationService;
import model.service.sql.Query;
import util.Constants;

public class AuthenticationServiceImpl implements AuthenticationService {
	private static final Logger LOG = LogManager.getLogger(AuthenticationServiceImpl.class);
	private final DatabaseConnection db;
	private final MappingUser mapper;
	Connection connection = null;
	PreparedStatement ps = null;

	public AuthenticationServiceImpl(DatabaseConnection db, MappingUserImpl mapper) {
		super();
		this.db = db;
		this.mapper = mapper;
		connection = db.getInstance();
	}

	@Override
	public Userdto registerUser(Users user) {
		String query = Query.CREATE_USER;
		try {
			ps = connection.prepareStatement(query);
			ps.setString(1, user.getName());
			ResultSet result = ps.executeQuery();
			if (result == null) {
				LOG.info(Constants.ERROR_DURING_USER_INSERTION);
			}
			Userdto userDTO = new Userdto();
			while (result.next()) {
				userDTO.setName(result.getString("name"));
			}
			return userDTO;
		} catch (Exception e) {
			LOG.error(Constants.ERROR_CREATE_USER, e.getMessage());
			return null;
		}
	}

	@Override
	public Userdto loginUser() {
		// TODO Auto-generated method stub
		return null;
	}

	
}
