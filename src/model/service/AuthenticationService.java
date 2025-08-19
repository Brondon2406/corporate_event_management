package model.service;

import controller.dto.Userdto;

public interface AuthenticationService {
	
	public Userdto registerUser ();
	public Userdto loginUser ();
	

}
