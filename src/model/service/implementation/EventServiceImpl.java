package model.service.implementation;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import model.database.DatabaseConnection;
import model.dto.Eventdto;
import model.entity.Event;
import model.service.sql.Query;
import util.constants.Constants;
import model.service.EventService;

public class EventServiceImpl implements EventService {
	
	private static final Logger LOG = LogManager.getLogger(EventServiceImpl.class);
	Connection connection = DatabaseConnection.getInstance();
    
    @Override
    public Eventdto registerEvent(Event event) {     
        String query = Query.CREATE_EVENT ;

        try (PreparedStatement ps = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {

        	ps.setString(1, event.getTitle());
            ps.setObject(2, event.getDateDebut()); 
            ps.setObject(3, event.getDateFin());
            ps.setString(4, event.getTypeEvent().name());
            ps.setInt(5, event.getEventRoom().getId());

            int result = ps.executeUpdate();
            if (result <= 0) {
            	   LOG.error(Constants.ERROR_DURING_EVENT_INSERTION);
                   return null;
               }

              
               try (ResultSet rs = ps.getGeneratedKeys()) {
                   if (rs.next()) {
                       event.setId(rs.getInt(1));
                   }
               }

             
               Eventdto eventDTO = new Eventdto();
               eventDTO.setId(event.getId());
               eventDTO.setTitle(event.getTitle());
               eventDTO.setDateDebut(event.getDateDebut());
               eventDTO.setDateFin(event.getDateFin());
               eventDTO.setTypeEvent(event.getTypeEvent().name());
               eventDTO.setEvenRoom(event.getEventRoom());

               LOG.info("Utilisateur créé avec succès : {}", event.getTitle());
               return eventDTO;

           } catch (SQLException e) {
               LOG.error(Constants.ERROR_CREATE_EVENT, e);
               return null;
           }
       }
}