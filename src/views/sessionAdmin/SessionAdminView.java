package views.sessionAdmin;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import controller.EventController;
import controller.UserController;
import model.dto.Eventdto;
import model.dto.Userdto;
import model.entity.EventRoom;
public class SessionAdminView {
	private static final Scanner scanner = new Scanner(System.in);
	private static final EventController eventController = new EventController();
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
	 
	public void updateUser (Userdto userDTO) {

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
	
	public void registEvent(Userdto currentUser, EventRoom room) {
	    System.out.println("========== CREATION D'EVENEMENT ==========");
	    System.out.println("=== Création d'un nouvel événement ===");
	    
	    System.out.print("Titre de l'événement : ");
	    String title = scanner.nextLine();

	    System.out.print("Date de début (yyyy-MM-dd HH:mm) : ");    
	    LocalDateTime dateDebut = LocalDateTime.parse(scanner.nextLine(), formatter);

	    System.out.print("Date de fin (yyyy-MM-dd HH:mm) : ");
	    LocalDateTime dateFin = LocalDateTime.parse(scanner.nextLine(), formatter);

	    System.out.print("Type d'événement : ");
	    String typeEvent = scanner.nextLine();

	    System.out.print("Salle de l'événement (id) : ");
	    int id = Integer.parseInt(scanner.nextLine());

	    System.out.print("Nom de la salle : ");
	    String name = scanner.nextLine();


	    List<Userdto> users = new ArrayList<>();
	    users.add(currentUser);
    
	    Eventdto event = new Eventdto(title, dateDebut, dateFin, typeEvent, new EventRoom(name, id), users);
	    boolean success = eventController.EventCreatController(event);

	    if (success) {
	        System.out.println("Événement créé avec succès !");
	    } else {
	        System.out.println("Erreur lors de la création de l'événement.");
	    }
	}}
