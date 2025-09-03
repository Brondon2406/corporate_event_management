package model.entity.enumeration;

import java.util.Arrays;	
import java.util.List;
import java.util.stream.Collectors;

public enum TypeEvent {

	REUNION, FORMATION, ATELIER, TEAM_BUILDING;

	public static List<String> getAllTypeEvent() {
		List<String> typeEvent = Arrays.asList(TypeEvent.values()).stream().map(e -> e.name()).collect(Collectors.toList());
		return typeEvent;
	}

	public static TypeEvent fromString(String typeEvent) {
	return typeEvent.isEmpty() ? null : TypeEvent.valueOf(typeEvent);
	}
	
}
