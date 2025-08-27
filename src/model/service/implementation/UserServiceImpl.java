package model.service.implementation;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import model.dto.Userdto;
import model.database.DatabaseConnection;
import model.entity.Users;
import model.service.UserService;
import model.service.sql.Query;

public class UserServiceImpl implements UserService{
	
	private static final Logger LOG = LogManager.getLogger(UserServiceImpl.class);
	Connection connection = DatabaseConnection.getInstance();  
	
	@Override
	public boolean updateUser(Userdto userDTO) {
		String query = Query.UPDATE_USER ;
		 try (PreparedStatement ps = connection.prepareStatement(query)) {
			 
			 	ps.setString(1, userDTO.getName());
		        ps.setString(2, userDTO.getEmail());
		        ps.setString(3, userDTO.getPassword());
		        ps.setInt(4, userDTO.getId());

		        int rows = ps.executeUpdate();
		        return rows > 0;

		    } catch (SQLException e) {
		        LOG.error("Erreur updateUser: {}", e);
		        return false;
		    }
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
