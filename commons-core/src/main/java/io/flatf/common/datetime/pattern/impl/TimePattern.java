package io.flatf.common.datetime.pattern.impl;

import io.flatf.common.datetime.pattern.TemporalPattern;

import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * 常用时间格式列表
 *
 * @author yellow013
 */
public enum TimePattern implements TemporalPattern<LocalTime> {

    /**
     * example: 13
     */
    HH("HH"),

    /**
     * example: 1314
     */
    HHMM("HHmm"),

    /**
     * example: 131423
     */
    HHMMSS("HHmmss"),

    /**
     * example: 131423678
     */
    HHMMSSSSS("HHmmssSSS"),

    /**
     * example: 131423678789
     */
    HHMMSSSSSSSS("HHmmssSSSSSS"),

    /**
     * example: 13:14
     */
    HH_MM("HH:mm"),

    /**
     * example: 13:14:23
     */
    HH_MM_SS("HH:mm:ss"),

    /**
     * example: 13:14:23.678
     */
    HH_MM_SS_SSS("HH:mm:ss.SSS"),

    /**
     * example: 13:14:23.678789
     */
    HH_MM_SS_SSSSSS("HH:mm:ss.SSSSSS"),

    ;

    private final String pattern;

    private final DateTimeFormatter formatter;

    TimePattern(String pattern) {
        this.pattern = pattern;
        this.formatter = DateTimeFormatter.ofPattern(pattern);
    }

    /**
     * @return the pattern string
     */
    @Override
    public String getPattern() {
        return pattern;
    }

    /**
     * @return the DateTimeFormatter instance
     */
    @Override
    public DateTimeFormatter getFormatter() {
        return formatter;
    }

    /**
     * @param zoneId ZoneId
     * @return String
     */
    @Override
    public String now(ZoneId zoneId) {
        return fmt(LocalTime.now(zoneId));
    }

    /**
     * @param temporalObj T extends Temporal
     * @return String
     */
    @Override
    public String fmt(LocalTime temporalObj) {
        return formatter.format(temporalObj);
    }

    /**
     * @param text String
     * @return LocalTime
     */
    @Override
    public LocalTime parse(String text) {
        return LocalTime.parse(text, getFormatter());
    }

    /**
     * @param text String
     * @return LocalTime
     * @throws DateTimeParseException e
     */
    public static LocalTime tryParse(String text) throws DateTimeParseException {
        for (TimePattern pattern : TimePattern.values()) {
            try {
                return LocalTime.parse(text, pattern.formatter);
            } catch (DateTimeParseException ignored) {
            }
        }
        throw new DateTimeParseException("No matching pattern", text, 0);
    }

}
