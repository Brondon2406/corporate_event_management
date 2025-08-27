package views.sessionAdmin;

import java.util.Scanner;

import controller.SessionAdminController;
import model.dto.Userdto;

public class SessionAdminView {
	private static final Scanner scanner = new Scanner(System.in);
	 
	public void updateUser (Userdto userDTO) {

	    	System.out.println("\n========== MON PROFIL ==========");
	    	
	    	System.out.print(" Entrez votre nouveau nom : ");
		        String newName = scanner.nextLine();
	       
	        System.out.print(" Entrez votre nouvel email : ");
	        String newEmail = scanner.nextLine();
	        
	        System.out.print(" Entrez votre nouveau mot de passe : ");
	        String newPassword = scanner.nextLine();

	        SessionAdminController sessionAdminController = new SessionAdminController();
			sessionAdminController.updateUser(userDTO, newName, newEmail, newPassword);
	    }
	}
