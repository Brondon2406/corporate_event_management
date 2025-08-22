package views;

import java.util.Scanner;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import controller.AuthenticationController;

public class DashboardAdmin {

    private static Scanner scanner = new Scanner(System.in);
    AuthenticationController controller = new AuthenticationController();
    private static final Logger LOG = LogManager.getLogger(DashboardAdmin.class);

    public void showMenu() {
        boolean continuer = true;

        System.out.println("========== Tableau de bord ==========");

        while (continuer) {
            System.out.println("\nMenu principal :");
            System.out.println("1 - Créer un planning");
            System.out.println("2 - Créer un événement");
            System.out.println("3 - Modifier un événement");
            System.out.println("4 - Supprimer un événement");
            System.out.println("5 - Modifier un utilisateur");
            System.out.println("6 - Supprimer un utilisateur");
            System.out.println("7 - Quitter");

            System.out.print("Choisissez une option : ");
            int choix = scanner.nextInt();
            scanner.nextLine(); 

            switch (choix) {
                case 1:
                    LOG.info("Création d’un planning ");
                    
                    break;
                case 2:
                    LOG.info("Création d’un événement ");
                   
                    break;
                case 3:
                    LOG.info("Modification d’un événement ");
                    
                    break;
                case 4:
                    LOG.info("Suppression d’un événement ");
                    
                    break;
                case 5:
                    LOG.info("Modification d’un utilisateur ");
                    
                    break;
                case 6:
                    LOG.info("Suppression d’un utilisateur ");
                    
                    break;
                case 7:
                    System.out.println("Au revoir !");
                    continuer = false;
                    break;
                default:
                    System.out.println("Choix invalide, veuillez réessayer.");
                    break;
            }
        }

        scanner.close();
    }

    public static void main(String[] args) {
        DashboardAdmin dashboard = new DashboardAdmin();
        dashboard.showMenu();
    }
}
