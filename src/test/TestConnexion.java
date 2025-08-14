package test;

import java.sql.Connection;
import model.database.DatabaseConnection;

public class TestConnexion {

	public static void main(String[] args) {
		        DatabaseConnection db = new DatabaseConnection();
		        Connection con = db.getInstance();

		        if (con != null) {
		           System.out.println(" Connexion réussie à la base de données !");
		        } else {
		        	System.err.println(" Échec de la connexion à la base de données !");
		        }
	}

}
