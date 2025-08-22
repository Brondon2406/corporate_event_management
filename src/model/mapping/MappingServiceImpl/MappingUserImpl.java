package controller.utilities.MappingServiceImpl;

import java.time.LocalDateTime;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import controller.dto.Userdto;
import controller.utilities.MappingService.MappingUser;
import model.entity.LogEvent;
import model.entity.Users;
import model.entity.enumeration.Role;
import util.Constants;

public class MappingUserImpl implements MappingUser {
	private static final Logger LOG = LogManager.getLogger(MappingUserImpl.class);
	private final LogEvent logEvent;

	public MappingUserImpl(LogEvent logEvent) {
		this.logEvent = logEvent;
	}

	public MappingUserImpl() {
		this.logEvent = new LogEvent();
	}

	@Override
	public Userdto convertUserToUserdto(Users user) {
		if (user == null) {
			logEvent.setAction("convert User to Userdto");
			logEvent.setDate(LocalDateTime.now());
			logEvent.setDescription(Constants.USER_IS_EMPTY);
			LOG.info(logEvent.toString());
			return null;
		}
		try {
			Userdto userdto = new Userdto();
			userdto.setEmail(user.getEmail().isEmpty() ? null : user.getEmail());
			userdto.setFonction(user.getFonction().isEmpty() ? null : user.getFonction());
			userdto.setName(user.getName().isEmpty() ? null : user.getName());
			userdto.setPassword(user.getPassword().isEmpty() ? null : user.getPassword());
			userdto.setId(user.getId() <= 0 ? 0 : user.getId());
			userdto.setRole(user.getRole() == null ? null : user.getRole().name().toUpperCase());
			return userdto;
		} catch (Exception e) {
			logEvent.setAction("convert User to Userdto");
			logEvent.setDate(LocalDateTime.now());
			logEvent.setDescription(String.format(Constants.MAPPING_USER_DTO_ERROR, user.toString(), e.getMessage()));
			LOG.info(logEvent);
			return null;
		}
	}

	@Override
	public Users convertUserdtoToUsers(Userdto userdto) {
		if (userdto == null) {
			logEvent.setAction("convert Userdto to Users");
			logEvent.setDate(LocalDateTime.now());
			logEvent.setDescription(Constants.USER_DTO_IS_EMPTY);
			LOG.info(logEvent.toString());
			return null;
		}

		try {
			Users user = new Users();
			user.setEmail(userdto.getEmail().isEmpty() ? null : userdto.getEmail());
			user.setFonction(userdto.getFonction().isEmpty() ? null : userdto.getFonction());
			user.setName(userdto.getName().isEmpty() ? null : userdto.getName());
			user.setPassword(userdto.getPassword().isEmpty() ? null : userdto.getPassword());
			user.setRole(userdto.getRole().isEmpty() ? null : Role.fromString(userdto.getRole()));
			return user;
		} catch (Exception e) {
			logEvent.setAction("convert Userdto to Users");
			logEvent.setDate(LocalDateTime.now());
			logEvent.setDescription(String.format(Constants.MAPPING_USER_ERROR, userdto.toString(), e.getMessage()));
			LOG.info(logEvent.toString());
			return null;
		}
	}
}
