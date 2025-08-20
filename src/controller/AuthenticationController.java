package controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import controller.dto.Userdto;
import controller.utilities.MappingService.MappingUser;
import controller.utilities.MappingServiceImpl.MappingUserImpl;
import model.entity.Users;
import model.service.AuthenticationService;
import model.service.implementation.AuthenticationServiceImpl;
import util.Constants;

public class AuthenticationController {

	private AuthenticationService service;
	private MappingUser mapper;
	
	private static final Logger LOG = LogManager.getLogger(AuthenticationController.class);
	
	public AuthenticationController(AuthenticationServiceImpl service, MappingUserImpl mapper) {
		this.service = service;
		this.mapper = mapper;
	}
	
	public AuthenticationController() {}

	public Userdto AuthenticationController(Userdto userdto) {
		if(userdto == null) {
			LOG.error(Constants.EMPTY_USER_DTO);
			return null;
		}
		Users user = mapper.convertUserdtoToUsers(userdto);
		Userdto dto = service.registerUser(user);
		if(dto == null) {
			LOG.error(Constants.EMPTY_USER_DTO);
			return null;
		}
		return dto;
	}
	
}
