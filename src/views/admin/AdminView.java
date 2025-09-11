package views.admin;

import java.util.List;
import java.util.Scanner;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import controller.UserController;

import model.dto.Userdto;

import model.entity.enumeration.Role;

public class AdminView {
	private static final Logger LOG = LogManager.getLogger(UserController.class);
	private static final Scanner scanner = new Scanner(System.in);
	private static final UserController userController = new UserController();

	public void modifyProfile() {
		System.out.println("\n=== Modification du profil ===");

		Userdto currentUser = UserController.getCurrentUser();
		if (currentUser == null) {
			System.out.println("Erreur : aucun utilisateur connecté !");
			return;
		}

		System.out.print("Nom actuel : " + currentUser.getName() + " | Nouveau nom (laisser vide pour conserver) : ");
		String name = scanner.nextLine().trim();
		if (!name.isEmpty() && checkName(name)) {
			currentUser.setName(name);
		}

		System.out.print(
				"Prénom actuel : " + currentUser.getFirstName() + " | Nouveau prénom (laisser vide pour conserver) : ");
		String firstName = scanner.nextLine().trim();
		if (!firstName.isEmpty() && checkName(firstName)) {
			currentUser.setFirstName(firstName);
		}

		System.out.print(
				"Email actuel : " + currentUser.getEmail() + " | Nouveau email (laisser vide pour conserver) : ");
		String email = scanner.nextLine().trim();
		if (!email.isEmpty() && checkEmail(email)) {
			currentUser.setEmail(email);
		}

		System.out.print("Nouveau mot de passe (laisser vide pour conserver) : ");
		String password = scanner.nextLine().trim();
		if (!password.isEmpty()) {
			currentUser.setPassword(password);
		}

		System.out.print("Fonction actuelle : " + currentUser.getFonction()
				+ " | Nouvelle fonction (laisser vide pour conserver) : ");
		String fonction = scanner.nextLine().trim();
		if (!fonction.isEmpty()) {
			currentUser.setFonction(fonction);
		}

		boolean success = userController.updateUserController(currentUser);
		if (success) {
			System.out.println("Profil mis à jour avec succès !");
		} else {
			System.out.println("Erreur lors de la mise à jour du profil.");
		}
	}

	private boolean checkName(String name) {
		if (name == null || name.trim().isEmpty())
			return false;
		return name.matches("^[A-Za-zÀ-ÖØ-öø-ÿ ]+$");
	}

	private boolean checkEmail(String email) {
		if (email == null)
			return false;
		String regex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
		return email.matches(regex);
	}

	public void creatUser() {
		System.out.println("\n=== Inscription ===");

		String name;
		do {
			System.out.print("Entrez Le Nom du nouvel l'utilisateur : ");
			name = scanner.nextLine().trim();
			if (!checkName(name)) {
				System.out.println("Nom invalide ! (pas de chiffres ni de symboles)");
			}
		} while (!checkName(name));

		String firstName;
		do {
			System.out.print("Entrez le prénom du nouvel l'utilisateur: ");
			firstName = scanner.nextLine().trim();
			if (!checkName1(firstName)) {
				System.out.println("Nom invalide ! (pas de chiffres ni de symboles)");
			}
		} while (!checkName(firstName));

		String email;
		do {
			System.out.print("Entrez le email du nouvel l'utilisateur : ");
			email = scanner.nextLine();
			if (!checkEmail(email)) {
				System.out.println("Email incorrect ! Veuillez réessayer.");
			}
		} while (!checkEmail1(email));

		System.out.print("Entrez Le mot de passe : ");
		String password = scanner.nextLine();

		List<String> roles = Role.getUserRoles();
		System.out.println("Liste des rôles disponibles : ");
		for (int i = 0; i < roles.size(); i++) {
			System.out.println((i + 1) + "- " + roles.get(i));
		}

		String role = null;
		boolean choixValide = false;
		while (!choixValide) {
			System.out.print("Choisissez un rôle (numéro entre 1 et " + roles.size() + ") : ");
			if (scanner.hasNextInt()) {
				int roleIndex = scanner.nextInt();
				scanner.nextLine();
				if (roleIndex >= 1 && roleIndex <= roles.size()) {
					role = roles.get(roleIndex - 1);
					choixValide = true;
				} else {
					System.out.println("Numéro invalide ! Veuillez choisir un nombre entre 1 et " + roles.size() + ".");
				}
			} else {
				System.out.println("Entrée invalide ! Veuillez entrer un chiffre.");
				scanner.nextLine();
			}
		}

		String fonction;
		do {
			System.out.print("Entrez votre fonction: ");
			fonction = scanner.nextLine().trim();
		} while (fonction.isEmpty());

		Userdto dto = new Userdto();
		dto.setName(name);
		dto.setFirstName(firstName);
		dto.setEmail(email);
		dto.setPassword(password);
		dto.setRole(role);
		dto.setFonction(fonction);

		dto = userController.userCreateController(dto);
		if (dto == null) {
			System.out.println("Erreur lors de l'inscription ! Veuillez réessayer.");

		} else {
			System.out.println("Utilisateur créé avec succès : " + dto.getEmail());

		}
	}

	private boolean checkEmail1(String email) {
		if (email == null)
			return false;
		String regex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
		return email.matches(regex);
	}

	private boolean checkName1(String name) {
		if (name == null || name.trim().isEmpty())
			return false;
		return name.matches("^[A-Za-zÀ-ÖØ-öø-ÿ ]+$");
	}

	public void deleteUser() {
		System.out.print("ID de l'événement à supprimer : ");
		int id = Integer.parseInt(scanner.nextLine());

		boolean success = userController.userDeleteController(id);
		if (success) {
			LOG.info("Utilisateur supprimé avec succès !");
		} else {
			LOG.error("Erreur lors de la suppression.");
		}
	}

	public void searchUserById() {
		System.out.print("Entrez l'ID de l'Utilisateur à rechercher : ");
		int id = Integer.parseInt(scanner.nextLine());

		Userdto user = userController.getEventByIdController(id);

		if (user != null) {
			System.out.println("\n===== Détails de l'événement =====");
			System.out.println("ID : " + user.getId());
			System.out.println("Nom : " + user.getName());
			System.out.println("Prénom : " + user.getFirstName());
			System.out.println("Email : " + user.getEmail());
			System.out.println("Role : " + user.getRole());
			System.out.println("Fonction : " + user.getFonction());
			System.out.println("-------------------------------");
		} else {
			System.out.println(" Aucun Utilisateur trouvé avec cet ID.");
		}
	}

	public void searchUsersByRole() {
		System.out.println("\n=== Rechercher des utilisateurs par rôle ===");

		List<String> roles = Role.getUserRoles();
		System.out.println("Liste des rôles disponibles : ");
		for (int i = 0; i < roles.size(); i++) {
			System.out.println((i + 1) + "- " + roles.get(i));
		}

		String role = null;
		boolean choixValide = false;
		while (!choixValide) {
			System.out.print("Choisissez un rôle (numéro entre 1 et " + roles.size() + ") : ");
			if (scanner.hasNextInt()) {
				int roleIndex = scanner.nextInt();
				scanner.nextLine();
				if (roleIndex >= 1 && roleIndex <= roles.size()) {
					role = roles.get(roleIndex - 1);
					choixValide = true;
				} else {
					System.out.println("Numéro invalide ! Veuillez choisir un nombre entre 1 et " + roles.size() + ".");
				}
			} else {
				System.out.println("Entrée invalide ! Veuillez entrer un chiffre.");
				scanner.nextLine();
			}
		}

		List<Userdto> users = userController.findUsersByRoleController(role);

		if (users == null || users.isEmpty()) {
			System.out.println("Aucun utilisateur trouvé avec le rôle : " + role);
		} else {
			System.out.println("\nUtilisateurs avec le rôle " + role + " :");
			for (Userdto user : users) {
				System.out.println("- ID: " + user.getId() + ", Nom: " + user.getName() + " " + user.getFirstName()
						+ ", Email: " + user.getEmail());
			}
		}
	}

	public void listAllUsers() {

		var users = userController.getAllUsers();

		if (users.isEmpty()) {
			System.out.println("Aucun utilisateur trouvé.");
		} else {
			System.out.println("\n=== Liste de tous les utilisateurs ===");
			for (Userdto user : users) {
				System.out.println(" ID=" + user.getId());
				System.out.println(" Nom=" + user.getName() + user.getFirstName());
				System.out.println(" Email= " + user.getEmail());
				System.out.println(" Rôle: " + user.getRole());
				System.out.println(" Fonction: " + user.getFonction());
				System.out.println("----------------------------");

			}
		}
	}

	public void listPendingEvents() {

	}

	public void listRejectedEvents() {

	}

	public void listCanceledEvents() {

	}

	public void sendNotification() {

	}

	public void listExpiredEvents() {

	}

	public void viewStatistics() {

	}

	public void manageGlobalEvents() {

	}
}