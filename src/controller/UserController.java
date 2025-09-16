package controller;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import model.dto.Userdto;
import model.service.UserService;
import model.service.implementation.UserServiceImpl;
import util.constants.Constants;

public class UserController {
	private static final Logger LOG = LogManager.getLogger(UserController.class);
	private static UserService userService = new UserServiceImpl();

	 /**
     * Creates a new user.
     *
     * @param userdto Userdto object containing the user's information
     * @return the created user (Userdto), or null if the operation fails
     */
	public Userdto userCreateController(Userdto userdto) {

		Userdto dto = userService.createUser(userdto);
		if (dto == null) {
			LOG.error(Constants.EMPTY_USER_DTO);
			return null;
		}
		return dto;
	}	

	/**
     * Updates the information of an existing user.
     *
     * @param user Userdto containing the new information
     * @return true if the update succeeds, false otherwise
     */
	public boolean updateUserController(Userdto user) {
		if (user == null) {
			LOG.error("Userdto vide ou null !");
			return false;
		}

		try {
			boolean success = userService.updateUser(user);
			if (success) {
				LOG.info("Utilisateur {} mis à jour avec succès", user.getEmail());
			} else {
				LOG.warn("Impossible de mettre à jour l'utilisateur {}", user.getEmail());
			}
			return success;
		} catch (Exception e) {
			LOG.error("Erreur lors de la mise à jour de l'utilisateur : {}", e.getMessage());
			return false;
		}
	}
	
	/**
     * Deletes a user by their identifier.
     *
     * @param userId user identifier
     * @return true if deletion succeeds, false otherwise
     */
	public boolean userDeleteController(int userId) {
		return userService.deleteUser(userId);
	}
	
	/**
     * Retrieves a user by their identifier.
     *
     * @param id user identifier
     * @return corresponding Userdto or null if not found
     */
	public Userdto getEventByIdController(int id) {
		return userService.getUserById(id);
	}
	/**
     * Retrieves all users.
     *
     * @return list of Userdto
     */
	public List<Userdto> getAllUsers() {
		return userService.getAllUsers();
	}

	private static Userdto currentUser;
	 /**
     * Sets the currently logged-in user.
     *
     * @param user current user
     */
	public static void setCurrentUser(Userdto user) {
		currentUser = user;
	}

    /**
     * Returns the currently logged-in user.
     *
     * @return current Userdto or null if no user is logged in
     */
	public static Userdto getCurrentUser() {
		return currentUser;
	}
	
	/**
     * Searches for users based on their role.
     *
     * @param role the role to search for
     * @return list of Userdto matching the role, or empty list if no results
     */
	public List<Userdto> findUsersByRoleController(String role) {
	    if (role == null || role.isEmpty()) {
	        LOG.error("Rôle fourni est vide !");
	        return new ArrayList<>(); 
	    }

	    List<Userdto> users = userService.findUsersByRole(role);

	    if (users == null || users.isEmpty()) {
	        LOG.info("Aucun utilisateur trouvé avec le rôle : " + role);
	    } else {
	        LOG.info(users.size() + " utilisateur(s) trouvé(s) avec le rôle : " + role);
	    }

	    return users;
	}
	


}
