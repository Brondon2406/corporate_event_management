package model.service;

import controller.dto.Userdto;

public interface UserService {
	
	public Userdto createUser (Userdto userDTO);
	public Userdto updateUser (Userdto userDTO);
	public String deleteUser (int userId);
	public Userdto getUserById (int userId);

}
