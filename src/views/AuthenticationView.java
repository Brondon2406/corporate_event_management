package views;

import java.util.List;
import java.util.Scanner;

import controller.AuthenticationController;
import controller.dto.Userdto;
import model.entity.enumeration.Role;

public class AuthenticationView {

	private static Scanner scanner = new Scanner(System.in);
	AuthenticationController controller = new AuthenticationController();
	
	public AuthenticationView() {}

	public void Authenticate() {
		System.out.println("\n=== Inscription ===");

	    System.out.print("Entrez votre nom : ");
	    String nom = scanner.nextLine().trim();
	    
	    System.out.print("Entrez votre email : ");
	    String email = scanner.nextLine().trim();
	    System.out.print("Entrez votre mot de passe:");
	    String password = scanner.nextLine();
	    
	    List<String> roles = Role.getUserRoles();
	    System.out.println("Liste des roles : ");
	    int index = 0;
	    for (String role : roles) {
	    	System.out.println(index + 1 +"- " + role);
			index ++;
		}
	    String role = scanner.nextLine();
	    
	    System.out.print("Entrez votre fonction: ");
	    String fonction = scanner.nextLine();
	    
	    Userdto dto = controller.AuthenticationController(new Userdto());
	}
}
