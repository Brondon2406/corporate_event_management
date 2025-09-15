package model.service;

import java.util.List;

import model.dto.Userdto;

public interface UserService {

	public Userdto createUser(Userdto userdto);

	public boolean updateUser(Userdto userDTO);

	public boolean deleteUser(int userId);

	public Userdto getUserById(int userId);

	public List<Userdto> getAllUsers();
	
	public List<Userdto> findUsersByRole(String role);

}
