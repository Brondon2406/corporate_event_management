package views.admin;

import java.util.Scanner;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import controller.AuthenticationController;

public class DashboardAdmin {
	private static Scanner scanner = new Scanner(System.in);
    AuthenticationController controller = new AuthenticationController();
    private static final Logger LOG = LogManager.getLogger(DashboardAdmin.class);

    public static void AdminMenu() {
        boolean continuer = true;

        System.out.println("=================== TABLEAU DE BORD ===================");

        while (continuer) {
            System.out.println("\n============== Gestion des utilisateurs ==============");
            System.out.println("1 - Modifier un Utilisateur ");
            System.out.println("2 - Supprimer un Utilisateur");
            System.out.println("3 - Rechercher un Utilisateur par son Id ");
            System.out.println("4 - Lister les utlisateurs");
            
            System.out.println("\n============== Gestion des Planning ==============");
            System.out.println("5 - Ajouter un planning ");
            System.out.println("6 - Modiffier un planning");
            System.out.println("7 - Supprimer un planning ");
            System.out.println("8 - Lister les Evenemants par periode");
            
            System.out.println("\n============== Gestion des Gestion des salles ==============");
            System.out.println("9 - Ajouter un Salle ");
            System.out.println("10 - Modiffier un Salle");
            System.out.println("11 - Supprimer un Salle ");
            System.out.println("12 - Rechercher un Salle par son Id");
            System.out.println("13 - Lister les Salles");
            
            System.out.println("\n14 - Se deconnecter");

            System.out.print("Choisissez une option : ");
            int choix = scanner.nextInt();
            scanner.nextLine(); 

            switch (choix) {
                case 1:
                    LOG.info("Modifier un Utilisateur ");
                    
                    break;
                case 2:
                    LOG.info("Supprimer un Utilisateur");
                   
                    break;
                case 3:
                    LOG.info("Rechercher un Utilisateur par son Id  ");
                    
                    break;
                case 4:
                    LOG.info("Lister les utlisateurs ");
                    
                    break;
                case 5:
                    LOG.info("Ajouter un planning  ");
                    break;
                    
                case 6:
                    LOG.info("Modifier un planning  ");
                    break;
                    
                case 7:
                    LOG.info("Supprimer un planning  ");
                    break;
                case 8:
                    LOG.info("Lister les Evenemants par periode  ");
                    break;
                    
                case 9:
                    LOG.info("Ajouter un Salle  ");
                    break;
                    
                case 10:
                    LOG.info("Modifier un Salle  ");
                    break;
                    
                case 11:
                    LOG.info("Supprimer un Salle  ");
                    break;
                    
                case 12:
                    LOG.info("Rechercher la salle par son Id  ");
                    break;
                    
                case 13:
                    LOG.info("Lister les Salles  ");
                    break;
                    
               
                case 14:
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

}
