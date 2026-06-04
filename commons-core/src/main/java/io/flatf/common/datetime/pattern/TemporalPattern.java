package io.flatf.common.datetime.pattern;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.Temporal;

import static io.flatf.common.constant.TimeZoneConst.SYS_DEFAULT;
import static io.flatf.common.constant.TimeZoneConst.UTC;

public interface TemporalPattern<T extends Temporal> {

    /**
     * @return the pattern string
     */
    String getPattern();

    /**
     * @return the DateTimeFormatter instance
     */
    DateTimeFormatter getFormatter();

    /**
     * @return the new <b> [java.time.format.DateTimeFormatter] </b> instance
     */
    default DateTimeFormatter newFormatter() {
        return DateTimeFormatter.ofPattern(getPattern());
    }

    /**
     * @return the new <b> [java.text.DateFormat] </b> instance
     */
    default DateFormat newDateFormat() {
        return new SimpleDateFormat(getPattern());
    }

    /**
     * @return String
     */
    default String utc() {
        return now(UTC);
    }

    /**
     * @return String
     */
    default String now() {
        return now(SYS_DEFAULT);
    }

    /**
     * @return String
     */
    String now(ZoneId zoneId);

    /**
     * @param temporalObj T extends Temporal
     * @return String
     */
    String fmt(T temporalObj);

    /**
     * @param text String
     * @return T extends Temporal
     */
    T parse(String text);

}
