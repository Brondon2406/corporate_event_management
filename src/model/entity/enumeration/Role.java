package model.entity.enumeration;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public enum Role {
	ADMIN,
	COLLABORATORS,
	ANIMATORS;

	public static List<String> getUserRoles(){
		List<String> roles = Arrays.asList(Role.values()).stream()
				.filter(e -> !e.equals(ADMIN))
				.map(e-> e.name())
				.collect(Collectors.toList());
		return roles;
	}
   	public static Role fromString (String role) {
		return role.isEmpty() ? null : Role.valueOf(role);
	}
	boolean isEmpty() {
		// TODO Auto-generated method stub
		return false;
	}

	
}
