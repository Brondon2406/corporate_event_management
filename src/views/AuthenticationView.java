package views;
import java.util.List;
import java.util.Scanner;
import controller.AuthenticationController;
import controller.dto.Userdto;
import model.entity.enumeration.Role;

public class AuthenticationView {
    
    public static boolean checkEmail(String email) {
        if (email == null)
            return false;
        String regex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
        return email.matches(regex);
    }
    
    public static boolean checkName(String nom) {
        if (nom == null || nom.trim().isEmpty())
            return false;
        return nom.matches("^[A-Za-zÀ-ÖØ-öø-ÿ ]+$");
    }
    
    private static Scanner scanner = new Scanner(System.in);
    AuthenticationController controller = new AuthenticationController();
    
    public AuthenticationView() {
    }
    
    public void Authenticate() {
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
        System.out.println("Liste des roles : ");
        int index = 0;
        for (String role : roles) {
            System.out.println((index + 1) + "- " + role);
            index++;
        }
        System.out.print("Choisissez un rôle (numéro): ");
        String roleInput = scanner.nextLine();

        System.out.print("Entrez votre fonction: ");
        String fonction = scanner.nextLine();
       
        Userdto dto = new Userdto();
        dto.setName(name);
        dto.setEmail(email);
        dto.setPassword(password);
        dto.setFonction(fonction);
        
       
    }
}