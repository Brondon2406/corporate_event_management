package controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import model.dto.Eventdto;
import model.entity.Event;
import model.mapping.MappingService.MappingUser;
import model.mapping.MappingServiceImpl.MappingUserImpl;
import model.service.EventService;
import model.service.implementation.EventServiceImpl;
import util.constants.Constants;

public class EventController {
	private static final Logger LOG = LogManager.getLogger(UserController.class);
	private MappingUser mapper =  new MappingUserImpl();
	private EventService EventService = new EventServiceImpl();
	
	public boolean EventCreatController(Eventdto eventdto) {
	    if (eventdto == null) {
	        LOG.error(Constants.EMPTY_EVENT_DTO);
	        return false;
	        }
	  
	    Event event = mapper.convertEventdtoToEvent(eventdto);

	    Eventdto dto = EventService.registerEvent(event);

	    if (dto != null) {
	        LOG.info("Événement créé avec succès !");
	        return true;
	    } else {
	        LOG.error("Erreur lors de la création de l'événement.");
	        return false;
	    }
	}

}
