package controller;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import model.dto.Userdto;
import model.entity.Users;
import model.mapping.MappingService.MappingUser;
import model.mapping.MappingServiceImpl.MappingUserImpl;
import model.service.UserService;
import model.service.implementation.UserServiceImpl;
import util.constants.Constants;

public class UserController {
	private static final Logger LOG = LogManager.getLogger(UserController.class);
	private static UserService userService = new UserServiceImpl();
	private MappingUser mapper = new MappingUserImpl();

	public boolean userCreateController(Userdto userdto) {
		Users user = mapper.convertUserdtoToUsers(userdto);

		if (userdto == null) {
			LOG.error(Constants.EMPTY_USER_DTO);
			return false;
		}

		Userdto dto = userService.createUser(user);

		if (dto != null) {
			LOG.info("utilisateur créé avec succès !");
			return true;
		} else {
			LOG.error(Constants.ERROR_DURING_USER_INSERTION);
			return false;
		}
	}

	public void updateUserController(Userdto userDTO, String newName, String newEmail, String newPassword) {
		userDTO.setName(newName);
		userDTO.setEmail(newEmail);
		userDTO.setPassword(newPassword);

		boolean success = userService.updateUser(userDTO);
		if (success) {
			LOG.info("Profil mis à jour avec succès !");
		} else {
			LOG.error(Constants.ERROR_UPDATE_USER);
		}
	}

	public boolean userDeleteController(int eventId) {
		return userService.deleteUser(eventId);
	}

	public Userdto getEventByIdController(int id) {
		return userService.getUserById(id);
	}

	public static List<Userdto> getAllUsers() {
		return userService.getAllUsers();
	}
}
