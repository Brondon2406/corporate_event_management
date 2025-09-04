package model.service;

import java.util.List;

import model.dto.Userdto;
import model.entity.Users;


public interface UserService {

	public Userdto createUser(Users user);

	public boolean updateUser(Userdto userDTO);

	public boolean deleteUser(int userId);

	public Userdto getUserById(int userId);

	public List<Userdto> getAllUsers();

}
