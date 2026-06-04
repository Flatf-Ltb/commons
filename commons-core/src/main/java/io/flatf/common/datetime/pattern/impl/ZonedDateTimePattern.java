package io.flatf.common.datetime.pattern.impl;

import io.flatf.common.datetime.pattern.TemporalPattern;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import static io.flatf.common.datetime.pattern.impl.DateTimePattern.YYMMDDHH;
import static io.flatf.common.datetime.pattern.impl.DateTimePattern.YYMMDDHHMM;
import static io.flatf.common.datetime.pattern.impl.DateTimePattern.YYMMDDHHMMSS;
import static io.flatf.common.datetime.pattern.impl.DateTimePattern.YYMMDDHHMMSSSSS;
import static io.flatf.common.datetime.pattern.impl.DateTimePattern.YYMMDD_HHMMSS;
import static io.flatf.common.datetime.pattern.impl.DateTimePattern.YYMMDD_HHMMSSSSS;
import static io.flatf.common.datetime.pattern.impl.DateTimePattern.YYMMDD_HHMMSSSSSSSS;
import static io.flatf.common.datetime.pattern.impl.DateTimePattern.YYMMDD_HH_MM_SS;
import static io.flatf.common.datetime.pattern.impl.DateTimePattern.YYMMDD_HH_MM_SS_SSS;
import static io.flatf.common.datetime.pattern.impl.DateTimePattern.YYMMDD_HH_MM_SS_SSSSSS;
import static io.flatf.common.datetime.pattern.impl.DateTimePattern.YYMMDD_L_HHMMSS;
import static io.flatf.common.datetime.pattern.impl.DateTimePattern.YYMMDD_L_HHMMSSSSS;
import static io.flatf.common.datetime.pattern.impl.DateTimePattern.YYMMDD_L_HHMMSSSSSSSS;
import static io.flatf.common.datetime.pattern.impl.DateTimePattern.YYMMDD_L_HH_MM_SS;
import static io.flatf.common.datetime.pattern.impl.DateTimePattern.YYMMDD_L_HH_MM_SS_SSS;
import static io.flatf.common.datetime.pattern.impl.DateTimePattern.YYMMDD_L_HH_MM_SS_SSSSSS;
import static io.flatf.common.datetime.pattern.impl.DateTimePattern.YYYYMMDDHH;
import static io.flatf.common.datetime.pattern.impl.DateTimePattern.YYYYMMDDHHMM;
import static io.flatf.common.datetime.pattern.impl.DateTimePattern.YYYYMMDDHHMMSS;
import static io.flatf.common.datetime.pattern.impl.DateTimePattern.YYYYMMDDHHMMSSSSS;
import static io.flatf.common.datetime.pattern.impl.DateTimePattern.YYYYMMDD_HHMMSS;
import static io.flatf.common.datetime.pattern.impl.DateTimePattern.YYYYMMDD_HHMMSSSSS;
import static io.flatf.common.datetime.pattern.impl.DateTimePattern.YYYYMMDD_HHMMSSSSSSSS;
import static io.flatf.common.datetime.pattern.impl.DateTimePattern.YYYYMMDD_HH_MM_SS;
import static io.flatf.common.datetime.pattern.impl.DateTimePattern.YYYYMMDD_HH_MM_SS_SSS;
import static io.flatf.common.datetime.pattern.impl.DateTimePattern.YYYYMMDD_HH_MM_SS_SSSSSS;
import static io.flatf.common.datetime.pattern.impl.DateTimePattern.YYYYMMDD_L_HHMMSS;
import static io.flatf.common.datetime.pattern.impl.DateTimePattern.YYYYMMDD_L_HHMMSSSSS;
import static io.flatf.common.datetime.pattern.impl.DateTimePattern.YYYYMMDD_L_HHMMSSSSSSSS;
import static io.flatf.common.datetime.pattern.impl.DateTimePattern.YYYYMMDD_L_HH_MM_SS;
import static io.flatf.common.datetime.pattern.impl.DateTimePattern.YYYYMMDD_L_HH_MM_SS_SSS;
import static io.flatf.common.datetime.pattern.impl.DateTimePattern.YYYYMMDD_L_HH_MM_SS_SSSSSS;
import static io.flatf.common.datetime.pattern.impl.DateTimePattern.YYYY_MM_DD_HHMMSS;
import static io.flatf.common.datetime.pattern.impl.DateTimePattern.YYYY_MM_DD_HHMMSSSSS;
import static io.flatf.common.datetime.pattern.impl.DateTimePattern.YYYY_MM_DD_HHMMSSSSSSSS;
import static io.flatf.common.datetime.pattern.impl.DateTimePattern.YYYY_MM_DD_HH_MM_SS;
import static io.flatf.common.datetime.pattern.impl.DateTimePattern.YYYY_MM_DD_HH_MM_SS_SSS;
import static io.flatf.common.datetime.pattern.impl.DateTimePattern.YYYY_MM_DD_HH_MM_SS_SSSSSS;
import static io.flatf.common.datetime.pattern.impl.DateTimePattern.YY_MM_DD_HHMMSS;
import static io.flatf.common.datetime.pattern.impl.DateTimePattern.YY_MM_DD_HHMMSSSSS;
import static io.flatf.common.datetime.pattern.impl.DateTimePattern.YY_MM_DD_HHMMSSSSSSSS;
import static io.flatf.common.datetime.pattern.impl.DateTimePattern.YY_MM_DD_HH_MM_SS;
import static io.flatf.common.datetime.pattern.impl.DateTimePattern.YY_MM_DD_HH_MM_SS_SSS;
import static io.flatf.common.datetime.pattern.impl.DateTimePattern.YY_MM_DD_HH_MM_SS_SSSSSS;

/**
 * 常用日期时间格式(带时区)
 */
public enum ZonedDateTimePattern implements TemporalPattern<ZonedDateTime> {

    /**
     * example: 18031413+0900
     */
    YYMMDDHH_Z(YYMMDDHH.getPattern() + "Z"),

    /**
     * example: 1803141314+0900
     */
    YYMMDDHHMM_Z(YYMMDDHHMM.getPattern() + "Z"),

    /**
     * example: 180314131423+0900
     */
    YYMMDDHHMMSS_Z(YYMMDDHHMMSS.getPattern() + "Z"),

    /**
     * example: 180314131423678+0900
     */
    YYMMDDHHMMSSSSS_Z(YYMMDDHHMMSSSSS.getPattern() + "Z"),

    /**
     * example: 2018031413+0900
     */
    YYYYMMDDHH_Z(YYYYMMDDHH.getPattern() + "Z"),

    /**
     * example: 201803141314+0900
     */
    YYYYMMDDHHMM_Z(YYYYMMDDHHMM.getPattern() + "Z"),

    /**
     * example: 20180314131423+0900
     */
    YYYYMMDDHHMMSS_Z(YYYYMMDDHHMMSS.getPattern() + "Z"),

    /**
     * example: 20180314131423678+0900
     */
    YYYYMMDDHHMMSSSSS_Z(YYYYMMDDHHMMSSSSS.getPattern() + "Z"),

    /*
     * =============================================================================================
     */

    /**
     * example: 180314 131423+0900
     */
    YYMMDD_HHMMSS_Z(YYMMDD_HHMMSS.getPattern() + "Z"),

    /**
     * example: 180314 131423678+0900
     */
    YYMMDD_HHMMSSSSS_Z(YYMMDD_HHMMSSSSS.getPattern() + "Z"),

    /**
     * example: 180314 131423678789+0900
     */
    YYMMDD_HHMMSSSSSSSS_Z(YYMMDD_HHMMSSSSSSSS.getPattern() + "Z"),

    /**
     * example: 180314-131423+0900
     */
    YYMMDD_L_HHMMSS_Z(YYMMDD_L_HHMMSS.getPattern() + "Z"),

    /**
     * example: 180314-131423678+0900
     */
    YYMMDD_L_HHMMSSSSS_Z(YYMMDD_L_HHMMSSSSS.getPattern() + "Z"),

    /**
     * example: 180314-131423678789+0900
     */
    YYMMDD_L_HHMMSSSSSSSS_Z(YYMMDD_L_HHMMSSSSSSSS.getPattern() + "Z"),

    /**
     * example: 20180314 131423+0900
     */
    YYYYMMDD_HHMMSS_Z(YYYYMMDD_HHMMSS.getPattern() + "Z"),

    /**
     * example: 20180314 131423678+0900
     */
    YYYYMMDD_HHMMSSSSS_Z(YYYYMMDD_HHMMSSSSS.getPattern() + "Z"),

    /**
     * example: 20180314 131423678789+0900
     */
    YYYYMMDD_HHMMSSSSSSSS_Z(YYYYMMDD_HHMMSSSSSSSS.getPattern() + "Z"),

    /**
     * example: 20180314-131423+0900
     */
    YYYYMMDD_L_HHMMSS_Z(YYYYMMDD_L_HHMMSS.getPattern() + "Z"),

    /**
     * example: 20180314-131423678+0900
     */
    YYYYMMDD_L_HHMMSSSSS_Z(YYYYMMDD_L_HHMMSSSSS.getPattern() + "Z"),

    /**
     * example: 20180314-131423678789+0900
     */
    YYYYMMDD_L_HHMMSSSSSSSS_Z(YYYYMMDD_L_HHMMSSSSSSSS.getPattern() + "Z"),

    /*
     * =============================================================================================
     */

    /**
     * example: 180314 13:14:23+0900
     */
    YYMMDD_HH_MM_SS_Z(YYMMDD_HH_MM_SS.getPattern() + "Z"),

    /**
     * example: 180314 13:14:23.678+0900
     */
    YYMMDD_HH_MM_SS_SSS_Z(YYMMDD_HH_MM_SS_SSS.getPattern() + "Z"),

    /**
     * example: 180314T13:14:23.678789+0900
     */
    YYMMDD_HH_MM_SS_SSSSSS_Z(YYMMDD_HH_MM_SS_SSSSSS.getPattern() + "Z"),

    /**
     * example: 180314-13:14:23+0900
     */
    YYMMDD_L_HH_MM_SS_Z(YYMMDD_L_HH_MM_SS.getPattern() + "Z"),

    /**
     * example: 180314-13:14:23.678+0900
     */
    YYMMDD_L_HH_MM_SS_SSS_Z(YYMMDD_L_HH_MM_SS_SSS.getPattern() + "Z"),

    /**
     * example: 180314-13:14:23.678789+0900
     */
    YYMMDD_L_HH_MM_SS_SSSSSS_Z(YYMMDD_L_HH_MM_SS_SSSSSS.getPattern() + "Z"),

    /**
     * example: 20180314 13:14:23+0900
     */
    YYYYMMDD_HH_MM_SS_Z(YYYYMMDD_HH_MM_SS.getPattern() + "Z"),

    /**
     * example: 20180314 13:14:23.678+0900
     */
    YYYYMMDD_HH_MM_SS_SSS_Z(YYYYMMDD_HH_MM_SS_SSS.getPattern() + "Z"),

    /**
     * example: 20180314 13:14:23.678789+0900
     */
    YYYYMMDD_HH_MM_SS_SSSSSS_Z(YYYYMMDD_HH_MM_SS_SSSSSS.getPattern() + "Z"),

    /**
     * example: 20180314-13:14:23+0900
     */
    YYYYMMDD_L_HH_MM_SS_Z(YYYYMMDD_L_HH_MM_SS.getPattern() + "Z"),

    /**
     * example: 20180314-13:14:23.678+0900
     */
    YYYYMMDD_L_HH_MM_SS_SSS_Z(YYYYMMDD_L_HH_MM_SS_SSS.getPattern() + "Z"),

    /**
     * example: 20180314-13:14:23.678789+0900
     */
    YYYYMMDD_L_HH_MM_SS_SSSSSS_Z(YYYYMMDD_L_HH_MM_SS_SSSSSS.getPattern() + "Z"),

    /*
     * =============================================================================================
     */

    /**
     * example: 18-03-14 131423+0900
     */
    YY_MM_DD_HHMMSS_Z(YY_MM_DD_HHMMSS.getPattern() + "Z"),

    /**
     * example: 18-03-14 131423678+0900
     */
    YY_MM_DD_HHMMSSSSS_Z(YY_MM_DD_HHMMSSSSS.getPattern() + "Z"),

    /**
     * example: 18-03-14 131423678789+0900
     */
    YY_MM_DD_HHMMSSSSSSSS_Z(YY_MM_DD_HHMMSSSSSSSS.getPattern() + "Z"),

    /**
     * example: 2018-03-14 131423+0900
     */
    YYYY_MM_DD_HHMMSS_Z(YYYY_MM_DD_HHMMSS.getPattern() + "Z"),

    /**
     * example: 2018-03-14 131423678+0900
     */
    YYYY_MM_DD_HHMMSSSSS_Z(YYYY_MM_DD_HHMMSSSSS.getPattern() + "Z"),

    /**
     * example: 2018-03-14 131423678789+0900
     */
    YYYY_MM_DD_HHMMSSSSSSSS_Z(YYYY_MM_DD_HHMMSSSSSSSS.getPattern() + "Z"),

    /*
     * =============================================================================================
     */

    /**
     * example: 18-03-14 13:14:23+0900
     */
    YY_MM_DD_HH_MM_SS_Z(YY_MM_DD_HH_MM_SS.getPattern() + "Z"),

    /**
     * example: 18-03-14 13:14:23.678+0900
     */
    YY_MM_DD_HH_MM_SS_SSS_Z(YY_MM_DD_HH_MM_SS_SSS.getPattern() + "Z"),

    /**
     * example: 18-03-14 13:14:23.678789+0900
     */
    YY_MM_DD_HH_MM_SS_SSSSSS_Z(YY_MM_DD_HH_MM_SS_SSSSSS.getPattern() + "Z"),

    /**
     * example: 2018-03-14 13:14:23+0900
     */
    YYYY_MM_DD_HH_MM_SS_Z(YYYY_MM_DD_HH_MM_SS.getPattern() + "Z"),

    /**
     * example: 2018-03-14 13:14:23.678+0900
     */
    YYYY_MM_DD_HH_MM_SS_SSS_Z(YYYY_MM_DD_HH_MM_SS_SSS.getPattern() + "Z"),

    /**
     * example: 2018-03-14 13:14:23.678789+0900
     */
    YYYY_MM_DD_HH_MM_SS_SSSSSS_Z(YYYY_MM_DD_HH_MM_SS_SSSSSS.getPattern() + "Z"),


    ;

    private final String pattern;

    private final DateTimeFormatter formatter;

    ZonedDateTimePattern(String pattern) {
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
        return fmt(ZonedDateTime.now(zoneId));
    }

    /**
     * @param temporalObj T extends Temporal
     * @return String
     */
    @Override
    public String fmt(ZonedDateTime temporalObj) {
        return formatter.format(temporalObj);
    }

    /**
     * @param text String
     * @return T extends Temporal
     */
    @Override
    public ZonedDateTime parse(String text) {
        return ZonedDateTime.parse(text, formatter);
    }

    /**
     * @param text String
     * @return ZonedDateTime
     * @throws DateTimeParseException e
     */
    public static ZonedDateTime tryParse(String text) throws DateTimeParseException {
        for (ZonedDateTimePattern pattern : ZonedDateTimePattern.values()) {
            try {
                return ZonedDateTime.parse(text, pattern.formatter);
            } catch (DateTimeParseException ignored) {
            }
        }
        throw new DateTimeParseException("No matching pattern", text, 0);
    }

}
