package io.flatf.common.datetime.pattern.impl;

import io.flatf.common.datetime.pattern.TemporalPattern;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * 日期格式列表
 *
 * @author yellow013
 */
public enum DatePattern implements TemporalPattern<LocalDate> {

    /**
     * example: 1803
     */
    YYMM("yyMM"),

    /**
     * example: 201803
     */
    YYYYMM("yyyyMM"),

    /**
     * example: 180314
     */
    YYMMDD("yyyyMMdd"),

    /**
     * example: 20180314
     */
    YYYYMMDD("yyyyMMdd"),

    /**
     * example: 18-03
     */
    YY_MM("yy-MM"),

    /**
     * example: 2018-03
     */
    YYYY_MM("yyyy-MM"),

    /**
     * example: 18-03-14
     */
    YY_MM_DD("yyyy-MM-dd"),

    /**
     * example: 2018-03-14
     */
    YYYY_MM_DD("yyyy-MM-dd"),

    ;

    private final String pattern;

    private final DateTimeFormatter formatter;

    DatePattern(String pattern) {
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
        return fmt(LocalDate.now(zoneId));
    }

    /**
     * @param temporalObj T extends Temporal
     * @return String
     */
    @Override
    public String fmt(LocalDate temporalObj) {
        return formatter.format(temporalObj);
    }

    /**
     * @param text String
     * @return LocalDate
     */
    @Override
    public LocalDate parse(String text) {
        return LocalDate.parse(text, formatter);
    }

    /**
     * @param text String
     * @return LocalDate
     * @throws DateTimeParseException e
     */
    public static LocalDate tryParse(String text) throws DateTimeParseException {
        for (DatePattern pattern : DatePattern.values()) {
            try {
                return LocalDate.parse(text, pattern.formatter);
            } catch (DateTimeParseException ignored) {
            }
        }
        throw new DateTimeParseException("No matching pattern", text, 0);
    }

}
