package views;

import java.util.Scanner;


public class MainView {
		 	    	
		public static void main(String[] args) {
			AuthenticationView view = new AuthenticationView();
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
