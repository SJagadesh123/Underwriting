package com.zettamine.mpa.ucm.utility;

public class StringUtils {

	public static String removeAllSpaces(String text) {
		return text.replaceAll("\\s+", "");
	}

	public static String trimSpacesBetween(String text) {
		return text.replaceAll("\\s+", " ").trim();
	}
}
