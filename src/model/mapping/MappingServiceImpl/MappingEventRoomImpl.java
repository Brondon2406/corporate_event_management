package model.mapping.MappingServiceImpl;

import java.time.LocalDateTime;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import model.dto.EventRoomdto;
import model.entity.EventRoom;
import model.entity.LogEvent;
import model.mapping.MappingService.MappingEventRoom;
import util.constants.Constants;

public class MappingEventRoomImpl implements MappingEventRoom {
	private static final Logger LOG = LogManager.getLogger(MappingEventImpl.class);
	private LogEvent logEvent;

	public MappingEventRoomImpl(LogEvent logEvent) {
		this.logEvent = logEvent;
	}

	public MappingEventRoomImpl() {
		this.logEvent = new LogEvent();
	}

	@Override
	public EventRoomdto convertEventRoomToEventRoomdto(EventRoom eventRoom) {
		if (eventRoom == null) {
			logEvent.setAction("convert eventRoom to eventRoomdto");
			logEvent.setDate(LocalDateTime.now());
			logEvent.setDescription(Constants.EVENTROOM_IS_EMPTY);
			LOG.info(logEvent.toString());
			return null;
		}
		try {
			EventRoomdto eventRoomdto = new EventRoomdto();
			eventRoomdto.setName(eventRoom.getName());
			eventRoomdto.setId(eventRoom.getId());
			eventRoomdto.setCapacity(eventRoom.getCapacity());
			eventRoomdto.setActive(eventRoom.isActive());
			return eventRoomdto;

		} catch (Exception e) {
			logEvent.setAction("convert Planning to Planningdto");
			logEvent.setDate(LocalDateTime.now());
			logEvent.setDescription(String.format(Constants.EVENTROOM_DTO_ERROR, eventRoom.toString(), e.getMessage()));
			LOG.info(logEvent);
		}
		return null;
	}

	@Override
	public EventRoom convertEventRoomdtoToEventRoom(EventRoomdto eventRoomdto) {
		if (eventRoomdto == null) {
			logEvent.setAction("convert eventRoomdto to eventRoom");
			logEvent.setDate(LocalDateTime.now());
			logEvent.setDescription(Constants.EVENTROOM_DTO_IS_EMPTY);
			LOG.info(logEvent.toString());
			return null;
		}
		try {
			EventRoom eventRoom = new EventRoom();
			eventRoom.setName(eventRoomdto.getName());
			eventRoom.setId(eventRoomdto.getId());
			eventRoom.setCapacity(eventRoomdto.getCapacity());
			eventRoom.setActive(eventRoomdto.isActive());
			return eventRoom;

		} catch (Exception e) {
			logEvent.setAction("convert eventRoomdto to eventRoom");
			logEvent.setDate(LocalDateTime.now());
			logEvent.setDescription(
					String.format(Constants.MAPPING_EVENTROOM_ERROR, eventRoomdto.toString(), e.getMessage()));
			LOG.info(logEvent);
		}
		return null;
	}
}
