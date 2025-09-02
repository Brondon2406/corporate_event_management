package model.service.implementation;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import model.dto.Userdto;
import model.database.DatabaseConnection;
import model.entity.Users;
import model.service.UserService;
import model.service.sql.Query;
import util.constants.Constants;

public class UserServiceImpl implements UserService {

	private static final Logger LOG = LogManager.getLogger(UserServiceImpl.class);
	Connection connection = DatabaseConnection.getInstance();

	@Override
	public Userdto createUser(Users user) {
		String query = Query.CREATE_USER;

		try (PreparedStatement ps = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {

			ps.setString(1, user.getName());
			ps.setString(2, user.getEmail());
			ps.setString(3, user.getPassword());
			ps.setString(4, user.getRole().name());
			ps.setString(5, user.getFonction());

			int result = ps.executeUpdate();
			if (result <= 0) {
				LOG.error(Constants.ERROR_DURING_USER_INSERTION);
				return null;
			}

			try (ResultSet rs = ps.getGeneratedKeys()) {
				if (rs.next()) {
					user.setId(rs.getInt(1));
				}
			}

			Userdto userDTO = new Userdto();
			userDTO.setId(user.getId());
			userDTO.setName(user.getName());
			userDTO.setEmail(user.getEmail());
			userDTO.setRole(user.getRole().name());
			userDTO.setFonction(user.getFonction());

			LOG.info("Utilisateur créé avec succès : {}", user.getEmail());
			return userDTO;

		} catch (SQLException e) {
			LOG.error(Constants.ERROR_CREATE_USER, e);
			return null;
		}
	}

	@Override
	public boolean updateUser(Userdto userDTO) {
		String query = Query.UPDATE_USER;
		try (PreparedStatement ps = connection.prepareStatement(query)) {

			ps.setString(1, userDTO.getName());
			ps.setString(2, userDTO.getEmail());
			ps.setString(3, userDTO.getPassword());
			ps.setInt(4, userDTO.getId());

			int rows = ps.executeUpdate();
			return rows > 0;

		} catch (SQLException e) {
			LOG.error(Constants.ERROR_UPDATE_USER, e);
			return false;
		}
	}

	@Override
	public boolean deleteUser(int userId) {
		String query = Query.DELETE_USER;
		try (PreparedStatement ps = connection.prepareStatement(query)) {
			ps.setInt(1, userId);
			int rows = ps.executeUpdate();

			if (rows > 0) {
				LOG.info("Utilisateur avec ID {} supprimé avec succès", userId);
				return true;
			} else {
				LOG.warn(Constants.NO_USER_FOUND, userId);
				return false;
			}
		} catch (SQLException e) {
			LOG.error(Constants.ERROR_DELETE_USER, userId, e);
			return false;
		}
	}

	@Override
	public Userdto getUserById(int userId) {
		String query = Query.SELECT_USER_BY_ID;
		Userdto user = null;

		try (PreparedStatement ps = connection.prepareStatement(query)) {
			ps.setInt(1, userId);

			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					user = new Userdto();
					user.setId(rs.getInt("id"));
					user.setName(rs.getString("name"));
					user.setEmail(rs.getString("email"));
					user.setRole(rs.getString("role"));
					user.setFonction(rs.getString("fonction"));
				}
			}
		} catch (SQLException e) {
			LOG.error(Constants.ERROR_GET_USER_BY_ID, e);
		}

		return user;
	}

	@Override
	public List<Userdto> getAllUsers() {
	    List<Userdto> users = new ArrayList<>();
	    String query = "SELECT id, name, email, role FROM users";

	    try (Connection con = DatabaseConnection.getInstance();
	         PreparedStatement ps = con.prepareStatement(query);
	         ResultSet rs = ps.executeQuery()) {

	        while (rs.next()) {
	            Userdto user = new Userdto();
	            user.setId(rs.getInt("id"));
	            user.setName(rs.getString("name"));
	            user.setEmail(rs.getString("email"));
	            user.setRole(rs.getString("role"));
	            users.add(user);
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }

	    return users;
	}

}
