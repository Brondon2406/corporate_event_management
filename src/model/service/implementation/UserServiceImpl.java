package model.service.implementation;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.time.StopWatch;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import model.dto.Userdto;
import model.database.DatabaseConnection;
import model.service.UserService;
import model.service.sql.Query;
import util.constants.Constants;

public class UserServiceImpl implements UserService {

	private static final Logger LOG = LogManager.getLogger(UserServiceImpl.class);

	Connection connection = DatabaseConnection.getInstance();

	@Override
	public Userdto createUser(Userdto user) {
		StopWatch watch = new StopWatch();
		watch.start();
		String query = Query.CREATE_USER;

		try {
			PreparedStatement ps = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
			ps.setString(1, user.getName());
			ps.setString(2, user.getFirstName());
			ps.setString(3, user.getEmail());
			ps.setString(4, user.getPassword());
			ps.setString(5, user.getRole());
			ps.setString(6, user.getFonction());

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
			userDTO.setFirstName(user.getFirstName());
			userDTO.setEmail(user.getEmail());
			userDTO.setName(user.getPassword());
			userDTO.setRole(user.getRole());
			userDTO.setFonction(user.getFonction());

			LOG.info("Événement créé avec succès : {}", user.getEmail());
			return userDTO;

		} catch (SQLException e) {
			LOG.error(Constants.ERROR_CREATE_USER, e);
			return null;
		}finally {
			watch.stop();
			LOG.info("Time to execute getPlanningById: {} ms", watch.getTime(TimeUnit.MILLISECONDS));
		}
	}

	@Override
	public boolean updateUser(Userdto userDTO) {
		StopWatch watch = new StopWatch();
		watch.start();
		String query = Query.UPDATE_USER;
		try {
			PreparedStatement ps = connection.prepareStatement(query);

			ps.setString(1, userDTO.getName());
			ps.setString(2, userDTO.getFirstName());
			ps.setString(3, userDTO.getEmail());
			ps.setString(4, userDTO.getPassword());
			ps.setString(5, userDTO.getFonction());
			ps.setInt(6, userDTO.getId());

			int rows = ps.executeUpdate();
			return rows > 0;

		} catch (SQLException e) {
			LOG.error(Constants.ERROR_UPDATE_USER, e);
			return false;
		}finally {
			watch.stop();
			LOG.info("Time to execute getPlanningById: {} ms", watch.getTime(TimeUnit.MILLISECONDS));
		}
	}

	@Override
	public boolean deleteUser(int userId) {
		StopWatch watch = new StopWatch();
		watch.start();
		String deleteEventUsers = Query.DELETE_USER_FROM_EVENTUSERS;
		String deleteUser = Query.DELETE_USER;

		try {
			PreparedStatement ps = connection.prepareStatement(deleteEventUsers);
			PreparedStatement ps2 = connection.prepareStatement(deleteUser);

			ps.setInt(1, userId);
			ps.executeUpdate();

			ps2.setInt(1, userId);
			int rows = ps2.executeUpdate();

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
		}finally {
			watch.stop();
			LOG.info("Time to execute getPlanningById: {} ms", watch.getTime(TimeUnit.MILLISECONDS));
		}
	}

	@Override
	public Userdto getUserById(int userId) {
		StopWatch watch = new StopWatch();
		watch.start();
		String query = Query.GET_USER_BY_ID;
		Userdto user = null;

		try {
			PreparedStatement ps = connection.prepareStatement(query);
			ps.setInt(1, userId);

			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					user = new Userdto();
					user.setId(rs.getInt("id"));
					user.setFirstName(rs.getString("first_name"));
					user.setName(rs.getString("name"));
					user.setEmail(rs.getString("email"));
					user.setRole(rs.getString("role"));
					user.setFonction(rs.getString("fonction"));
				}
			}
		} catch (SQLException e) {
			LOG.error(Constants.ERROR_GET_USER_BY_ID, e);
		}finally {
			watch.stop();
			LOG.info("Time to execute getPlanningById: {} ms", watch.getTime(TimeUnit.MILLISECONDS));
		}

		return user;
	}
	
	@Override
	public List<Userdto> findUsersByRole(String role) {
		StopWatch watch = new StopWatch();
		watch.start();
		List<Userdto> users = new ArrayList<>();
		String query = Query.GET_USERS_BY_ROLE;

		try {
			PreparedStatement stmt = connection.prepareStatement(query);

			stmt.setString(1, role);
			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				Userdto user = new Userdto();
				user.setId(rs.getInt("id"));
				user.setName(rs.getString("name"));
				user.setFirstName(rs.getString("first_name"));
				user.setEmail(rs.getString("email"));
				user.setRole(rs.getString("role"));
				user.setFonction(rs.getString("fonction"));
				users.add(user);
			}

		} catch (SQLException e) {
			LOG.error(Constants.ERROR_DURING_GET_USERS_BY_ROLE, e);
		}finally {
			watch.stop();
			LOG.info("Time to execute getPlanningById: {} ms", watch.getTime(TimeUnit.MILLISECONDS));
		}

		return users;
	}

	@Override
	public List<Userdto> getAllUsers() {
		StopWatch watch = new StopWatch();
		watch.start();
		String query = Query.GET_ALL_USERS;
		List<Userdto> users = new ArrayList<>();

		try {
			PreparedStatement ps = connection.prepareStatement(query);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				Userdto user = new Userdto();
				user.setId(rs.getInt("id"));
				user.setName(rs.getString("name"));
				user.setFirstName(rs.getString("first_name"));
				user.setEmail(rs.getString("email"));
				user.setRole(rs.getString("role"));
				user.setFonction(rs.getString("fonction"));
				users.add(user);
			}
		} catch (SQLException e) {
			LOG.error(Constants.ERROR_DURING_GET_USERS, e);
		}

		return users;
	}

}
