package io.flatf.common.lang;

import java.math.BigDecimal;

public final class Setter {

    private Setter() {
    }

    public static String defaultString(String value) {
        return defaultString(value, "");
    }

    public static String defaultString(String value, String fallback) {
        return value == null ? fallback : value;
    }

    public static String defaultTrimmedString(String value) {
        return defaultTrimmedString(value, "");
    }

    public static String defaultTrimmedString(String value, String fallback) {
        String trimmedString = trimmedString(value);
        return trimmedString.isEmpty() ? fallback : trimmedString;
    }

    public static String trimmedString(String value) {
        return value == null ? "" : value.trim();
    }

    public static String trimmedStringOrNull(String value) {
        return value == null || value.isBlank() ? null : value.trim();
    }

    public static BigDecimal defaultBigDecimal(BigDecimal value, BigDecimal fallback) {
        return value == null ? fallback : value;
    }

    public static int defaultPositiveInteger(Integer value, int fallback) {
        return value == null || value <= 0 ? fallback : value;
    }

}
