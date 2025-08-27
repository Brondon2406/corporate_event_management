package model.mapping.MappingServiceImpl;

import java.time.LocalDateTime;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import model.dto.Eventdto;
import model.entity.Event;
import model.entity.LogEvent;
import model.entity.enumeration.TypeEvent;
import model.mapping.MappingService.MappingEvent;
import util.constants.Constants;

public class MappingEventImpl implements MappingEvent {
	private static final Logger LOG = LogManager.getLogger(MappingEventImpl.class);
	private final LogEvent logEvent = new LogEvent();
	
	@Override
	public Eventdto convertEventToEventdto(Event event) {
	    if (event == null) {
	        logEvent.setAction("convert Event to Eventdto");
	        logEvent.setDate(LocalDateTime.now());
	        logEvent.setDescription(Constants.EVENT_IS_EMPTY);
	        LOG.info(logEvent.toString());
	        return null;
	    }

	    try {
	        Eventdto eventdto = new Eventdto();

	        eventdto.setId(event.getId() <= 0 ? 0 : event.getId());
	        eventdto.setTitle(event.getTitle() .isEmpty() ? null : event.getTitle());
	        eventdto.setDateDebut(event.getDateDebut() == null ? null : event.getDateDebut());
	        eventdto.setDateFin(event.getDateFin() == null ? null : event.getDateFin());
	        eventdto.setTypeEvent(event.getTypeEvent()== null ? null : event.getTypeEvent().name().toUpperCase());
	        eventdto.setEventRoom(event.getEventRoom() == null ? null : event.getEventRoom());
	    } catch (Exception e) {
	        logEvent.setAction("convert Event to Eventdto");
	        logEvent.setDate(LocalDateTime.now());
	        logEvent.setDescription(String.format(Constants.MAPPING_EVENT_DTO_ERROR, event.toString(), e.getMessage()));
	        LOG.info(logEvent.toString());
	        return null;
	    }
		return null;
	}
	
	@Override
	public Event convertEventdtoToEvent(Eventdto eventdto) {
	    if (eventdto == null) {
	        logEvent.setAction("convert Eventdto to Event");
	        logEvent.setDate(LocalDateTime.now());
	        logEvent.setDescription(Constants.EVENT_DTO_IS_EMPTY);
	        LOG.info(logEvent.toString());
	        return null;
	    }

	    try {
	        Event event = new Event();
	        event.setEventRoom(event.getEventRoom() == null ? null : eventdto.getEventRoom());
	        event.setTitle(eventdto.getTitle() == null || eventdto.getTitle().isEmpty() ? null : eventdto.getTitle());
	        event.setDateDebut(eventdto.getDateDebut() == null ? null : eventdto.getDateDebut());
	        event.setDateFin(eventdto.getDateFin() == null ? null : eventdto.getDateFin());
	        event.setTypeEvent(eventdto.getTypeEvent().isEmpty() ? null : TypeEvent.fromString(eventdto.getTypeEvent()));
	        //user.setRole(userdto.getRole().isEmpty() ? null : Role.fromString(userdto.getRole()));
	    } catch (Exception e) {
	        logEvent.setAction("convert Eventdto to Event");
	        logEvent.setDate(LocalDateTime.now());
	        logEvent.setDescription(String.format(Constants.MAPPING_EVENT_ERROR, eventdto.toString(), e.getMessage()));
	        LOG.info(logEvent.toString());
	        return null;
	    }
		return null;
	}

}
