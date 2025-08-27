package model.mapping.MappingService;

import model.dto.Eventdto;
import model.entity.Event;

public interface MappingEvent {
	
	Event convertEventdtoToEvent(Eventdto eventdto);
	Eventdto convertEventToEventdto(Event event);

}
