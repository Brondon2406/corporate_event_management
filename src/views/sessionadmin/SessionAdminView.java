package views.sessionadmin;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import controller.EventController;
import controller.EventRoomController;
import controller.UserController;

import model.dto.Eventdto;
import model.dto.Userdto;
import model.entity.EventRoom;
import model.entity.enumeration.Format;
import model.entity.enumeration.TypeEvent;

public class SessionAdminView {
	public static void main(String[] args) {
	}

	private static final Logger LOG = LogManager.getLogger(SessionAdminView.class);
	private static final Scanner scanner = new Scanner(System.in);
	private static final EventController eventController = new EventController();
	private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

	public void updateUser(Userdto userDTO) {

		System.out.println("\n========== MON PROFIL ==========");

		System.out.print(" Entrez votre nouveau nom : ");
		String newName = scanner.nextLine();

		System.out.print(" Entrez votre nouvel email : ");
		String newEmail = scanner.nextLine();

		System.out.print(" Entrez votre nouveau mot de passe : ");
		String newPassword = scanner.nextLine();

		UserController UserController = new UserController();
		UserController.updateUserController(userDTO, newName, newEmail, newPassword);
	}

	public void registEvent() {
		System.out.println("========== CREATION D'EVENEMENT ==========");

		String title;
		do {
			System.out.print("Entrez le nom : ");
			title = scanner.nextLine().trim();
			if (!checkTitle(title)) {
				System.out.println("Title invalide ! (pas de chiffres ni de symboles)");
			}
		} while (!checkTitle(title));

		LocalDateTime dateDebut = null;
		while (dateDebut == null) {
			System.out.print("Date de début (yyyy-MM-dd HH:mm) : ");
			String inputDebut = scanner.nextLine();
			try {
				dateDebut = LocalDateTime.parse(inputDebut, formatter);
			} catch (DateTimeParseException e) {
				System.out.println("Format invalide ! Exemple attendu : 2025-01-01 07:30");
			}
		}

		LocalDateTime dateFin = null;
		while (dateFin == null || dateFin.isBefore(dateDebut)) {
			System.out.print("Date de fin (yyyy-MM-dd HH:mm) : ");
			String inputFin = scanner.nextLine();
			try {
				dateFin = LocalDateTime.parse(inputFin, formatter);
				if (dateFin.isBefore(dateDebut)) {
					System.out.println(" La date de fin doit être après la date de début !");
				}
			} catch (DateTimeParseException e) {
				System.out.println("Format invalide ! Exemple attendu : 2025-01-01 07:30");
			}
		}

		if (dateFin.isBefore(dateDebut)) {
			LOG.error("La date de fin doit être après la date de début !");
			return;
		}

		List<String> typeEvents = TypeEvent.getAllTypeEvent();
		System.out.println("Liste des Types d'événement : ");
		for (int i = 0; i < typeEvents.size(); i++) {
			System.out.println((i + 1) + "- " + typeEvents.get(i));
		}

		int typeEventIndex = -1;
		String typeEvent = null;

		while (true) {
			System.out.print("Choisissez un Type d'événement (numéro): ");
			if (scanner.hasNextInt()) {
				typeEventIndex = scanner.nextInt();
				scanner.nextLine();
				if (typeEventIndex >= 1 && typeEventIndex <= typeEvents.size()) {
					typeEvent = typeEvents.get(typeEventIndex - 1);
					break;
				} else {
					LOG.error("Numéro invalide. Veuillez entrer un nombre entre 1 et " + typeEvents.size() + ".");
				}
			} else {
				LOG.info("Entrée invalide. Veuillez entrer un nombre.");
				scanner.next();
			}
		}

		EventRoom eventroom = null;
		while (eventroom == null) {
			System.out.println("Liste des salles disponibles :");
			EventRoomController roomController = new EventRoomController();
			List<EventRoom> salles = roomController.getAllActiveRooms();

			for (EventRoom r : salles) {
				System.out.println(r.getId() + " - " + r.getName());
			}

			System.out.print("ID de la salle : ");
			int id = Integer.parseInt(scanner.nextLine());

			eventroom = roomController.findRoomById(id);
			if (eventroom == null) {
				LOG.info("Salle invalide !");
			}
		}
		
		List<String> format = Format.getAllFormat();
		System.out.println("Liste des Formats d'événement : ");
		for (int i = 0; i < format.size(); i++) {
			System.out.println((i + 1) + "- " + format.get(i));
		}
		int formatIndex = -1;
		while (true) {
			System.out.print("Choisissez un format d'événement (numéro): ");
			if (scanner.hasNextInt()) {
				formatIndex = scanner.nextInt();
				scanner.nextLine();
				if (formatIndex >= 1 && formatIndex <= format.size()) {
					break;
				} else {
					LOG.error("Numéro invalide. Veuillez entrer un nombre entre 1 et " + typeEvents.size() + ".");
				}
			} else {
				LOG.info("Entrée invalide. Veuillez entrer un nombre.");
				scanner.next();
			}
		}

		System.out.println("===== Choisir le Tuteur =====");
		List<Userdto> allUsers = UserController.getAllUsers();
		for (int i = 0; i < allUsers.size(); i++) {
			System.out.println((i + 1) + " - " + allUsers.get(i).getName() + " (" + allUsers.get(i).getEmail() + ")");
		}
		System.out.print("Numéro du tuteur : ");
		int tutorIndex = Integer.parseInt(scanner.nextLine());
		Userdto tutor = allUsers.get(tutorIndex - 1);

		System.out.println("===== Choisir le Modérateur =====");
		for (int i = 0; i < allUsers.size(); i++) {
			System.out.println((i + 1) + " - " + allUsers.get(i).getName() + " (" + allUsers.get(i).getEmail() + ")");
		}
		System.out.print("Numéro du modérateur : ");
		int moderatorIndex = Integer.parseInt(scanner.nextLine());
		Userdto moderator = allUsers.get(moderatorIndex - 1);

		List<Userdto> users = new ArrayList<>();
		boolean addMore = true;

		while (addMore) {
			System.out.println("Ajouter des participants internes (ex: 1,3,5) ou 0 pour arrêter : ");

			for (int i = 0; i < allUsers.size(); i++) {
				System.out
						.println((i + 1) + " - " + allUsers.get(i).getName() + " (" + allUsers.get(i).getEmail() + ")");
			}

			String input = scanner.nextLine().trim();

			if (input.equals("0")) {
				addMore = false;
			} else {

				String[] parts = input.split(",");
				for (String part : parts) {
					try {
						int choice = Integer.parseInt(part.trim());
						if (choice > 0 && choice <= allUsers.size()) {
							users.add(allUsers.get(choice - 1));
						} else {
							System.out.println("Numéro invalide : " + choice);
						}
					} catch (NumberFormatException e) {
						System.out.println(" Entrée invalide : " + part);
					}
				}
			}
		}

		List<String> externalParticipants = new ArrayList<>();
		boolean addExt = true;
		while (addExt) {
			System.out.print("Entrer email d’un participant externe (vide pour arrêter) : ");
			String email = scanner.nextLine().trim();
			if (email.isEmpty()) {
				addExt = false;
			} else {
				externalParticipants.add(email);
			}
		}

		Eventdto event = new Eventdto(title, dateDebut, dateFin, typeEvent, eventroom, users, moderator,tutor);

		boolean success = eventController.EventCreatController(event);

		if (success) {
			LOG.info("Événement créé avec succès !");
		} else {
			LOG.error("Erreur lors de la création de l'événement.");
		}
	}

	private boolean checkTitle(String title) {
		if (title == null || title.trim().isEmpty())
			return false;
		return title.matches("^[A-Za-zÀ-ÖØ-öø-ÿ ]+$");
	}

	public void updateEvent() {
		System.out.print("ID de l'événement à modifier : ");
		int id = Integer.parseInt(scanner.nextLine());

		System.out.print("Nouveau titre : ");
		String newtitle = scanner.nextLine();

		System.out.print("Nouvelle date début (yyyy-MM-dd HH:mm) : ");
		LocalDateTime newdateDebut = LocalDateTime.parse(scanner.nextLine(), formatter);

		System.out.print("Nouvelle date fin (yyyy-MM-dd HH:mm) : ");
		LocalDateTime newdateFin = LocalDateTime.parse(scanner.nextLine(), formatter);

		System.out.print("Nouveau type : ");
		String newtypeEvent = scanner.nextLine();

		System.out.print("Nouvel ID de salle : ");
		int roomId = Integer.parseInt(scanner.nextLine());

		System.out.print("Nouveau nom de salle : ");
		String roomName = scanner.nextLine();

		List<Userdto> participants = new ArrayList<>();
		boolean addMore = true;

		while (addMore) {
			System.out.print("Ajouter l'email d'un participant (ou vide pour arrêter) : ");
			String email = scanner.nextLine();

			if (email.isEmpty()) {
				addMore = false;
			} else {
				Userdto user = new Userdto();
				user.setEmail(email);
				participants.add(user);
			}
		}

		Eventdto eventDTO = new Eventdto(id, newtitle, newdateDebut, newdateFin, newtypeEvent, roomName, roomName, roomName, null);
		boolean success = eventController.updateEventController(eventDTO, newtitle, newdateDebut, newdateFin, null);

	}

	public void deleteEvent() {
		System.out.print("ID de l'événement à supprimer : ");
		int id = Integer.parseInt(scanner.nextLine());

		boolean success = eventController.deleteEvent(id);
		if (success) {
			LOG.info("Événement supprimé avec succès !");
		} else {
			LOG.error("Erreur lors de la suppression.");
		}
	}

	public void searchEventById() {
		System.out.print("Entrez l'ID de l'événement à rechercher : ");
		int eventId = Integer.parseInt(scanner.nextLine());

		EventController eventController = new EventController();
		Eventdto event = eventController.getEventByIdController(eventId);

		if (eventId <= 0) {
			System.out.println("L'ID doit être supérieur à 0");
			return;
		} else {
			if (event != null) {
				System.out.println(" Événement trouvé :");
				System.out.println("ID : " + event.getId());
				System.out.println("Titre : " + event.getTitle());
				System.out.println("Date début : " + event.getDateDebut().format(formatter));
				System.out.println("Date fin : " + event.getDateFin().format(formatter));
				System.out.println("Type : " + event.getTypeEvent());
				System.out.println("Salle : " + event.getEventRoom().getName());
			} else {
				System.out.println(" Aucun événement trouvé avec l'ID " + eventId);
			}
		}
	}

	public void listEvents() {
		System.out.println("=== Liste des événements ===");
		List<Eventdto> events = eventController.getAllEvents();
		if (events.isEmpty()) {
			LOG.error("Aucun événement trouvé.");
		} else {
			for (Eventdto e : events) {
				System.out.println(e);
			}
		}
	}
}
