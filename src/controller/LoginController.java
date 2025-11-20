package controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import model.dto.Userdto;
import model.service.AuthenticationService;
import model.service.implementation.AuthenticationServiceImpl;
import util.constants.Constants;
import views.sessionadmin.DashboardSessionAdmin;
import views.admin.DashboardAdmin;
import views.planningmanager.DashboardPlanningManager;

public class LoginController {

    private static final Logger LOG = LogManager.getLogger(LoginController.class);
    private final AuthenticationService authenticationService = new AuthenticationServiceImpl();

    /**
     * Allows a user to log in.
     * @param email User's email address
     * @param password User's password
     * @return Userdto if login is successful, otherwise null
     */
    public Userdto loginUser(String email, String password) {
        Userdto user = authenticationService.loginUser(email, password);

        if (user == null) {
            LOG.error(Constants.AUTHENTICATION_FAILED);
            return null;
        }

        UserController.setCurrentUser(user);
        LOG.info("Connexion réussie pour : {}", user.getEmail());

        String role = user.getRole() != null ? user.getRole().toUpperCase() : "";

        switch (role) {
            case "ADMIN":
                DashboardAdmin.AdminMenu();
                break;

            case "PLANNING_ROOM_MANAGER":
                DashboardPlanningManager.PlanningManagerMenu();
                break;

            case "COLLABORATORS":
            case "ANIMATORS":
                DashboardSessionAdmin.SessionAdminMenu(user);
                break;

            default:
                LOG.error(Constants.AUTHENTICATION_FAILED + " Rôle inconnu : " + role);
                break;
        }

        return user;
    }

    /**
     * Retrieves the currently logged-in user.
     * @return Logged-in user or null if no user is logged in.
     */
    public static Userdto getCurrentUser() {
        return UserController.getCurrentUser();
    }
}
