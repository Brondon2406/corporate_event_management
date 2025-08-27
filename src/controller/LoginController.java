package controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import model.dto.Userdto;
import model.service.AuthenticationService;
import model.service.implementation.AuthenticationServiceImpl;
import util.constants.Constants;
import views.sessionAdmin.DashboardSessionAdmin;
import views.admin.DashboardAdmin;


	public class LoginController {
		private static AuthenticationService AuthenticationService = new AuthenticationServiceImpl();
	
	 	private static final Logger LOG = LogManager.getLogger(LoginController.class);

	 	public void loginUser(String email, String password) {
		
			Userdto user = AuthenticationService.loginUser(email, password);
        	if (user == null) {
        	LOG.error(Constants.AUTHENTICATION_FAILED);
        	} else {
            	LOG.info("Connexion réussie !");
            	if ("ADMIN".equalsIgnoreCase(user.getRole())) {
            		DashboardAdmin.AdminMenu();
            	} else if ("COLLABORATORS".equalsIgnoreCase(user.getRole())) {
            		DashboardSessionAdmin.SessionAdminMenu(user, null);            	            	
            	} else if ("ANIMATORS".equalsIgnoreCase(user.getRole())) {
            		DashboardSessionAdmin.SessionAdminMenu(user, null);
            	 } else {
            	 LOG.error(Constants.AUTHENTICATION_FAILED + user.getRole());
                 }
            }       		
	}
}