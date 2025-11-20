package views.planningmanager;

import java.time.LocalDate;

import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import controller.EventRoomController;
import controller.PlanningController;
import controller.UserController;
import model.dto.EventRoomdto;
import model.dto.Planningdto;
import model.dto.Userdto;

public class PlanningManagerView {

	private static final Scanner scanner = new Scanner(System.in);
	private static final Logger LOG = LogManager.getLogger(PlanningManagerView.class);
	private static final PlanningController planningController = new PlanningController();
	private static final EventRoomController roomController = new EventRoomController();
	private static final DateTimeFormatter formatterDate = DateTimeFormatter.ofPattern("yyyy-MM-dd");

	public void creatPlanning() {

		System.out.println("========== CREATION DE PLANNING ==========");

		String motif;
		do {
			System.out.print("Entrez un motif pour votre planning : ");
			motif = scanner.nextLine().trim();
			if (!checkMotif(motif)) {
				System.out.println("Nom invalide ! (pas de chiffres ni de symboles)");
			}
		} while (!checkMotif(motif));

		LocalDate maintenant = LocalDate.now();
		LocalDate limite = maintenant.plusDays(1);
		LocalDate dateDebut = null;

		while (dateDebut == null) {
			System.out.print("Date de début (yyyy-MM-dd) : ");
			String inputDebut = scanner.nextLine();
			try {
				dateDebut = LocalDate.parse(inputDebut, formatterDate);
				if (!dateDebut.isAfter(limite)) {
					System.out.println("La date de début doit être strictement après demain !");
					dateDebut = null;
				}
			} catch (DateTimeParseException e) {
				System.out.println("Format invalide ! Exemple attendu : 2025-01-01");
			}
		}

		LocalDate dateFin = null;
		while (dateFin == null || !dateFin.isAfter(dateDebut)) {
			System.out.print("Date de fin (yyyy-MM-dd) : ");
			String inputFin = scanner.nextLine();
			try {
				dateFin = LocalDate.parse(inputFin, formatterDate);
				if (!dateFin.isAfter(dateDebut)) {
					System.out.println("La date de fin doit être strictement après la date de début !");
				}
			} catch (DateTimeParseException e) {
				System.out.println("Format invalide ! Exemple attendu : 2025-01-02");
			}
		}

		Userdto tutor = UserController.getCurrentUser();
		if (tutor == null) {
			System.out.println("Erreur : aucun utilisateur connecté !");
			return;
		}

		Planningdto planningdto = new Planningdto();
		planningdto.setMotif(motif);
		planningdto.setDateDebut(dateDebut);
		planningdto.setDateFin(dateFin);
		planningdto.setTutorPlanning(tutor);

		boolean success = planningController.createPlanning(planningdto);

		if (success) {
			System.out.println("Planning créé avec succès par " + tutor.getName() + " !");
		} else {
			System.out.println("Erreur lors de la création du planning.");
		}
	}

	private boolean checkMotif(String title) {
		if (title == null || title.trim().isEmpty())
			return false;
		return title.matches("^[A-Za-zÀ-ÖØ-öø-ÿ ]+$");
	}

	public void updatePlanning() {
		System.out.print("ID du Planning à modifier : ");
		int id = Integer.parseInt(scanner.nextLine());

		System.out.print("Nouveau motif : ");
		String newMotif = scanner.nextLine();

		System.out.print("Nouvelle date début (yyyy-MM-dd ) : ");
		LocalDate newDateDebut = LocalDate.parse(scanner.nextLine(), formatterDate);

		System.out.print("Nouvelle date fin (yyyy-MM-dd ) : ");
		LocalDate newDateFin = LocalDate.parse(scanner.nextLine(), formatterDate);

		Userdto currentUser = UserController.getCurrentUser();
		if (currentUser == null) {
			LOG.error("Aucun utilisateur connecté. Mise à jour impossible !");
			System.out.println("Erreur : aucun utilisateur connecté !");
			return;
		}

		Planningdto planningDTO = new Planningdto();
		planningDTO.setId(id);
		planningDTO.setMotif(newMotif);
		planningDTO.setDateDebut(newDateDebut);
		planningDTO.setDateFin(newDateFin);
		planningDTO.setTutorPlanning(currentUser);

		boolean success = planningController.updatePlanningController(planningDTO, id, newMotif, newDateFin, newDateFin,
				currentUser);
		if (success) {
			System.out.println("Planning mis à jour avec succès !");
		} else {
			System.out.println("Erreur lors de la mise à jour du planning.");
		}
	}

	public void deletePlanning() {
		System.out.print("ID du Planning à supprimer : ");
		int id = Integer.parseInt(scanner.nextLine());

		List<Planningdto> allPlannings = planningController.getAllPlannings();
		Planningdto planningToDelete = null;
		for (Planningdto p : allPlannings) {
			if (p.getId() == id) {
				planningToDelete = p;
				break;
			}
		}

		if (planningToDelete == null) {
			System.out.println("Aucun planning trouvé avec cet ID.");
			return;
		}

		List<Planningdto> eventsLinked = planningController.getEventsByPeriod(planningToDelete.getDateDebut(),
				planningToDelete.getDateFin());
		boolean hasLinkedEvents = eventsLinked.stream().anyMatch(e -> e.getId() == id);

		if (hasLinkedEvents) {
			System.out.println("Attention : ce planning contient des événements liés.");
			System.out.print("Voulez-vous supprimer tous les événements liés et le planning ? (oui/non) : ");
			String confirm = scanner.nextLine().trim().toLowerCase();
			if (!confirm.equals("oui")) {
				System.out.println("Suppression annulée !");
				return;
			}
			for (Planningdto e : eventsLinked) {
				planningController.planningDeleteController(e.getId());
			}
		}

		boolean success = planningController.planningDeleteController(id);
		if (success) {
			System.out.println("Planning supprimé avec succès !");
		} else {
			System.out.println("Erreur lors de la suppression du planning.");
		}
	}

	public void getEventByPeriod() {
		System.out.println("========== RECHERCHE D'ÉVÉNEMENTS PAR PÉRIODE ==========");

		try {

			System.out.print("Date de début (yyyy-MM-dd) : ");
			LocalDate dateDebut = LocalDate.parse(scanner.nextLine(), formatterDate);

			System.out.print("Date de fin (yyyy-MM-dd) : ");
			LocalDate dateFin = LocalDate.parse(scanner.nextLine(), formatterDate);

			List<Planningdto> events = planningController.getEventsByPeriod(dateDebut, dateFin);

			if (events == null || events.isEmpty()) {
				System.out.println("Aucun planning trouvé dans cette période.");
			} else {
				System.out.println("========== PLANNINGS TROUVÉS ==========");
				for (Planningdto e : events) {
					String tutorName = (e.getTutorPlanning() != null) ? e.getTutorPlanning().getName() : "Non assigné";
					String tutorFirstName = (e.getTutorPlanning() != null) ? e.getTutorPlanning().getFirstName() : "";
					System.out.println("ID = " + e.getId());
					System.out.println("Motif = " + e.getMotif());
					System.out.println("Début = " + e.getDateDebut());
					System.out.println("Fin = " + e.getDateFin());
					System.out.println("Tuteur = " + tutorName + " " + tutorFirstName);
					System.out.println("-----------------------------------");
				}
			}
		} catch (DateTimeParseException dtpe) {
			System.out.println("Format de date invalide ! Exemple attendu : 2025-01-01");
		} catch (Exception e) {
			LOG.error("Erreur lors de la récupération des plannings par période", e);
		}
	}

	public void creatRoom() {
		System.out.println("========== CRÉATION DE SALLE ==========");

		System.out.print("Nom de la salle : ");
		String name = scanner.nextLine();

		System.out.print("Capacité : ");
		int capacity = Integer.parseInt(scanner.nextLine());
		boolean active = true;

		EventRoomdto eventRoomdto = new EventRoomdto();
		eventRoomdto.setName(name);
		eventRoomdto.setCapacity(capacity);
		eventRoomdto.setActive(active);

		boolean success = roomController.createRoomController(eventRoomdto);

		if (success) {
			System.out.println("Salle créée avec succès !");
		} else {
			System.out.println("Erreur lors de la création de la salle.");
		}
	}

	public void updateRoom() {
		System.out.print("ID de la salle à modifier : ");
		int id = Integer.parseInt(scanner.nextLine());

		System.out.print("Nouveau nom : ");
		String name = scanner.nextLine();

		System.out.print("Nouvelle capacité : ");
		int capacity = Integer.parseInt(scanner.nextLine());

		System.out.print("Salle active ? (true/false) : ");
		boolean active = Boolean.parseBoolean(scanner.nextLine());

		EventRoomdto eventRoomdto = new EventRoomdto(id, name, capacity, active);

		boolean success = roomController.updateRoomController(eventRoomdto, name, capacity, active);

		if (success) {
			System.out.println("Salle mise à jour avec succès !");
		} else {
			System.out.println("Erreur lors de la mise à jour de la salle.");
		}
	}

	public void deleteRoom() {
		System.out.print("ID de la salle à supprimer : ");
		int id = Integer.parseInt(scanner.nextLine());

		boolean success = roomController.deleteRoomController(id);

		if (success) {
			System.out.println("Salle supprimée avec succès !");
		} else {
			System.out.println("Erreur lors de la suppression.");
		}
	}

	public void getRoomById() {
		System.out.print("ID de la salle à consulter : ");
		int id = Integer.parseInt(scanner.nextLine());

		var room = roomController.findRoomById(id);

		if (room != null) {
			System.out.println(" Salle trouvée : " + room.getName());
			System.out.println(" Capacité=" + room.getCapacity());
			System.out.println("Active=" + room.isActive());
			System.out.println("-------------------------------");
		} else {
			System.out.println("Aucune salle trouvée avec cet ID.");
		}
	}

	public void getAllRoom() {
		
		var rooms = roomController.getAllRoomsController();

		if (rooms.isEmpty()) {
			System.out.println("Aucune salle enregistrée.");
		} else {
			System.out.println("========== LISTE DES SALLES ==========");
			for (var room : rooms) {
				System.out.println(" ID=" + room.getId());
				System.out.println(" Nom=" + room.getName());
				System.out.println(" Capacité=" + room.getCapacity());
				System.out.println(" Active=" + room.isActive());
				System.out.println("----------------------------");
			}
		}
	}

}
