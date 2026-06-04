package io.flatf.common.epoch;

import javax.annotation.Nonnull;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;

import static io.flatf.common.constant.TimeConst.MILLIS_PER_DAY;
import static io.flatf.common.constant.TimeConst.MILLIS_PER_HOUR;
import static io.flatf.common.constant.TimeConst.MILLIS_PER_MINUTE;
import static io.flatf.common.constant.TimeConst.MILLIS_PER_SECONDS;
import static io.flatf.common.constant.TimeConst.NANOS_PER_MILLIS;
import static io.flatf.common.constant.TimeConst.SECONDS_PER_HOUR;
import static io.flatf.common.constant.TimeConst.SECONDS_PER_MINUTE;
import static io.flatf.common.constant.TimeZoneConst.SYS_DEFAULT;
import static io.flatf.common.constant.TimeZoneConst.UTC;
import static io.flatf.common.lang.Validator.nonNull;
import static java.lang.System.currentTimeMillis;
import static java.time.Instant.EPOCH;

/**
 * <b>Epoch </b>获取工具
 *
 * @author yellow013
 */
public final class EpochUtil {

    /**
     * EpochTime Zero Point : UTC 1970-01-01 00:00:00.0000
     */
    public static final ZonedDateTime EPOCH_ZERO = ZonedDateTime.ofInstant(EPOCH, UTC);

    /**
     * @param unit EpochUnit
     * @return long
     */
    public static long getEpochTime(@Nonnull EpochUnit unit) {
        nonNull(unit, "unit");
        return switch (unit) {
            case SECOND -> getEpochSeconds();
            case MILLIS -> getEpochMillis();
            case MICROS -> HighResolutionEpoch.micros();
            case NANOS -> HighResolutionEpoch.nanos();
        };
    }


    /**
     * 获取 Epoch<b> 毫秒 </b>数
     *
     * @return long
     */
    public static long getEpochMillis() {
        return currentTimeMillis();
    }

    /**
     * @param datetime ZonedDateTime
     * @return long
     */
    public static long getEpochMillis(@Nonnull ZonedDateTime datetime) {
        return datetime.toEpochSecond() * MILLIS_PER_SECONDS
                + datetime.getNano() / NANOS_PER_MILLIS;
    }

    /**
     * @param datetime LocalDateTime
     * @return long
     */
    public static long getEpochMillis(@Nonnull LocalDateTime datetime) {
        return getEpochMillis(datetime, SYS_DEFAULT);
    }

    /**
     * @param datetime LocalDateTime
     * @param offset   ZoneOffset
     * @return long
     */
    public static long getEpochMillis(@Nonnull LocalDateTime datetime,
                                      @Nonnull ZoneOffset offset) {
        return datetime.toLocalDate().toEpochDay() * MILLIS_PER_DAY
                + (long) datetime.toLocalTime().toSecondOfDay() * MILLIS_PER_SECONDS
                + datetime.getNano() / NANOS_PER_MILLIS
                - (long) offset.getTotalSeconds() * MILLIS_PER_SECONDS;
    }

    /**
     * @return long
     */
    public static long getEpochSeconds() {
        return currentTimeMillis() / MILLIS_PER_SECONDS;
    }

    /**
     * @param datetime LocalDateTime
     * @return long
     */
    public static long getEpochSeconds(@Nonnull LocalDateTime datetime) {
        return datetime.toEpochSecond(SYS_DEFAULT);
    }

    /**
     * @param datetime LocalDateTime
     * @param offset   ZoneOffset
     * @return long
     */
    public static long getEpochSeconds(@Nonnull LocalDateTime datetime,
                                       @Nonnull ZoneOffset offset) {
        return datetime.toEpochSecond(offset);
    }

    /**
     * @param datetime ZonedDateTime
     * @return long
     */
    public static long getEpochSeconds(@Nonnull ZonedDateTime datetime) {
        return datetime.toEpochSecond();
    }

    /**
     * @return long
     */
    public static long getEpochMinutes() {
        return currentTimeMillis() / MILLIS_PER_MINUTE;
    }

    /**
     * @param datetime LocalDateTime
     * @return long
     */
    public static long getEpochMinutes(@Nonnull LocalDateTime datetime) {
        return getEpochMinutes(datetime, SYS_DEFAULT);
    }

    /**
     * @param datetime LocalDateTime
     * @param offset   ZoneOffset
     * @return long
     */
    public static long getEpochMinutes(@Nonnull LocalDateTime datetime,
                                       @Nonnull ZoneOffset offset) {
        return datetime.toEpochSecond(offset) / SECONDS_PER_MINUTE;
    }

    /**
     * @param datetime ZonedDateTime
     * @return long
     */
    public static long getEpochMinutes(@Nonnull ZonedDateTime datetime) {
        return datetime.toEpochSecond() / SECONDS_PER_MINUTE;
    }

    /**
     * @return long
     */
    public static long getEpochHours() {
        return currentTimeMillis() / MILLIS_PER_HOUR;
    }

    /**
     * @param datetime LocalDateTime
     * @return long
     */
    public static long getEpochHours(@Nonnull LocalDateTime datetime) {
        return datetime.toEpochSecond(SYS_DEFAULT) / SECONDS_PER_HOUR;
    }

    /**
     * @param datetime LocalDateTime
     * @param offset   ZoneOffset
     * @return long
     */
    public static long getEpochHours(@Nonnull LocalDateTime datetime,
                                     @Nonnull ZoneOffset offset) {
        return datetime.toEpochSecond(offset) / SECONDS_PER_HOUR;
    }

    /**
     * @param datetime ZonedDateTime
     * @return long
     */
    public static long getEpochHours(@Nonnull ZonedDateTime datetime) {
        return datetime.toEpochSecond() / SECONDS_PER_HOUR;
    }

    /**
     * @return long
     */
    public static long getEpochDays() {
        return currentTimeMillis() / MILLIS_PER_DAY;
    }

    /**
     * @param date LocalDate
     * @return long
     */
    public static long getEpochDays(@Nonnull LocalDate date) {
        return date.toEpochDay();
    }

    /**
     * @param dateTime LocalDateTime
     * @return long
     */
    public static long getEpochDays(@Nonnull LocalDateTime dateTime) {
        return dateTime.toLocalDate().toEpochDay();
    }

    /**
     * @param datetime ZonedDateTime
     * @return long
     */
    public static long getEpochDays(@Nonnull ZonedDateTime datetime) {
        return datetime.toLocalDate().toEpochDay();
    }

    /**
     * @param millis long
     * @return long
     */
    public static ZonedDateTime ofEpochMillis(long millis) {
        return ofEpochMillis(millis, SYS_DEFAULT);
    }

    /**
     * @param millis long
     * @param zoneId ZoneId
     * @return long
     */
    public static ZonedDateTime ofEpochMillis(long millis, @Nonnull ZoneId zoneId) {
        return ZonedDateTime.ofInstant(Instant.ofEpochMilli(millis), zoneId);
    }

    /**
     * @param seconds long
     * @return long
     */
    public static ZonedDateTime ofEpochSeconds(long seconds) {
        return ofEpochSeconds(seconds, SYS_DEFAULT);
    }

    /**
     * @param seconds long
     * @param zoneId  ZoneId
     * @return long
     */
    public static ZonedDateTime ofEpochSeconds(long seconds, @Nonnull ZoneId zoneId) {
        return ZonedDateTime.ofInstant(Instant.ofEpochSecond(seconds), zoneId);
    }


    public static void main(String[] args) {
        long[] mss = new long[50];
        long[] nss = new long[50];
        long ms = System.currentTimeMillis();
        for (int i = 0; i < nss.length; i++) {
            mss[i] = HighResolutionEpoch.micros();
            nss[i] = HighResolutionEpoch.nanos();
        }
        System.out.println(ms);
        for (int i = 0; i < nss.length; i++) {
            System.out.println(mss[i]);
            System.out.println(nss[i]);
        }
    }

}
