package model.service.implementation;

import java.io.PrintStream;
import java.util.Scanner;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import controller.dto.Userdto;
import controller.utilities.MappingService.MappingUser;
import model.database.DatabaseConnection;
import model.service.AuthenticationService;



public class AuthenticationServiceImpl implements AuthenticationService {
	private static final Logger LOG = LogManager.getLogger(AuthenticationServiceImpl.class);
    private final DatabaseConnection db ;
    private final MappingUser mapper ;
	
	public AuthenticationServiceImpl(DatabaseConnection db, MappingUser mapper) {
		super();
		this.db = db;
		this.mapper = mapper;
	}

	@Override
	public Userdto registerUser(Userdto UserDTO) {	
	Scanner scanner = new Scanner(System.in);
		
		 System.out.println("\n=== Inscription ===");

	        System.out.print("Entrez votre nom : ");
	        String nom = scanner.nextLine().trim();
	   
	        if (!nomValide(nom)) {
	        	LOG.error(" Nom invalide. Utilisez uniquement des lettres, espaces, apostrophes ou tirets.");
	          return null;
	          }
	        
	        System.out.print("Entrez votre email : ");
	        String email = scanner.nextLine().trim();
	        if (!emailValide(email)) {
	        	LOG.error(" Email invalide. Veuillez entrer un email correct.");
	         return null ; 
	        
	        }
	        System.out.print("Entrez votre mot de passe:");
	        String password = scanner.nextLine();
	        
	        System.out.print("Entrez votre fonction: ");
	        String fonction = scanner.nextLine();

	        boolean inscrit = Userdto.inscrireUser(nom, email, password);
	        if (inscrit) {
	        	LOG.info("Inscription réussie ! Vous pouvez maintenant vous connecter.");
	        } else {
	        	LOG.error("Échec de l'inscription. Veuillez réessayer.");
	        }
			return UserDTO;
	    }

	   

		

		public static boolean emailValide(String email) {
		    return email != null && email.contains("@") && email.contains(".");
		}

	    private static boolean nomValide(String nom) {
	        String regex = "^[a-zA-ZÀ-ÿ '-]+$"; 
	        return nom != null && nom.matches(regex);
	    
	}

	@Override
	public Userdto loginUser(Userdto UserDTO) {
		Scanner Scanner = new Scanner(System.in);
		
		 System.out.println("\n=== Connexion ===");

	        System.out.print("Entrez votre email : ");
	        String email = Scanner.nextLine().trim();

	        System.out.print("Entrez votre mot de passe : ");
	    
	        String password = Scanner.nextLine().trim();
	        boolean connecte = Userdto.connecterUser(email, password);
	        if (!connecte) {
	        	LOG.error("Connexion échouée. Vérifiez vos identifiants.");
	        }
			return UserDTO;
	    

}}
