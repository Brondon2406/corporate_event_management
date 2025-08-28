package controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import model.dto.Userdto;
import model.service.UserService;
import model.service.implementation.UserServiceImpl;
import util.constants.Constants;

public class UserController {
	private static final Logger LOG = LogManager.getLogger(UserController.class);
	private UserService userService = new UserServiceImpl();

	public void updateUser(Userdto userDTO, String newName, String newEmail, String newPassword) {
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
}
