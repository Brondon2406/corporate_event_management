package views;
import java.util.List;
import java.util.Scanner;

import controller.AuthenticationController;
import controller.LoginController;
import model.dto.Userdto;
import model.entity.enumeration.Role;

public class AuthenticationView {
	private static Scanner scanner = new Scanner(System.in);
    AuthenticationController controller = new AuthenticationController();
    
    public void registration() {
        System.out.println("\n=== Inscription ===");
        
        String name;       
        do {
            System.out.print("Entrez votre nom : ");
            name = scanner.nextLine().trim();
            if (!checkName(name)) {
                System.out.println("Nom invalide ! (pas de chiffres ni de symboles)");
            }
        } while (!checkName(name));
        
        String email;      
        do {
            System.out.print("Entrez votre email : ");
            email = scanner.nextLine();
            if (!checkEmail(email)) {
                System.out.println("Email incorrect ! Veuillez réessayer.");
            }
        } while (!checkEmail(email));

        System.out.print("Entrez votre mot de passe: ");
        String password = scanner.nextLine();

        List<String> roles = Role.getUserRoles();
        System.out.println("Liste des rôles disponibles : ");
        int index = 0;
        for (String roleItem : roles) {
            System.out.println((index + 1) + "- " + roleItem);
            index++;
        }

        String role = null;
        boolean choixValide = false;

        while (!choixValide) {
            System.out.print("Choisissez un rôle (numéro entre 1 et " + roles.size() + ") : ");

            if (scanner.hasNextInt()) { 
                int roleIndex = scanner.nextInt();

                if (roleIndex >= 1 && roleIndex <= roles.size()) {
                    role = roles.get(roleIndex - 1);
                    choixValide = true;
                } else {
                    System.out.println(" Numéro invalide ! Veuillez choisir un nombre entre 1 et " + roles.size() + ".");
                }
            } else {
                System.out.println("Entrée invalide ! Veuillez entrer un chiffre.");
                scanner.next();
            }
        }

        String fonction = "";
        do {
        	System.out.print("Entrez votre fonction: ");
            fonction = scanner.nextLine().trim();
        } while (fonction.isEmpty());
       
        Userdto dto = new Userdto();
        dto.setName(name);
        dto.setEmail(email);
        dto.setPassword(password);
        dto.setRole(role);
        dto.setFonction(fonction);
        
        dto = controller.RegisterController(dto);
        if(dto == null) {
        	System.out.print("Choisissez un rôle (numéro): ");
        }
       
    }
    
    private boolean checkEmail(String email) {
        if (email == null)
            return false;
        String regex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
        return email.matches(regex);
    }
    
    private boolean checkName(String name) {
        if (name == null || name.trim().isEmpty())
            return false;
        return name.matches("^[A-Za-zÀ-ÖØ-öø-ÿ ]+$");
    }
    
    public void connection () {
    	System.out.println("\n=== CONNEXION ===");
    	
    	 System.out.print("Entrez votre email: ");
         String email = scanner.nextLine();
         
         System.out.print("Entrez votre mot de passe: ");
         String password = scanner.nextLine();
         
         LoginController controller = new LoginController();
         controller.loginUser(email, password);
    }
}