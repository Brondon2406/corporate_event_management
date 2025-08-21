package model.service.implementation;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import controller.dto.Userdto;
import model.database.DatabaseConnection;
import model.entity.Users;
import model.service.AuthenticationService;
import model.service.sql.Query;
import util.Constants;

public class AuthenticationServiceImpl implements AuthenticationService {
	private static final Logger LOG = LogManager.getLogger(AuthenticationServiceImpl.class);
	Connection connection = DatabaseConnection.getInstance();
	PreparedStatement ps = null;

	@Override
	public Userdto registerUser(Users user) {
		String query = Query.CREATE_USER;
		try {
			ps = connection.prepareStatement(query);
			ps.setString(1, user.getName());
			ps.setString(2, user.getEmail());
			ps.setString(3, user.getPassword());
			ps.setString(4, user.getRole().name());
			ps.setString(5, user.getFonction());
			ResultSet result = ps.executeQuery();
			if (result == null) {
				LOG.info(Constants.ERROR_DURING_USER_INSERTION);
			}
			Userdto userDTO = new Userdto();
			while (result.next()) {
				userDTO.setId(result.getInt("id"));
				userDTO.setName(result.getString("name"));
				userDTO.setName(result.getString("email"));
				userDTO.setName(result.getString("role"));
				userDTO.setName(result.getString("fonction"));
			}
			return userDTO;
		} catch (Exception e) {
			LOG.error(Constants.ERROR_CREATE_USER, e.getMessage());
			return null;
		}
	}

	@Override
	public Userdto loginUser(Users user) {
	    String query = Query.GET_USER ;
	    try (Connection conn = DatabaseConnection.getConnection();
	         PreparedStatement ps = conn.prepareStatement(query)) {

	      
	        ps.setString(1, user.getEmail());
	        ps.setString(2, user.getPassword());

	       
	        ResultSet result = ps.executeQuery();
	       
	        if (result.next()) {
	            Userdto userDTO = new Userdto();
	            userDTO.setId(result.getInt("id"));
	            userDTO.setEmail(result.getString("email"));
	            userDTO.setPassword(result.getString("password"));
	            return userDTO;
	        } else {
	            LOG.info(Constants.ERROR_DURING_USER_SELECTION);
	            return null;
	        }
	    } catch (SQLException e) {
	        LOG.error(Constants.ERROR_GET_USER + e.getMessage());
	        return null;
	    }
	 }
}