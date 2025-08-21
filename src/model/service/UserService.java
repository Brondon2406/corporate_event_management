package model.service;

import controller.dto.Userdto;
import model.entity.Users;

public interface UserService {
	
	public Userdto createUser (Users user);
	public Userdto updateUser (Userdto userDTO);
	public String deleteUser (int userId);
	public Userdto getUserById (int userId);
	
	

}
