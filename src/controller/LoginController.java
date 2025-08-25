package controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import model.dto.Userdto;
import model.entity.Users;
import model.service.AuthenticationService;
import model.service.implementation.AuthenticationServiceImpl;
import util.constants.Constants;
import views.DashboardSessionAdmin;



public class LoginController {
	private static AuthenticationService authService = new AuthenticationServiceImpl();
	
	 private static final Logger LOG = LogManager.getLogger(LoginController.class);

	public static void loginUser(String email, String password) {
		
		Userdto user = authService.loginUser(email, password);
        if (user != null) {
           LOG.info("Connexion réussie !");
            DashboardSessionAdmin.SessionAdminMenu();
        } else {
            LOG.error(Constants.AUTHENTICATION_FAILED);
        }
    }
		


}
