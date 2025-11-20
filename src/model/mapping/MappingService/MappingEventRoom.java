package model.mapping.MappingService;

import model.dto.EventRoomdto;
import model.entity.EventRoom;

public interface MappingEventRoom {
	
	EventRoomdto convertEventRoomToEventRoomdto(EventRoom eventRoom);
	EventRoom convertEventRoomdtoToEventRoom(EventRoomdto eventRoomdto);

}
