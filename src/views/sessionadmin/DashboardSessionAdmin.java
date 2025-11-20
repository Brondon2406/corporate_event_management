package views.sessionadmin;

import java.util.Scanner;
import model.dto.Userdto;
import views.MainView;

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
			System.out.println("5 - Envoyer une notification");
			System.out.println("6 - Modifier un événement");
			System.out.println("7 - Supprimer un événement");
			System.out.println("8 - Rechercher un événement par son Id ");
			System.out.println("9 - Lister les événements");
			System.out.println("10 - Consulter les évènements Assignées");
			System.out.println("0 - Se deconnecter");

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
				sessionview.sendNotification();
				break;

			case 6:
				sessionview.updateEvent();
				break;

			case 7:
				sessionview.deleteEvent();
				break;

			case 8:
				sessionview.searchEventById();
				break;

			case 9:
				sessionview.listEvents();
				break;

			case 10:
				sessionview.viewAssignedEvents();
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
