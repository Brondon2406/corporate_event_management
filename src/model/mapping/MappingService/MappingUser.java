package model.mapping.MappingService;

import model.entity.Event;
import model.dto.Eventdto;
import model.dto.Userdto;
import model.entity.Users;

public interface MappingUser {

	Userdto convertUserToUserdto(Users user);
	Users convertUserdtoToUsers(Userdto userdto);
	Event convertEventdtoToEvent(Eventdto eventdto);
}
