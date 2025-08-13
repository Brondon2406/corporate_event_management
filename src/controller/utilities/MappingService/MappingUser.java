package controller.utilities.MappingService;

import controller.dto.Userdto;
import model.entity.Users;

public interface MappingUser {

	Userdto convertUserToUserdto(Users user);
	Users convertUserdtoToUsers(Userdto userdto);
}
