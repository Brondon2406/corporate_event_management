package views.sessionadmin;

import java.util.Scanner;
import model.dto.Userdto;

public class DashboardSessionAdmin {

	private static Scanner scanner = new Scanner(System.in);

	public static void SessionAdminMenu(Userdto user) {

		SessionAdminView sessionview = new SessionAdminView();
		boolean continuer = true;

		System.out.println("========== Tableau de bord ==========");

		while (continuer) {
			System.out.println("\nMenu principal :");
			System.out.println("1 - Modifier mon profil ");
			System.out.println("2 - Créer un événement");
			System.out.println("3 - Lier les utilisateurs à  un Evenement ");
			System.out.println("4 - Modifier les utilisateurs à  un Evenement ");
			System.out.println("5 - Modifier un événement");
			System.out.println("6 - Supprimer un événement");
			System.out.println("7 - Rechercher un evenement par son Id ");
			System.out.println("8 - Lister les événements");
			System.out.println("9 - Se deconnecter");

			int choix = -1;
			while (choix == -1) {
				System.out.print("Choisissez une option : ");
				String input = scanner.nextLine();

				try {
					choix = Integer.parseInt(input);
				} catch (NumberFormatException e) {
					System.out.println("Veuillez entrer un nombre valide .");
				}
			}

			switch (choix) {
			case 1:
				sessionview.updateUser(user);
				break;
				
			case 2:
				sessionview.registEvent();
				break;
				
			case 3:
				sessionview.linkUsersToEvent();
				break;
				
			case 4:
				sessionview.updateUsersToEvent();
				break;
				
			case 5:
				sessionview.updateEvent();
				break;
				
			case 6:
				sessionview.deleteEvent();
		        break;
		        
			case 7:
				sessionview.searchEventById();
				break;
				
			case 8:
				sessionview.listEvents();
				break;
				
			case 9:
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
