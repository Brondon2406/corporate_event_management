package test;

import java.sql.Connection;
import model.database.DatabaseConnection;

public class TestConnexion {

	public static void main(String[] args) {
		        DatabaseConnection db = new DatabaseConnection();
		        Connection con = db.getInstance();

		        if (con != null) {
		           System.out.println(" Connexion r�ussie � la base de donn�es !");
		        } else {
		        	System.err.println(" Echec de la connexion à la base de donn�es !");
		        }
	}

}
