package model.service.scheduler;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDateTime;

import model.database.DatabaseConnection;

public class ExpireEventsTask implements Runnable {

    private static final String UPDATE_EXPIRED_EVENTS = 
        "UPDATE events SET status = 'EXPIRED' " +
        "WHERE date_fin < ? AND status <> 'EXPIRED'";

    @Override
    public void run() {
        System.out.println("ExpireEventsTask: vérification des événements expirés à " + LocalDateTime.now());
        Connection connection = null; 
        PreparedStatement stmt = null;

        try {
            connection = DatabaseConnection.getConnection();

            stmt = connection.prepareStatement(UPDATE_EXPIRED_EVENTS);
            stmt.setObject(1, LocalDateTime.now());

            int updatedRows = stmt.executeUpdate();
            System.out.println("ExpireEventsTask: " + updatedRows + " événement(s) marqué(s) comme expiré(s).");

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (stmt != null) stmt.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}

