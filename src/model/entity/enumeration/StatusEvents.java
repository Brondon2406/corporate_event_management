package model.entity.enumeration;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public enum StatusEvents {

	PENDING, VALIDATED, REJECTED, CANCELED, EXPIRED;

	public static List<String> getAllStatus() {
		List<String> status = Arrays.asList(TypeEvent.values()).stream().map(e -> e.name())
				.collect(Collectors.toList());
		return status;
	}

	public static StatusEvents fromString(String status) {
		return status.isEmpty() ? null : StatusEvents.valueOf(status);
	}

	public boolean isEmpty() {
		return false;
	}

}
