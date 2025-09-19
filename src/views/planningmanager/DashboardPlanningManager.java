package views.planningmanager;

import java.util.Scanner;

import controller.PlanningController;
import views.MainView;

public class DashboardPlanningManager {
	private static Scanner scanner = new Scanner(System.in);
	PlanningController controller = new PlanningController();

	public static void PlanningManagerMenu() {

		PlanningManagerView planningView = new PlanningManagerView();
		boolean continuer = true;

		System.out.println("=================== TABLEAU DE BORD ===================");

		while (continuer) {

			System.out.println("\n============== Gestion des Planning ==============");
			System.out.println("1 - Ajouter un planning ");
			System.out.println("2 - Modiffier un planning");
			System.out.println("3 - Supprimer un planning ");
			System.out.println("4 - Lister les Evenements par periode");

			System.out.println("\n============== Gestion des Gestion des salles ==============");
			System.out.println("5 - Ajouter un Salle ");
			System.out.println("6 - Modiffier un Salle");
			System.out.println("7 - Supprimer un Salle ");
			System.out.println("8 - Rechercher un Salle par son Id");
			System.out.println("9 - Lister les Salles");

			System.out.println("\n0 - Se deconnecter");

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
				planningView.creatPlanning();
				break;

			case 2:
				planningView.updatePlanning();
				break;

			case 3:
				planningView.deletePlanning();
				break;

			case 4:
				planningView.getEventByPeriod();
				break;

			case 5:
				planningView.creatRoom();
				break;

			case 6:
				planningView.updateRoom();
				break;

			case 7:
				planningView.deleteRoom();
				break;

			case 8:
				planningView.getRoomById();
				break;

			case 9:
				planningView.getAllRoom();
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
