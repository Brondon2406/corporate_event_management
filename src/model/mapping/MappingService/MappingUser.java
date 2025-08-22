package model.mapping.MappingService;

import model.dto.Userdto;
import model.entity.Users;

public interface MappingUser {

	Userdto convertUserToUserdto(Users user);
	Users convertUserdtoToUsers(Userdto userdto);
}
