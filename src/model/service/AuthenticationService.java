package model.service;

import controller.dto.Userdto;
import model.entity.Users;

public interface AuthenticationService {
	
	public Userdto registerUser (Users user);
	public Userdto loginUser (Users user);
	
	

}
