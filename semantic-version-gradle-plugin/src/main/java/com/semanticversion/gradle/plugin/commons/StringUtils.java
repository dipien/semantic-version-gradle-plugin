package com.semanticversion.gradle.plugin.commons;

import java.util.List;


/**
 * This class contains functions for managing Strings
 */
public abstract class StringUtils {
	
	public final static String COMMA = ",";

	@Deprecated
	public static Boolean isEmpty(String text) {
		return text != null ? text.length() == 0 : true;
	}

	@Deprecated
	public static Boolean isNotEmpty(String text) {
		return !isEmpty(text);
	}
	
	public static List<String> splitToListWithCommaSeparator(String text) {
		return splitToList(text, COMMA);
	}

	public static List<String> splitToList(String text, String separator) {
		List<String> values = ListUtils.newArrayList();
		if (isNotEmpty(text)) {
			values = ListUtils.newArrayList(text.split(separator));
		}
		return values;
	}
}
