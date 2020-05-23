package com.semanticversion.gradle.plugin.commons;

public class TypeUtils {

	public static Float getFloat(String value) {
		return getFloat(value, null);
	}
	
	public static Float getFloat(String value, Float defaultValue) {
		return StringUtils.isNotEmpty(value) ? Float.valueOf(value) : defaultValue;
	}

	public static Float getSafeFloat(String value) {
		return getSafeFloat(value, null);
	}

	public static Float getSafeFloat(String value, Float defaultValue) {
		try {
			return getFloat(value, defaultValue);
		} catch (NumberFormatException e) {
			return null;
		}
	}
	
	public static Integer getInteger(String value) {
		return getInteger(value, null);
	}
	
	public static Integer getInteger(String value, Integer defaultValue) {
		return StringUtils.isNotEmpty(value) ? Integer.valueOf(value) : defaultValue;
	}
	
	public static Integer getSafeInteger(String value) {
		return getSafeInteger(value, null);
	}

	public static Integer getSafeInteger(String value, Integer defaultValue) {
		try {
			return getInteger(value, defaultValue);
		} catch (NumberFormatException e) {
			return null;
		}
	}
	
	public static Long getLong(String value) {
		return getLong(value, null);
	}

	public static Long getLong(String value, Long defaultValue) {
		return StringUtils.isNotEmpty(value) ? Long.valueOf(value) : defaultValue;
	}

	public static Long getSafeLong(String value) {
		return getSafeLong(value, null);
	}

	public static Long getSafeLong(String value, Long defaultValue) {
		try {
			return getLong(value, defaultValue);
		} catch (NumberFormatException e) {
			return null;
		}
	}
	
	public static Double getDouble(String value) {
		return getDouble(value, null);
	}

	public static Double getDouble(String value, Double defaultValue) {
		return StringUtils.isNotEmpty(value) ? Double.valueOf(value) : defaultValue;
	}

	public static Double getSafeDouble(String value) {
		return getSafeDouble(value, null);
	}

	public static Double getSafeDouble(String value, Double defaultValue) {
		try {
			return getDouble(value, defaultValue);
		} catch (NumberFormatException e) {
			return null;
		}
	}

	public static Boolean getBooleanFromNumber(String value) {
		return StringUtils.isNotEmpty(value) ? "1".equals(value) : null;
	}

	public static Boolean getBoolean(String value) {
		return getBoolean(value, null);
	}

	public static Boolean getBoolean(String value, Boolean defaultValue) {
		if (StringUtils.isNotEmpty(value)) {
			if (value.equalsIgnoreCase("true")) {
				return true;
			} else if (value.equalsIgnoreCase("false")) {
				return false;
			} else {
				throw new RuntimeException("Invalid Boolean value: " + value);
			}
		} else {
			return defaultValue;
		}
	}

	public static Boolean getSafeBoolean(String value) {
		return getSafeBoolean(value, null);
	}

	public static Boolean getSafeBoolean(String value, Boolean defaultValue) {
		try {
			return getBoolean(value, defaultValue);
		} catch (RuntimeException e) {
			return null;
		}
	}

	public static String getString(Integer value) {
		return value != null ? value.toString() : null;
	}
}
