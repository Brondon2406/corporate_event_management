package views;

import java.util.Scanner;

import model.service.scheduler.EventStatusScheduler;

public class MainView {

	public static void main(String[] args) {
		EventStatusScheduler scheduler = new EventStatusScheduler();
		scheduler.start(30, 30);
		AuthenticationView view = new AuthenticationView();
		Scanner scanner = new Scanner(System.in);
		boolean continuer = true;

		System.out.println("=== Application de Gestion D'Evenements ===");

		while (continuer) {
			System.out.println("\nMenu principal :");
			System.out.println("1 - Inscription");
			System.out.println("2 - Connexion");
			System.out.println("3 - Quitter");

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
				view.registration();
				break;
			case 2:
				view.connection();
				break;
			case 3:
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
