package test;

import java.sql.Connection;
import model.database.DatabaseConnection;

public class TestConnexion {

	public static void Test() {
		Connection connection = DatabaseConnection.getInstance();

		if (connection != null) {
			System.out.println(" Connexion r�ussie à la base de données !");
		} else {
			System.err.println(" Echec de la connexion à la base de donn�es !");
		}
	}

}
