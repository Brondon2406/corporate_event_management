package views.sessionAdmin;

import java.util.Scanner;
import model.dto.Userdto;
import model.entity.EventRoom;

public class DashboardSessionAdmin {
    private static Scanner scanner = new Scanner(System.in);

    public static void SessionAdminMenu(Userdto userDTO,  EventRoom room) {
    	
    	SessionAdminView sessionview = new SessionAdminView();
        boolean continuer = true;

        System.out.println("========== Tableau de bord ==========");

        while (continuer) {
            System.out.println("\nMenu principal :");
            System.out.println("1 - Modifier mon profil ");
            System.out.println("2 - Créer un événement");
            System.out.println("3 - Modifier un événement");
            System.out.println("4 - Supprimer un événement");
            System.out.println("5 - Rechercher un evenement par son Id ");
            System.out.println("6 - Se deconnecter");

            System.out.print("Choisissez une option : ");
            int choix = scanner.nextInt();
            scanner.nextLine(); 

            switch (choix) {
                case 1:
                	sessionview.updateUser(userDTO);
                    
                    break;
                case 2:
                	sessionview.registEvent(userDTO, room);
                   
                    break;
                case 3:
                   // LOG.info("Modification d’un événement ");
                    
                    break;
                case 4:
                  //  LOG.info("Suppression d’un événement ");
                    
                    break;
                case 5:
                   // LOG.info("Rechercher un evenement par son Id  ");
                    break;
                case 6:
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
