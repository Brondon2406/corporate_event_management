package model.entity.enumeration;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public enum TypeEvent {

	REUNION,
	FORMATION,
	ATELIER,
	TEAM_BUILDING;
	
	public static List<String> getAllTypeEvent(){
		List<String> roles = Arrays.asList(Role.values()).stream()
				.map(e-> e.name())
				.collect(Collectors.toList());
		return roles;
	}
}
