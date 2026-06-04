package io.flatf.common.datetime.pattern;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZonedDateTime;

import static io.flatf.common.datetime.pattern.impl.DatePattern.YYYY_MM_DD;
import static io.flatf.common.datetime.pattern.impl.DateTimePattern.YYYY_MM_DD_HH_MM_SS_SSSSSS;
import static io.flatf.common.datetime.pattern.impl.TimePattern.HH_MM_SS_SSSSSS;
import static io.flatf.common.datetime.pattern.impl.ZonedDateTimePattern.YYYY_MM_DD_HH_MM_SS_SSSSSS_Z;

public final class StandardPattern {

    /**
     * Pattern : yyyy-MM-dd
     *
     * @param date LocalDate
     * @return String
     */
    public static String fmt(LocalDate date) {
        return YYYY_MM_DD.fmt(date);
    }

    /**
     * Pattern : HH:mm:ss.SSSSSS
     *
     * @param time LocalTime
     * @return String
     */
    public static String fmt(LocalTime time) {
        return HH_MM_SS_SSSSSS.fmt(time);
    }

    /**
     * Pattern : yyyy-MM-dd HH:mm:ss.SSSSSS
     *
     * @param datetime LocalDateTime
     * @return String
     */
    public static String fmt(LocalDateTime datetime) {
        return YYYY_MM_DD_HH_MM_SS_SSSSSS.fmt(datetime);
    }

    /**
     * Pattern : yyyy-MM-dd HH:mm:ss.SSSSSSZ
     *
     * @param datetime ZonedDateTime
     * @return String
     */
    public static String fmt(ZonedDateTime datetime) {
        return YYYY_MM_DD_HH_MM_SS_SSSSSS_Z.fmt(datetime);
    }

    public static LocalDate toDate(String text) {
        return YYYY_MM_DD.parse(text);
    }

    public static LocalTime toTime(String text) {
        return HH_MM_SS_SSSSSS.parse(text);
    }

    public static LocalDateTime toDateTime(String text) {
        return YYYY_MM_DD_HH_MM_SS_SSSSSS.parse(text);
    }

    public static ZonedDateTime toZonedDateTime(String text) {
        return YYYY_MM_DD_HH_MM_SS_SSSSSS_Z.parse(text);
    }

}
