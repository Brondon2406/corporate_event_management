package model.service;

import model.dto.Eventdto;
import model.entity.Event;

public interface EventService {
   public Eventdto registerEvent(Event event);              
   
}
