package controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import model.dto.Userdto;
import model.mapping.MappingService.MappingUser;
import model.mapping.MappingServiceImpl.MappingUserImpl;
import model.entity.Users;
import model.service.AuthenticationService;
import model.service.implementation.AuthenticationServiceImpl;
import util.constants.Constants;

/**
 * Authentication management controller.
 *
 * This class acts as a link between the View layer (user interfaces) and the
 * Service layer (business logic) for operations related to user authentication
 * and registration.
 * 
 * @author Severin Kengne
 * @version 1.0
 */

public class AuthenticationController {

	private AuthenticationService service = new AuthenticationServiceImpl();
	private MappingUser mapper = new MappingUserImpl();

	private static final Logger LOG = LogManager.getLogger(AuthenticationController.class);

	/**
	 * Registers a new user in the system.
	 *
	 * This method converts a {@link Userdto} into a {@link Users} entity, delegates
	 * the persistence logic to the {@link AuthenticationService}, and then returns
	 * the created {@link Userdto} if successful.
	 *
	 * @param userdto The {@link Userdto} containing user details to be registered.
	 * @return The registered {@link Userdto} if successful, or {@code null} if
	 *         registration failed.
	 */

	public Userdto RegisterController(Userdto userdto) {
		Users user = mapper.convertUserdtoToUsers(userdto);
		Userdto dto = service.registerUser(user);
		if (dto == null) {
			LOG.error(Constants.EMPTY_USER_DTO);
			return null;
		}
		return dto;
	}

}
