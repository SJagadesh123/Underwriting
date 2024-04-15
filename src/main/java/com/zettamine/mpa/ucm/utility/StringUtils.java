package com.zettamine.mpa.ucm.utility;

public class StringUtils {

	public static String removeAllSpaces(String text) {
		if(text!=null) {
		return text.replaceAll("\\s+", "");
		}
		
		return null;
	}

	public static String trimSpacesBetween(String text) {
		if(text!=null) {
		return text.replaceAll("\\s+", " ").trim();
		}
		
		return null;
	}
}
