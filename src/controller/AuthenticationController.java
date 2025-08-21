package controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import controller.dto.Userdto;
import controller.utilities.MappingService.MappingUser;
import controller.utilities.MappingServiceImpl.MappingUserImpl;
import model.entity.Users;
import model.service.AuthenticationService;
import model.service.implementation.AuthenticationServiceImpl;
import util.Constants;

public class AuthenticationController {

	private AuthenticationService service = new AuthenticationServiceImpl();
	private MappingUser mapper =  new MappingUserImpl();
	
	private static final Logger LOG = LogManager.getLogger(AuthenticationController.class);
	
	public AuthenticationController() {}

	public Userdto RegisterController(Userdto userdto) {
		if(userdto == null) {
			LOG.error(Constants.EMPTY_USER_DTO);
			return null;
		}
		Users user = mapper.convertUserdtoToUsers(userdto);
		Userdto dto = service.registerUser(user);
		if(dto == null) {
			LOG.error(Constants.EMPTY_USER_DTO);
			return null;
		}
		return dto;
	}
	
	public Userdto loginUserController (Userdto userdto) {
		if (userdto != null) {
			LOG.info(" Connexion réussie ! Bienvenue " + userdto.getEmail());
        } else {
            LOG.error(" Email ou mot de passe incorrect !");
            return null;
        }
        return userdto;
    }
}
