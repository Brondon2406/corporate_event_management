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
import controller.PlanningController;
import controller.UserController;
import model.dto.EventRoomdto;
import model.dto.Eventdto;
import model.dto.Planningdto;
import model.dto.Userdto;
import model.entity.enumeration.Format;
import model.entity.enumeration.TypeEvent;

public class SessionAdminView {

	private static final Logger LOG = LogManager.getLogger(SessionAdminView.class);
	private static final Scanner scanner = new Scanner(System.in);
	private static final EventController eventController = new EventController();
	private static final UserController userController = new UserController();
	private static final PlanningController planningController = new PlanningController();
	private static final EventRoomController roomController = new EventRoomController();
	private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

	public void updateUser(Userdto userDTO) {

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

		List<EventRoomdto> salles = roomController.getAllActiveRooms();
		EventRoomdto eventRoom = null;
		while (eventRoom == null) {
			System.out.println("Liste des salles disponibles :");
			for (EventRoomdto r : salles) {
				System.out.println(r.getId() + " - " + r.getName());
			}

			System.out.print("ID de la salle : ");
			try {
				int id = Integer.parseInt(scanner.nextLine());
				eventRoom = roomController.findRoomById(id);
				if (eventRoom == null) {
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

		List<Userdto> allUsers = userController.getAllUsers();
		Userdto tutor = null;
		Userdto moderator = null;

		while (tutor == null) {
			System.out.println("===== Choisir le Tuteur =====");
			for (Userdto u : allUsers) {
				System.out
						.println(u.getId() + " - " + u.getFirstName() + " " + u.getName() + " (" + u.getEmail() + ")");
			}

			System.out.print("ID du tuteur : ");
			String input = scanner.nextLine().trim();

			try {
				int tutorId = Integer.parseInt(input);
				tutor = allUsers.stream().filter(u -> u.getId() == tutorId).findFirst().orElse(null);
				if (tutor == null) {
					System.out.println("ID invalide ! Veuillez entrer un ID existant.");
				}
			} catch (NumberFormatException e) {
				System.out.println("Entrée invalide ! Veuillez entrer un nombre.");
			}
		}

		while (moderator == null) {
			System.out.println("===== Choisir le Modérateur =====");
			for (Userdto u : allUsers) {
				System.out
						.println(u.getId() + " - " + u.getFirstName() + " " + u.getName() + " (" + u.getEmail() + ")");
			}

			System.out.print("ID du modérateur : ");
			String input = scanner.nextLine().trim();

			try {
				int modId = Integer.parseInt(input);
				Userdto chosen = allUsers.stream().filter(u -> u.getId() == modId).findFirst().orElse(null);

				if (chosen == null) {
					System.out.println("ID invalide ! Veuillez entrer un ID existant.");
				} else if (chosen.getId() == tutor.getId()) {
					System.out.println("Le modérateur ne peut pas être le même que le tuteur !");
				} else {
					moderator = chosen;
				}
			} catch (NumberFormatException e) {
				System.out.println("Entrée invalide ! Veuillez entrer un nombre.");
			}
		}

		List<Planningdto> plannings = planningController.getAllPlannings();
		Planningdto planningdto = null;
		while (planningdto == null) {
			System.out.println("Liste des Plannings :");
			for (Planningdto p : plannings) {
				System.out.println(p.getId() + " - " + p.getMotif());
			}
			System.out.print("ID du Planning : ");
			try {
				int id = Integer.parseInt(scanner.nextLine());
				planningdto = planningController.getEventByIdController(id);
				if (planningdto == null) {
					System.out.println("Planning introuvable ! Veuillez réessayer.");
				}
			} catch (NumberFormatException e) {
				System.out.println("Entrée invalide ! Veuillez entrer un nombre.");
			}
		}

		Eventdto eventdto = new Eventdto();
		eventdto.setTitle(title);
		eventdto.setDateDebut(dateDebut);
		eventdto.setDateFin(dateFin);
		eventdto.setTypeEvent(typeEvent);
		eventdto.setEventRoom(eventRoom);
		eventdto.setFormat(format);
		eventdto.setModerator(moderator.getFirstName() + " " + moderator.getName());
		eventdto.setTutor(tutor.getFirstName() + " " + tutor.getName());
		eventdto.setIdPlanning(planningdto);
		eventdto.setUsers(allUsers);
		eventdto.setStatus("PENDING");

		eventdto = eventController.createEvent(eventdto);

		if (eventdto == null) {
			System.out.println("Erreur lors de la création de l'événement.");
		} else {
			System.out.println("Événement créé avec succès !");
		}
	}

	private boolean checkTitle(String name) {
		if (name == null || name.trim().isEmpty())
			return false;
		return name.matches("^[A-Za-zÀ-ÖØ-öø-ÿ ]+$");

	}

	public void linkUsersToEvent() {
		System.out.println("========== AJOUT DE PARTICIPANTS À UN ÉVÉNEMENT ==========");

		List<Eventdto> allEvents = eventController.getAllEvents();
		if (allEvents.isEmpty()) {
			System.out.println(" Aucun événement disponible.");
			return;
		}

		System.out.println("===== Liste des événements disponibles =====");
		for (Eventdto e : allEvents) {
			System.out.println(e.getId() + " - " + e.getTitle() + " (Début: " + e.getDateDebut() + ", Fin: "
					+ e.getDateFin() + ", Status: " + e.getStatus() + ")");
		}

		System.out.print("Entrez l'ID de l'événement auquel ajouter des participants : ");
		int eventId;
		try {
			eventId = Integer.parseInt(scanner.nextLine());
		} catch (NumberFormatException e) {
			System.out.println(" ID invalide.");
			return;
		}

		Eventdto selectedEvent = allEvents.stream().filter(ev -> ev.getId() == eventId).findFirst().orElse(null);

		if (selectedEvent == null) {
			System.out.println(" Événement introuvable !");
			return;
		}

		List<Userdto> allUsers = userController.getAllUsers();
		List<Integer> internalUsersIds = new ArrayList<>();

		System.out.println("===== Liste des utilisateurs disponibles =====");
		for (Userdto u : allUsers) {
			System.out.println(u.getId() + " - " + u.getFirstName() + " " + u.getName());
		}

		System.out.println("Entrez les ID des participants séparés par des virgules (ex: 1,3,5) : ");
		String input = scanner.nextLine();
		String[] ids = input.split(",");
		for (String idStr : ids) {
			try {
				int id = Integer.parseInt(idStr.trim());
				internalUsersIds.add(id);
			} catch (NumberFormatException e) {
				System.out.println("ID invalide ignoré : " + idStr);
			}
		}

		List<String> externalUsersEmails = new ArrayList<>();
		System.out.print("Entrez les emails externes séparés par virgules : ");
		String inputEmails = scanner.nextLine();
		if (!inputEmails.trim().isEmpty()) {
			for (String email : inputEmails.split(",")) {
				externalUsersEmails.add(email.trim());
			}
		}

		boolean success = eventController.linkUsersToEventController(eventId, internalUsersIds, externalUsersEmails);

		if (success) {
			System.out.println(" Participants ajoutés avec succès à l'événement !");
		} else {
			System.out.println("Erreur lors de l'ajout des participants.");
		}
	}

	public void updateUsersToEvent() {
		System.out.println("========== GESTION DES PARTICIPANTS ==========");

		List<Eventdto> allEvents = eventController.getAllEvents();
		if (allEvents.isEmpty()) {
			System.out.println(" Aucun événement disponible.");
			return;
		}

		System.out.println("===== Liste des événements disponibles =====");
		for (Eventdto e : allEvents) {
			System.out.println(e.getId() + " - " + e.getTitle() + " (Début: " + e.getDateDebut() + ", Fin: "
					+ e.getDateFin() + ", Status: " + e.getStatus() + ")");
		}

		System.out.print("Entrez l'ID de l'événement : ");
		int eventId;
		try {
			eventId = Integer.parseInt(scanner.nextLine());
		} catch (NumberFormatException e) {
			System.out.println(" ID invalide.");
			return;
		}
		
		Eventdto selectedEvent = allEvents.stream().filter(ev -> ev.getId() == eventId).findFirst().orElse(null);

		if (selectedEvent == null) {
			System.out.println(" Événement introuvable !");
			return;
		}

		List<Userdto> allUsers = userController.getAllUsers();
		List<Integer> internalAdd = eventController.getInternalUsersForEvent(eventId);

		System.out.println("===== Liste des utilisateurs internes =====");
		for (Userdto u : allUsers) {
			System.out.println(u.getId() + " - " + u.getFirstName() + " " + u.getName());
		}

		System.out.print("IDs des participants internes à ajouter (séparés par , ou laisser vide) : ");
		String[] addInternalInput = scanner.nextLine().split(",");
		for (String s : addInternalInput) {
			if (!s.isBlank()) {
				try {
					internalAdd.add(Integer.parseInt(s.trim()));
				} catch (NumberFormatException ignored) {
				}
			}
		}

		List<String> externalAdd = eventController.getExternalUsersForEvent(eventId);
		System.out.print("Emails des participants externes à ajouter (séparés par , ou laisser vide) : ");
		String[] addExternalInput = scanner.nextLine().split(",");
		for (String s : addExternalInput)
			if (!s.isBlank())
				externalAdd.add(s.trim());

		List<Integer> internalRemove = eventController.getInternalUsersForEvent(eventId);
		List<String> externalRemove = eventController.getExternalUsersForEvent(eventId);

		System.out.println("===== Participants internes actuels =====");
		for (Integer id : internalRemove)
			System.out.println("ID : " + id);

		System.out.print("IDs des participants internes à retirer (séparés par , ou laisser vide) : ");
		String[] removeInternalInput = scanner.nextLine().split(",");
		List<Integer> internalRemoveSelected = new ArrayList<>();
		for (String s : removeInternalInput) {
			if (!s.isBlank()) {
				try {
					int id = Integer.parseInt(s.trim());
					if (internalRemove.contains(id))
						internalRemoveSelected.add(id);
				} catch (NumberFormatException ignored) {
				}
			}
		}

		System.out.println("===== Participants externes actuels =====");
		for (String email : externalRemove)
			System.out.println(email);

		System.out.print("Emails des participants externes à retirer (séparés par , ou laisser vide) : ");
		String[] removeExternalInput = scanner.nextLine().split(",");
		List<String> externalRemoveSelected = new ArrayList<>();
		for (String s : removeExternalInput)
			if (!s.isBlank() && externalRemove.contains(s.trim()))
				externalRemoveSelected.add(s.trim());

		if (internalAdd.isEmpty() && internalRemoveSelected.isEmpty() && externalAdd.isEmpty()
				&& externalRemoveSelected.isEmpty()) {
			System.out.println(" Aucune modification à effectuer.");
			return;
		}

		boolean success = eventController.updateParticipantsForEventController(eventId, internalAdd,
				internalRemoveSelected, externalAdd, externalRemoveSelected);
		if (success) {
			System.out.println("Mise à jour des participants réussie !");
		} else {
			System.out.println(" Échec de la mise à jour.");
		}
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

		List<Userdto> allUsers = userController.getAllUsers();
		for (int i = 0; i < allUsers.size(); i++) {
			System.out.println((i + 1) + " - " + allUsers.get(i).getName());
		}
		System.out.print("Numéro du modérateur : ");
		int modIndex = Integer.parseInt(scanner.nextLine());
		Userdto newModerator = allUsers.get(modIndex - 1);

		/*
		 * List<Userdto> newUsers = new ArrayList<>(); while (true) { System.out.
		 * print("Ajouter l'email d'un participant interne (ou vide pour arrêter) : ");
		 * String email = scanner.nextLine(); if (email.isEmpty()) break; Userdto user =
		 * new Userdto(); user.setEmail(email); newUsers.add(user); }
		 * 
		 * List<String> newExternal = new ArrayList<>(); while (true) { System.out.
		 * print("Ajouter email d'un participant externe (ou vide pour arrêter) : ");
		 * String email = scanner.nextLine(); if (email.isEmpty()) break;
		 * newExternal.add(email); }
		 */

		Eventdto eventDTO = new Eventdto();
		eventDTO.setId(id);
		eventDTO.setTitle(newTitle);
		eventDTO.setDateDebut(newDateDebut);
		eventDTO.setDateFin(newDateFin);
		eventDTO.setTypeEvent(newTypeEvent);
		eventDTO.setFormat(newFormat);
		eventDTO.setModerator(newModerator);
		eventDTO.setEventRoom(newRoom);

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
			System.out.println(" Aucun événement trouvé.");
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
