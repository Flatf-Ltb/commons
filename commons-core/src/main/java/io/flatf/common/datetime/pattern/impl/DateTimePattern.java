package io.flatf.common.datetime.pattern.impl;

import io.flatf.common.datetime.pattern.TemporalPattern;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import static io.flatf.common.character.Separator.BLANK;
import static io.flatf.common.character.Separator.LINE;
import static io.flatf.common.datetime.pattern.impl.DatePattern.YYMMDD;
import static io.flatf.common.datetime.pattern.impl.DatePattern.YYYYMMDD;
import static io.flatf.common.datetime.pattern.impl.DatePattern.YYYY_MM_DD;
import static io.flatf.common.datetime.pattern.impl.DatePattern.YY_MM_DD;
import static io.flatf.common.datetime.pattern.impl.TimePattern.HH;
import static io.flatf.common.datetime.pattern.impl.TimePattern.HHMM;
import static io.flatf.common.datetime.pattern.impl.TimePattern.HHMMSS;
import static io.flatf.common.datetime.pattern.impl.TimePattern.HHMMSSSSS;
import static io.flatf.common.datetime.pattern.impl.TimePattern.HHMMSSSSSSSS;
import static io.flatf.common.datetime.pattern.impl.TimePattern.HH_MM_SS;
import static io.flatf.common.datetime.pattern.impl.TimePattern.HH_MM_SS_SSS;
import static io.flatf.common.datetime.pattern.impl.TimePattern.HH_MM_SS_SSSSSS;

/**
 * 常用日期时间格式列表
 *
 * @author yellow013
 */
public enum DateTimePattern implements TemporalPattern<LocalDateTime> {

    /**
     * example: 18031413
     */
    YYMMDDHH(YYMMDD.getPattern() + HH.getPattern()),

    /**
     * example: 1803141314
     */
    YYMMDDHHMM(YYMMDD.getPattern() + HHMM.getPattern()),

    /**
     * example: 180314131423
     */
    YYMMDDHHMMSS(YYMMDD.getPattern() + HHMMSS.getPattern()),

    /**
     * example: 180314131423678
     */
    YYMMDDHHMMSSSSS(YYMMDD.getPattern() + HHMMSSSSS.getPattern()),

    /**
     * example: 2018031413
     */
    YYYYMMDDHH(YYYYMMDD.getPattern() + HH.getPattern()),

    /**
     * example: 201803141314
     */
    YYYYMMDDHHMM(YYYYMMDD.getPattern() + HHMM.getPattern()),

    /**
     * example: 20180314131423
     */
    YYYYMMDDHHMMSS(YYYYMMDD.getPattern() + HHMMSS.getPattern()),

    /**
     * example: 20180314131423678
     */
    YYYYMMDDHHMMSSSSS(YYYYMMDD.getPattern() + HHMMSSSSS.getPattern()),

    /*
     *============================================================================================
     */

    /**
     * example: 180314 131423
     */
    YYMMDD_HHMMSS(YYMMDD.getPattern() + BLANK + HHMMSS.getPattern()),

    /**
     * example: 180314 131423678
     */
    YYMMDD_HHMMSSSSS(YYMMDD.getPattern() + BLANK + HHMMSSSSS.getPattern()),

    /**
     * example: 180314 131423678789
     */
    YYMMDD_HHMMSSSSSSSS(YYMMDD.getPattern() + BLANK + HHMMSSSSSSSS.getPattern()),

    /**
     * example: 180314-131423
     */
    YYMMDD_L_HHMMSS(YYMMDD.getPattern() + LINE + HHMMSS.getPattern()),

    /**
     * example: 180314-131423678
     */
    YYMMDD_L_HHMMSSSSS(YYMMDD.getPattern() + LINE + HHMMSSSSS.getPattern()),

    /**
     * example: 180314-131423678789
     */
    YYMMDD_L_HHMMSSSSSSSS(YYMMDD.getPattern() + LINE + HHMMSSSSSSSS.getPattern()),

    /**
     * example: 20180314 131423
     */
    YYYYMMDD_HHMMSS(YYYYMMDD.getPattern() + BLANK + HHMMSS.getPattern()),

    /**
     * example: 20180314 131423678
     */
    YYYYMMDD_HHMMSSSSS(YYYYMMDD.getPattern() + BLANK + HHMMSSSSS.getPattern()),

    /**
     * example: 20180314 131423678789
     */
    YYYYMMDD_HHMMSSSSSSSS(YYYYMMDD.getPattern() + BLANK + HHMMSSSSSSSS.getPattern()),

    /**
     * example: 20180314-131423
     */
    YYYYMMDD_L_HHMMSS(YYYYMMDD.getPattern() + LINE + HHMMSS.getPattern()),

    /**
     * example: 20180314-131423678
     */
    YYYYMMDD_L_HHMMSSSSS(YYYYMMDD.getPattern() + LINE + HHMMSSSSS.getPattern()),

    /**
     * example: 20180314-131423678789
     */
    YYYYMMDD_L_HHMMSSSSSSSS(YYYYMMDD.getPattern() + LINE + HHMMSSSSSSSS.getPattern()),

    /*
     *============================================================================================
     */

    /**
     * example: 180314 13:14:23
     */
    YYMMDD_HH_MM_SS(YYMMDD.getPattern() + BLANK + HH_MM_SS.getPattern()),

    /**
     * example: 180314 13:14:23.678
     */
    YYMMDD_HH_MM_SS_SSS(YYMMDD.getPattern() + BLANK + HH_MM_SS_SSS.getPattern()),

    /**
     * example: 180314T13:14:23.678789
     */
    YYMMDD_HH_MM_SS_SSSSSS(YYMMDD.getPattern() + BLANK + HH_MM_SS_SSSSSS.getPattern()),

    /**
     * example: 180314-13:14:23
     */
    YYMMDD_L_HH_MM_SS(YYMMDD.getPattern() + LINE + HH_MM_SS.getPattern()),

    /**
     * example: 180314-13:14:23.678
     */
    YYMMDD_L_HH_MM_SS_SSS(YYMMDD.getPattern() + LINE + HH_MM_SS_SSS.getPattern()),

    /**
     * example: 180314-13:14:23.678789
     */
    YYMMDD_L_HH_MM_SS_SSSSSS(YYMMDD.getPattern() + LINE + HH_MM_SS_SSSSSS.getPattern()),

    /**
     * example: 20180314 13:14:23
     */
    YYYYMMDD_HH_MM_SS(YYYYMMDD.getPattern() + BLANK + HH_MM_SS.getPattern()),

    /**
     * example: 20180314 13:14:23.678
     */
    YYYYMMDD_HH_MM_SS_SSS(YYYYMMDD.getPattern() + BLANK + HH_MM_SS_SSS.getPattern()),

    /**
     * example: 20180314 13:14:23.678789
     */
    YYYYMMDD_HH_MM_SS_SSSSSS(YYYYMMDD.getPattern() + BLANK + HH_MM_SS_SSSSSS.getPattern()),

    /**
     * example: 20180314-13:14:23
     */
    YYYYMMDD_L_HH_MM_SS(YYYYMMDD.getPattern() + LINE + HH_MM_SS.getPattern()),

    /**
     * example: 20180314-13:14:23.678
     */
    YYYYMMDD_L_HH_MM_SS_SSS(YYYYMMDD.getPattern() + LINE + HH_MM_SS_SSS.getPattern()),

    /**
     * example: 20180314-13:14:23.678789
     */
    YYYYMMDD_L_HH_MM_SS_SSSSSS(YYYYMMDD.getPattern() + LINE + HH_MM_SS_SSSSSS.getPattern()),

    /*
     *============================================================================================
     */

    /**
     * example: 18-03-14 131423
     */
    YY_MM_DD_HHMMSS(YY_MM_DD.getPattern() + BLANK + HHMMSS.getPattern()),

    /**
     * example: 18-03-14 131423678
     */
    YY_MM_DD_HHMMSSSSS(YY_MM_DD.getPattern() + BLANK + HHMMSSSSS.getPattern()),

    /**
     * example: 18-03-14 131423678789
     */
    YY_MM_DD_HHMMSSSSSSSS(YY_MM_DD.getPattern() + BLANK + HHMMSSSSSSSS.getPattern()),

    /**
     * example: 2018-03-14 131423
     */
    YYYY_MM_DD_HHMMSS(YYYY_MM_DD.getPattern() + BLANK + HHMMSS.getPattern()),

    /**
     * example: 2018-03-14 131423678
     */
    YYYY_MM_DD_HHMMSSSSS(YYYY_MM_DD.getPattern() + BLANK + HHMMSSSSS.getPattern()),

    /**
     * example: 2018-03-14 131423678789
     */
    YYYY_MM_DD_HHMMSSSSSSSS(YYYY_MM_DD.getPattern() + BLANK + HHMMSSSSSSSS.getPattern()),

    /*
     *============================================================================================
     */

    /**
     * example: 18-03-14 13:14:23
     */
    YY_MM_DD_HH_MM_SS(YY_MM_DD.getPattern() + BLANK + HH_MM_SS.getPattern()),

    /**
     * example: 18-03-14 13:14:23.678
     */
    YY_MM_DD_HH_MM_SS_SSS(YY_MM_DD.getPattern() + BLANK + HH_MM_SS_SSS.getPattern()),

    /**
     * example: 18-03-14 13:14:23.678789
     */
    YY_MM_DD_HH_MM_SS_SSSSSS(YY_MM_DD.getPattern() + BLANK + HH_MM_SS_SSSSSS.getPattern()),

    /**
     * example: 2018-03-14 13:14:23
     */
    YYYY_MM_DD_HH_MM_SS(YYYY_MM_DD.getPattern() + BLANK + HH_MM_SS.getPattern()),

    /**
     * example: 2018-03-14 13:14:23.678
     */
    YYYY_MM_DD_HH_MM_SS_SSS(YYYY_MM_DD.getPattern() + BLANK + HH_MM_SS_SSS.getPattern()),

    /**
     * example: 2018-03-14 13:14:23.678789
     */
    YYYY_MM_DD_HH_MM_SS_SSSSSS(YYYY_MM_DD.getPattern() + BLANK + HH_MM_SS_SSSSSS.getPattern()),

    ;

    private final String pattern;

    private final DateTimeFormatter formatter;

    /**
     * @param pattern String
     */
    DateTimePattern(String pattern) {
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
        return fmt(LocalDateTime.now(zoneId));
    }

    /**
     * @param temporalObj T extends Temporal
     * @return String
     */
    @Override
    public String fmt(LocalDateTime temporalObj) {
        return formatter.format(temporalObj);
    }

    /**
     * @param text String
     * @return T extends Temporal
     */
    @Override
    public LocalDateTime parse(String text) {
        return LocalDateTime.parse(text, formatter);
    }

    /**
     * @param text String
     * @return LocalDateTime
     * @throws DateTimeParseException e
     */
    public static LocalDateTime tryParse(String text) throws DateTimeParseException {
        for (DateTimePattern pattern : DateTimePattern.values()) {
            try {
                return LocalDateTime.parse(text, pattern.formatter);
            } catch (DateTimeParseException ignored) {
            }
        }
        throw new DateTimeParseException("No matching pattern", text, 0);
    }

}
