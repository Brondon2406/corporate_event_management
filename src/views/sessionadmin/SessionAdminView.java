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
import model.dto.EventRoomdto;
import model.dto.Eventdto;
import model.dto.Userdto;
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
				System.out.println("Titre invalide ! (pas de chiffres ni de symboles)");
			}
		} while (!checkTitle(title));

		LocalDateTime maintenant = LocalDateTime.now();
		LocalDateTime limite = maintenant.plusWeeks(2);

		LocalDateTime dateDebut = null;
		while (dateDebut == null) {
			System.out.print("Date de début (yyyy-MM-dd HH:mm) : ");
			String inputDebut = scanner.nextLine();
			try {
				dateDebut = LocalDateTime.parse(inputDebut, formatter);
				if (dateDebut.isBefore(limite)) {
					System.out.println("La date de début doit être au moins deux semaines après aujourd'hui !");
					dateDebut = null;
				}
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
					System.out.println("La date de fin doit être après la date de début !");
				}
			} catch (DateTimeParseException e) {
				System.out.println("Format invalide ! Exemple attendu : 2025-01-01 07:30");
			}
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
					System.out.println(
							"Numéro invalide. Veuillez entrer un nombre entre 1 et " + typeEvents.size() + ".");
				}
			} else {
				System.out.println("Entrée invalide. Veuillez entrer un nombre.");
				scanner.next();
			}
		}

		EventRoomController roomController = new EventRoomController();
		List<EventRoomdto> salles = roomController.getAllActiveRooms();
		EventRoomdto eventroom = null;
		while (eventroom == null) {
			System.out.println("Liste des salles disponibles :");
			for (EventRoomdto r : salles) {
				System.out.println(r.getId() + " - " + r.getName());
			}

			System.out.print("ID de la salle : ");
			try {
				int id = Integer.parseInt(scanner.nextLine());
				eventroom = roomController.findRoomById(id);
				if (eventroom == null) {
					System.out.println("Salle invalide ! Veuillez réessayer.");
				}
			} catch (NumberFormatException e) {
				System.out.println("Entrée invalide ! Veuillez entrer un nombre.");
			}
		}

		List<String> formatList = Format.getAllFormat();
		System.out.println("Liste des Formats d'événement : ");
		for (int i = 0; i < formatList.size(); i++) {
			System.out.println((i + 1) + "- " + formatList.get(i));
		}
		int formatIndex = -1;
		String format = null;
		while (true) {
			System.out.print("Choisissez un format d'événement (numéro): ");
			if (scanner.hasNextInt()) {
				formatIndex = scanner.nextInt();
				scanner.nextLine();
				if (formatIndex >= 1 && formatIndex <= formatList.size()) {
					format = formatList.get(formatIndex - 1);
					break;
				} else {
					System.out.println(
							"Numéro invalide. Veuillez entrer un nombre entre 1 et " + formatList.size() + ".");
				}
			} else {
				System.out.println("Entrée invalide. Veuillez entrer un nombre.");
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
		String tutors = tutor.getName();

		System.out.println("===== Choisir le Modérateur =====");
		for (int i = 0; i < allUsers.size(); i++) {
			System.out.println((i + 1) + " - " + allUsers.get(i).getName() + " (" + allUsers.get(i).getEmail() + ")");
		}
		System.out.print("Numéro du modérateur : ");
		int moderatorIndex = Integer.parseInt(scanner.nextLine());
		Userdto moderator = allUsers.get(moderatorIndex - 1);
		String moderators = tutor.getName();

		System.out.print("ID du planning (ou 0 si aucun) : ");
		int idPlanning = 0;
		try {
			idPlanning = Integer.parseInt(scanner.nextLine().trim());
		} catch (NumberFormatException e) {
			System.out.println("ID invalide. La valeur 0 sera utilisée.");
		}

		System.out.println("=== Liste des participants disponibles ===");
		for (Userdto user : allUsers) {
			System.out.println(user.getId() + " - " + user.getEmail());
		}

		System.out.print("Entrez les ID des participants séparés par des virgules (ex: 1,3,5) : ");
		String input = scanner.nextLine().trim();
		List<Integer> participantIds = new ArrayList<>();

		if (!input.isEmpty()) {
			String[] parts = input.split(",");
			for (String part : parts) {
				try {
					int id = Integer.parseInt(part.trim());
					participantIds.add(id);
				} catch (NumberFormatException e) {
					System.out.println("ID invalide ignoré : " + part);
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

		Eventdto event = new Eventdto(0, title, dateDebut, dateFin, typeEvent, eventroom, format, moderator.getEmail(),
				tutor.getEmail(), idPlanning, externalParticipants, allUsers);
		event.setTitle(title);
		event.setDateDebut(dateDebut);
		event.setDateFin(dateFin);
		event.setTypeEvent(typeEvent);
		event.setEventRoom(eventroom);
		event.setFormat(format);
		event.setModerator(moderators);
		event.setTutor(tutors);
		event.setIdPlanning(idPlanning);
		event.setUsers(allUsers);
		event.setExternalParticipantsEmails(externalParticipants);

		boolean success = eventController.createEvent(event);

		if (success) {
			System.out.println("Événement créé avec succès !");
		} else {
			System.out.println("Erreur lors de la création de l'événement.");
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
		String newTitle = scanner.nextLine();

		
		System.out.print("Nouvelle date début (yyyy-MM-dd HH:mm) : ");
		LocalDateTime newDateDebut = LocalDateTime.parse(scanner.nextLine(), formatter);

		System.out.print("Nouvelle date fin (yyyy-MM-dd HH:mm) : ");
		LocalDateTime newDateFin = LocalDateTime.parse(scanner.nextLine(), formatter);

		
		List<String> typeEvents = TypeEvent.getAllTypeEvent();
		for (int i = 0; i < typeEvents.size(); i++) {
			System.out.println((i + 1) + "- " + typeEvents.get(i));
		}
		System.out.print("Choisissez un type d'événement : ");
		int typeIndex = Integer.parseInt(scanner.nextLine());
		String newTypeEvent = typeEvents.get(typeIndex - 1);

		
		List<String> formatList = Format.getAllFormat();
		for (int i = 0; i < formatList.size(); i++) {
			System.out.println((i + 1) + "- " + formatList.get(i));
		}
		System.out.print("Choisissez un format : ");
		int formatIndex = Integer.parseInt(scanner.nextLine());
		String newFormat = formatList.get(formatIndex - 1);

		
		EventRoomController roomController = new EventRoomController();
		List<EventRoomdto> salles = roomController.getAllActiveRooms();
		for (EventRoomdto r : salles) {
			System.out.println(r.getId() + " - " + r.getName());
		}
		System.out.print("Nouvel ID de salle : ");
		int roomId = Integer.parseInt(scanner.nextLine());
		EventRoomdto newRoom = roomController.findRoomById(roomId);

		
		List<Userdto> allUsers = UserController.getAllUsers();
		for (int i = 0; i < allUsers.size(); i++) {
			System.out.println((i + 1) + " - " + allUsers.get(i).getName());
		}
		System.out.print("Numéro du modérateur : ");
		int modIndex = Integer.parseInt(scanner.nextLine());
		Userdto newModerator = allUsers.get(modIndex - 1);

		System.out.print("Numéro du tuteur : ");
		int tutorIndex = Integer.parseInt(scanner.nextLine());
		Userdto newTutor = allUsers.get(tutorIndex - 1);

		
		List<Userdto> newUsers = new ArrayList<>();
		while (true) {
			System.out.print("Ajouter l'email d'un participant interne (ou vide pour arrêter) : ");
			String email = scanner.nextLine();
			if (email.isEmpty())
				break;
			Userdto user = new Userdto();
			user.setEmail(email);
			newUsers.add(user);
		}

		List<String> newExternal = new ArrayList<>();
		while (true) {
			System.out.print("Ajouter email d'un participant externe (ou vide pour arrêter) : ");
			String email = scanner.nextLine();
			if (email.isEmpty())
				break;
			newExternal.add(email);
		}

		Eventdto eventDTO = new Eventdto(id, newTitle, newDateDebut, newDateFin, newTypeEvent, newRoom, newFormat,
				newModerator.getEmail(), newTutor.getEmail(), 0, newExternal, newUsers);

		boolean success = eventController.updateEventController(eventDTO, newTitle, newDateDebut, newDateFin,
				newTypeEvent);

		if (success) {
			System.out.println(" Événement mis à jour avec succès !");
		} else {
			System.out.println(" Échec de la mise à jour.");
		}
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

		Eventdto event = eventController.getEventByIdController(eventId);

		if (event == null) {
			System.out.println(" Aucun événement trouvé.");
			return;
		}

		System.out.println("\n===== Détails de l'événement =====");
		System.out.println("ID : " + event.getId());
		System.out.println("Titre : " + event.getTitle());
		System.out.println("Date début : " + event.getDateDebut().format(formatter));
		System.out.println("Date fin : " + event.getDateFin().format(formatter));
		System.out.println("Type : " + event.getTypeEvent());
		System.out.println("Format : " + event.getFormat());
		System.out.println("Salle : " + (event.getEventRoom() != null ? event.getEventRoom().getName() : "N/A"));
		System.out.println("Modérateur : " + event.getModerator());
		System.out.println("Tuteur : " + event.getTutor());
		System.out.println("Planning ID : " + event.getIdPlanning());

		System.out.println("\n--- Participants internes ---");
		if (event.getUsers() != null) {
			for (Userdto u : event.getUsers()) {
				System.out.println("- " + u.getEmail());
			}
		}

		System.out.println("\n--- Participants externes ---");
		if (event.getExternalParticipantsEmails() != null) {
			for (String email : event.getExternalParticipantsEmails()) {
				System.out.println("- " + email);
			}
		}
	}

	public void listEvents() {
		System.out.println("=== Liste des événements ===");
		List<Eventdto> events = eventController.getAllEvents();
		if (events.isEmpty()) {
			System.out.println("❌ Aucun événement trouvé.");
		} else {
			for (Eventdto e : events) {
				System.out.println(e.getId() + " | " + e.getTitle() + " | " + e.getDateDebut().format(formatter) + " → "
						+ e.getDateFin().format(formatter) + " | Salle: "
						+ (e.getEventRoom() != null ? e.getEventRoom().getName() : "N/A") + " | Modérateur: "
						+ e.getModerator() + " | Tuteur: " + e.getTutor());
			}
		}
	}

}
