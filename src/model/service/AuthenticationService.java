package model.service;

import model.dto.Userdto;
import model.entity.Users;

public interface AuthenticationService {
	
	public Userdto registerUser (Users user);
	public Userdto loginUser (Users user) ;
	
	

}
