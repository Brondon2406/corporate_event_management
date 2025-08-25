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
import views.DashboardSessionAdmin;

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
			DashboardSessionAdmin dashboard = new DashboardSessionAdmin();
            dashboard.SessionAdminMenu();
        } else {
            LOG.error(" Email ou mot de passe incorrect !");
            return null;
        }
        return userdto;
    }
}
