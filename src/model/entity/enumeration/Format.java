package model.entity.enumeration;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public enum Format {

	EN_LIGNE, PRESENTIEL;

	public static List<String> getAllFormat() {
		List<String> format = Arrays.asList(Format.values()).stream().map(e -> e.name()).collect(Collectors.toList());
		return format;
	}

	public static Format formString(String format) {
		return format.isEmpty() ? null : Format.valueOf(format);
	}

}
