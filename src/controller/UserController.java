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


	public Userdto userCreateController(Userdto userdto) {

		Userdto dto = userService.createUser(userdto);
		if (dto == null) {
			LOG.error(Constants.EMPTY_USER_DTO);
			return null;
		}
		return dto;
	}

	/**
	 * Met à jour les informations d'un utilisateur
	 * 
	 * @param user Userdto contenant les nouvelles informations
	 * @return true si la mise à jour a réussi, false sinon
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

	public static Userdto getCurrentUser1() {

		return null;
	}

	public boolean userDeleteController(int userId) {
		return userService.deleteUser(userId);
	}

	public Userdto getEventByIdController(int id) {
		return userService.getUserById(id);
	}
	
	public List<Userdto> getAllUsers() {
		return userService.getAllUsers();
	}

	private static Userdto currentUser;

	public static void setCurrentUser(Userdto user) {
		currentUser = user;
	}

	public static Userdto getCurrentUser() {
		return currentUser;
	}

	public List<Userdto> findUsersByRoleController(String role) {
	    if (role == null || role.isEmpty()) {
	        LOG.error("Rôle fourni est vide !");
	        return new ArrayList<>(); // renvoyer une liste vide
	    }

	    // Appel au service
	    List<Userdto> users = userService.findUsersByRole(role);

	    if (users == null || users.isEmpty()) {
	        LOG.info("Aucun utilisateur trouvé avec le rôle : " + role);
	    } else {
	        LOG.info(users.size() + " utilisateur(s) trouvé(s) avec le rôle : " + role);
	    }

	    return users;
	}


}
