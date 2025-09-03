package model.mapping.MappingServiceImpl;

import java.time.LocalDateTime;
import java.util.ArrayList;


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
			Eventdto eventdto = new Eventdto(event.getId() > 0 ? event.getId() : 0,
					(event.getTitle() != null && !event.getTitle().isEmpty()) ? event.getTitle() : null,
					event.getDateDebut(), event.getDateFin(),
					(event.getTypeEvent() != null) ? event.getTypeEvent().name() : null, event.getEventRoom(),
					(event.getFormat() != null) ? event.getFormat().name() : null, event.getModerator(), // déjà String
					event.getTutor(),
					(event.getExternalParticipantsEmails() != null) ? event.getExternalParticipantsEmails()
							: new ArrayList<>(),
					(event.getUsers() != null) ? event.getUsers() : new ArrayList<>(), 0);

			return eventdto;

		} catch (Exception e) {
			logEvent.setAction("convert Event to Eventdto");
			logEvent.setDate(LocalDateTime.now());
			logEvent.setDescription(String.format(Constants.MAPPING_EVENT_DTO_ERROR, event.toString(), e.getMessage()));
			LOG.error(logEvent.toString(), e);
			return null;
		}
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
			event.setTypeEvent(
					eventdto.getTypeEvent().isEmpty() ? null : TypeEvent.fromString(eventdto.getTypeEvent()));
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
