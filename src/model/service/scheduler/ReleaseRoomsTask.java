package model.service.scheduler;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import model.database.DatabaseConnection;

public class ReleaseRoomsTask implements Runnable {

    @Override
    public void run() {
        System.out.println("ReleaseRoomsTask: libération des salles à " + java.time.LocalDateTime.now());

        String sql = "UPDATE event_rooms SET active = TRUE WHERE active = FALSE";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            int rowsUpdated = stmt.executeUpdate();
            System.out.println("ReleaseRoomsTask: " + rowsUpdated + " salle(s) libérée(s).");

        } catch (SQLException e) {
            System.err.println("Erreur lors de la libération des salles : " + e.getMessage());
        }
    }
}
