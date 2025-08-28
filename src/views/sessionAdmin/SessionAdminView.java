package views.sessionAdmin;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
import model.entity.EventRoom;
import model.entity.enumeration.TypeEvent;

public class SessionAdminView {
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
		UserController.updateUser(userDTO, newName, newEmail, newPassword);
	}

	public void registEvent() {
		System.out.println("========== CREATION D'EVENEMENT ==========");
		System.out.println("=== Création d'un nouvel événement ===");

		String title;
		do {
			System.out.print("Entrez votre nom : ");
			title = scanner.nextLine().trim();
			if (!checkTitle(title)) {
				System.out.println("Title invalide ! (pas de chiffres ni de symboles)");
			}
		} while (!checkTitle(title));

		System.out.print("Date de début (yyyy-MM-dd HH:mm) : ");
		LocalDateTime dateDebut = LocalDateTime.parse(scanner.nextLine(), formatter);

		System.out.print("Date de fin (yyyy-MM-dd HH:mm) : ");
		LocalDateTime dateFin = LocalDateTime.parse(scanner.nextLine(), formatter);

		LocalDateTime now = LocalDateTime.now();
		LocalDateTime limit = now.plusWeeks(1);

		if (dateDebut.isBefore(limit)) {
			LOG.error(" La date de début doit être au moins une semaine après aujourd'hui !");
		} else if (dateFin.isBefore(dateDebut)) {
			LOG.error(" La date de fin doit être après la date de début !");
		} else {
			LOG.info(" Les dates sont valides !");
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

				if (typeEventIndex >= 1 && typeEventIndex <= typeEvents.size()) {
					typeEvent = typeEvents.get(typeEventIndex - 1);
					break;
				} else {
					LOG.error(" Numéro invalide. Veuillez entrer un nombre entre 1 et " + typeEvents.size() + ".");
				}
			} else {
				LOG.info("Entrée invalide. Veuillez entrer un nombre.");
				scanner.next();
			}
		}
		EventRoom room = null;
		boolean salleValide = false;

		while (!salleValide) {
		    System.out.print("Salle de l'événement (id) : ");
		    int id = Integer.parseInt(scanner.nextLine());

		    System.out.print("Nom de la salle : ");
		    String name = scanner.nextLine();
  
		    EventRoomController roomController = new EventRoomController(); 
		    room = roomController.findRoomByIdAndName(id, name); 

		    if (room != null) {
		        salleValide = true; 
		    } else {
		        System.out.println("⚠️ Salle invalide ! Veuillez entrer un ID et un nom de salle existants.");
		    }
		}

		System.out.println("✅ Salle sélectionnée : " + room.getName());


		List<Userdto> users = new ArrayList<>();
		Userdto currentUser = new Userdto();
		users.add(currentUser);

		Eventdto event = new Eventdto(title, dateDebut, dateFin, typeEvent, new EventRoom(name, id), users);
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
		String title = scanner.nextLine();

		System.out.print("Nouvelle date début (yyyy-MM-dd HH:mm) : ");
		LocalDateTime dateDebut = LocalDateTime.parse(scanner.nextLine(), formatter);

		System.out.print("Nouvelle date fin (yyyy-MM-dd HH:mm) : ");
		LocalDateTime dateFin = LocalDateTime.parse(scanner.nextLine(), formatter);

		System.out.print("Nouveau type : ");
		String typeEvent = scanner.nextLine();

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

		EventRoomdto eventRoom = new EventRoomdto(roomId, roomName);
		Eventdto event = new Eventdto(id, title, dateDebut, dateFin, typeEvent, new EventRoom(roomName, id));
		boolean success = eventController.updateEventController(event);

		if (success) {
			LOG.info("Événement mis à jour avec succès !");
		} else {
			LOG.error("Erreur lors de la mise à jour.");
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
