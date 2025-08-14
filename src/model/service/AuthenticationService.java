package model.service;

import controller.dto.Userdto;

public interface AuthenticationService {
	
	public Userdto registerUser (Userdto UserDTO);
	public Userdto loginUser (Userdto UserDTO);
	

}
