package views.admin;

import java.util.InputMismatchException;
import java.util.Scanner;
import views.MainView;

public class DashboardAdmin {

    private static final Scanner scanner = new Scanner(System.in);

    public static void AdminMenu() {
        AdminView adminView = new AdminView();
        boolean continuer = true;

        System.out.println("=================== TABLEAU DE BORD ADMIN ===================");

        while (continuer) {

            System.out.println("\n============== Gestion des utilisateurs ==============");
            System.out.println("1 - Modifier mon profil");
            System.out.println("2 - Créer un utilisateur");
            System.out.println("3 - Supprimer un utilisateur");
            System.out.println("4 - Rechercher un utilisateur par ID");
            System.out.println("5 - Rechercher les utilisateurs par rôle");
            System.out.println("6 - Lister tous les utilisateurs");

            System.out.println("\n============== Gestion des événements ==============");
            System.out.println("7 - Modifier le statut d'un événement");
            System.out.println("8 - Liste des événements à valider (en attente)");
            System.out.println("9 - Liste des événements validés");
            System.out.println("10 - Liste des événements rejetés");
            System.out.println("11 - Liste des événements expirés");
            System.out.println("12 - Voir les statistiques et rapports");

            System.out.println("\n0 - Se déconnecter");

            int choix = -1;

            try {
                System.out.print("\nEntrez votre choix : ");
                choix = scanner.nextInt();
                scanner.nextLine(); 
            } catch (InputMismatchException e) {
                System.out.println("Erreur : veuillez entrer un nombre valide !");
                scanner.nextLine();
                continue;
            }

            switch (choix) {
                case 1:
                    adminView.modifyProfile();
                    break;
                case 2:
                    adminView.creatUser();
                    break;
                case 3:
                    adminView.deleteUser();
                    break;
                case 4:
                    adminView.searchUserById();
                    break;
                case 5:
                    adminView.searchUsersByRole();
                    break;
                case 6:
                    adminView.listAllUsers();
                    break;
                case 7:
                    adminView.modifyEventStatus();
                    break;
                case 8:
                    adminView.listPendingEvents();
                    break;
                case 9:
                    adminView.listValidatedEvents();
                    break;
                case 10:
                    adminView.listRejectedEvents();
                    break;
                case 11:
                    adminView.listExpiredEvents();
                    break;
                case 12:
                    adminView.viewStatistics();
                    break;
                case 0:
                    System.out.println("Déconnecté avec succès !");
                    continuer = false;
                    MainView.main(null);
                    break;
                default:
                    System.out.println("Choix invalide ! Veuillez réessayer.");
            }
        }
    }
}
