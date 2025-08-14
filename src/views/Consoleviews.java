package views;

import java.util.Scanner;

import controller.dto.Userdto;
import model.entity.Users;

public class Consoleviews {
	
	  

	    	    public static void main(String[] args) {
	    	        Scanner scanner = new Scanner(System.in);
	    	        boolean continuer = true;

	    	        System.out.println("=== Application Gestion Utilisateurs ===");

	    	        while (continuer) {
	    	            System.out.println("\nMenu principal :");
	    	            System.out.println("1 - Inscription");
	    	            System.out.println("2 - Connexion");
	    	            System.out.println("3 - Quitter");
	    	            System.out.print("Choisissez une option : ");

	    	            int choix = scanner.nextInt();
	    	            scanner.nextInt();
	    	            
	    	            switch (choix) {
	    	                case 1:
	    	                	Userdto.registerUser();
	    	                    break;
	    	                case 2:
	    	                	Userdto.loginUser();
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
