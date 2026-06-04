package io.flatf.common.datetime;

import io.flatf.common.datetime.pattern.TemporalPattern;
import io.flatf.common.datetime.pattern.impl.DateTimePattern;
import io.flatf.common.datetime.pattern.impl.TimePattern;
import io.flatf.common.datetime.pattern.spec.SpecifiedDatePattern;
import io.flatf.common.util.StringSupport;

import javax.annotation.Nonnull;
import javax.annotation.concurrent.ThreadSafe;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeParseException;
import java.util.Date;

import static io.flatf.common.constant.TimeZoneConst.SYS_DEFAULT;

@ThreadSafe
public final class DateTimeUtil {

    /**
     * 返回 primitive int 表示的 yyyyMMdd
     *
     * @return int
     */
    public static int date() {
        return date(LocalDate.now());
    }

    /**
     * 返回 primitive int 表示的 yyyyMMdd
     *
     * @return int
     */
    public static int date(ZoneId zoneId) {
        return date(LocalDate.now(zoneId));
    }

    /**
     * 根据指定 LocalDateTime 返回 primitive int 表示的 yyyyMMdd
     *
     * @param datetime LocalDateTime
     * @return int
     */
    public static int date(@Nonnull LocalDateTime datetime) {
        return date(datetime.toLocalDate());
    }

    /**
     * 根据指定 ZonedDateTime 返回 primitive int 表示的 yyyyMMdd
     *
     * @param datetime ZonedDateTime
     * @return int
     */
    public static int date(@Nonnull ZonedDateTime datetime) {
        return date(datetime.toLocalDate());
    }

    /**
     * 根据指定 LocalDate 返回 primitive int 表示的 yyyyMMdd
     *
     * @param date LocalDate
     * @return int
     */
    public static int date(@Nonnull LocalDate date) {
        return date.getYear() * 10000
                + date.getMonth().getValue() * 100
                + date.getDayOfMonth();
    }

    /**
     * 根据指定 Date 返回 primitive int 表示的 yyyyMMdd
     *
     * @param date Date
     * @return int
     */
    public static int date(@Nonnull Date date) {
        return date(toLocalDate(date));
    }

    /**
     * 返回 primitive int 表示的 yyyyMM
     *
     * @return int
     */
    public static int dateOfMonth() {
        return dateOfMonth(LocalDate.now());
    }

    /**
     * 根据指定 LocalDateTime 返回 primitive int 表示的 yyyyMM
     *
     * @param datetime LocalDateTime
     * @return int
     */
    public static int dateOfMonth(@Nonnull LocalDateTime datetime) {
        return dateOfMonth(datetime.toLocalDate());
    }

    /**
     * 根据指定 ZonedDateTime 返回 primitive int 表示的 yyyyMM
     *
     * @param datetime ZonedDateTime
     * @return int
     */
    public static int dateOfMonth(@Nonnull ZonedDateTime datetime) {
        return dateOfMonth(datetime.toLocalDate());
    }

    /**
     * 根据指定 LocalDate 返回 primitive int 表示的 yyyyMM
     *
     * @param date LocalDate
     * @return int
     */
    public static int dateOfMonth(@Nonnull LocalDate date) {
        return date.getYear() * 100
                + date.getMonth().getValue();
    }

    /**
     * 返回 primitive int 表示的 yyyyXXX
     *
     * @return int
     */
    public static int yearDay() {
        return yearDay(LocalDate.now());
    }

    /**
     * 根据指定 LocalDate 返回 primitive int 表示的 yyyyXXX
     *
     * @param date LocalDate
     * @return int
     */
    public static int yearDay(@Nonnull LocalDate date) {
        return date.getYear() * 1000
                + date.getDayOfYear();
    }

    /**
     * 返回 primitive int 表示的 HH
     *
     * @return int
     */
    public static int timeOfHour() {
        return timeOfHour(LocalTime.now());
    }

    /**
     * 根据指定 LocalTime 返回 primitive int 表示的 HH
     *
     * @param time LocalTime
     * @return int
     */
    public static int timeOfHour(@Nonnull LocalTime time) {
        return time.getHour();
    }

    /**
     * 返回 primitive int 表示的 HHmm
     *
     * @return int
     */
    public static int timeOfMinute() {
        return timeOfMinute(LocalTime.now());
    }

    /**
     * 根据指定 LocalTime 返回 primitive int 表示的 HHmm
     *
     * @param time LocalTime
     * @return int
     */
    public static int timeOfMinute(@Nonnull LocalTime time) {
        return time.getHour() * 100
                + time.getMinute();
    }

    /**
     * 返回 primitive int 表示的 HHmmss
     *
     * @return int
     */
    public static int timeOfSecond() {
        return timeOfSecond(LocalTime.now());
    }

    /**
     * 根据指定 LocalTime 返回 primitive int 表示的 HHmmss
     *
     * @param time LocalTime
     * @return int
     */
    public static int timeOfSecond(@Nonnull LocalTime time) {
        return time.getHour() * 10000
                + time.getMinute() * 100
                + time.getSecond();
    }

    /**
     * 返回 primitive int 表示的 HHmmssSSS
     *
     * @return int
     */
    public static int timeOfMillisecond() {
        return timeOfMillisecond(LocalTime.now());
    }

    /**
     * 根据指定 LocalTime 返回 primitive int 表示的 HHmmssSSS
     *
     * @param time LocalTime
     * @return int
     */
    public static int timeOfMillisecond(@Nonnull LocalTime time) {
        return timeOfSecond(time) * 1000
                + time.getNano() / 1000000;
    }

    /**
     * 返回 primitive long 表示的 HHmmssSSSSSS
     *
     * @return long
     */
    public static long timeOfMicrosecond() {
        return timeOfMicrosecond(LocalTime.now());
    }

    /**
     * 根据指定 LocalTime 返回 primitive long 表示的 HHmmssSSSSSS
     *
     * @param time LocalTime
     * @return long
     */
    public static long timeOfMicrosecond(@Nonnull LocalTime time) {
        return timeOfSecond(time) * 1000000L
                + time.getNano() / 1000;
    }

    /**
     * 返回 primitive long 表示的 HHmmssSSSSSSSSS
     *
     * @return long
     */
    public static long timeOfNanosecond() {
        return timeOfNanosecond(LocalTime.now());
    }

    /**
     * 根据指定 LocalTime 返回 primitive long 表示的 HHmmssSSSSSSSSS
     *
     * @param time LocalTime
     * @return long
     */
    public static long timeOfNanosecond(@Nonnull LocalTime time) {
        return timeOfSecond(time) * 1000000000L
                + time.getNano();
    }

    /**
     * 返回 primitive long 表示的 yyyyMMddHH
     *
     * @return LocalDateTime
     */
    public static long datetimeOfHour() {
        return datetimeOfHour(LocalDateTime.now());
    }

    /**
     * 根据指定 LocalDateTime 返回 primitive long 表示的 yyyyMMddHH
     *
     * @param datetime LocalDateTime
     * @return long
     */
    public static long datetimeOfHour(@Nonnull LocalDateTime datetime) {
        return date(datetime.toLocalDate()) * 100L
                + timeOfHour(datetime.toLocalTime());
    }

    /**
     * 返回 primitive long 表示的 yyyyMMddHHmm
     *
     * @return long
     */
    public static long datetimeOfMinute() {
        return datetimeOfMinute(LocalDateTime.now());
    }

    /**
     * 根据指定 LocalDateTime 返回 primitive long 表示的 yyyyMMddHHmm
     *
     * @param datetime LocalDateTime
     * @return long
     */
    public static long datetimeOfMinute(@Nonnull LocalDateTime datetime) {
        return date(datetime.toLocalDate()) * 10000L
                + timeOfMinute(datetime.toLocalTime());
    }

    /**
     * 返回 primitive long 表示的 yyyyMMddHHmmss
     *
     * @return long
     */
    public static long datetimeOfSecond() {
        return datetimeOfSecond(LocalDateTime.now());
    }

    /**
     * 根据指定 LocalDateTime 返回 primitive long 表示的 yyyyMMddHHmmss
     *
     * @param datetime LocalDateTime
     * @return long
     */
    public static long datetimeOfSecond(@Nonnull LocalDateTime datetime) {
        return date(datetime.toLocalDate()) * 1000000L
                + timeOfSecond(datetime.toLocalTime());
    }

    /**
     * 返回 primitive long 表示的 yyyyMMddHHmmssSSS
     *
     * @return long
     */
    public static long datetimeOfMillisecond() {
        return datetimeOfMillisecond(LocalDateTime.now());
    }

    /**
     * 根据指定 LocalDateTime 返回 primitive long 表示的 yyyyMMddHHmmssSSS<br>
     * year 不可超过 922337
     *
     * @param datetime LocalDateTime
     * @return long
     */
    public static long datetimeOfMillisecond(@Nonnull LocalDateTime datetime) {
        return datetimeOfSecond(datetime) * 1000L
                + datetime.toLocalTime().getNano() / 1000000;
    }

    /**
     * primitive int yyyyMMdd 转换为 LocalDate
     *
     * @param date int
     * @return LocalDate
     */
    public static LocalDate toLocalDate(int date) {
        return LocalDate.of(date / 10000, (date % 10000) / 100,
                date % 100);
    }

    /**
     * Date to LocalDate
     *
     * @param date Date
     * @return LocalDate
     */
    public static LocalDate toLocalDate(Date date) {
        return LocalDateTime.ofInstant(date.toInstant(), SYS_DEFAULT).toLocalDate();
    }

    /**
     * primitive int HHmmss 转换为 LocalTime
     *
     * @param time int
     * @return LocalTime
     */
    public static LocalTime toLocalTime(int time) {
        return LocalTime.of(time / 10000000, (time % 10000000) / 100000,
                (time % 100000) / 1000, (time % 1000) * 1000000);
    }

    /**
     * primitive long yyyyMMddHHmmss 转换为 LocalDateTime
     *
     * @param datetime long
     * @return LocalDateTime
     */
    public static LocalDateTime toLocalDateTime(long datetime) {
        return LocalDateTime.of(toLocalDate((int) (datetime / 1000000000)),
                toLocalTime((int) (datetime % 1000000000)));
    }

    /**
     * @param pattern DatePattern
     * @param str     String
     * @return LocalDate
     * @throws IllegalArgumentException ie
     * @throws DateTimeParseException   de
     */
    public static LocalDate toLocalDate(@Nonnull SpecifiedDatePattern pattern,
                                        @Nonnull String str)
            throws IllegalArgumentException, DateTimeParseException {
        checkFormatParam(pattern, str);
        return LocalDate.parse(str, pattern.getFormatter());
    }

    /**
     * @param pattern TimePattern
     * @param str     String
     * @return LocalTime
     * @throws IllegalArgumentException ie
     * @throws DateTimeParseException   de
     */
    public static LocalTime toLocalTime(@Nonnull TimePattern pattern,
                                        @Nonnull String str)
            throws IllegalArgumentException, DateTimeParseException {
        checkFormatParam(pattern, str);
        return LocalTime.parse(str, pattern.getFormatter());
    }

    /**
     * @param pattern  DateTimePattern
     * @param datetime String
     * @return LocalDateTime
     * @throws IllegalArgumentException ie
     * @throws DateTimeParseException   de
     */
    public static LocalDateTime toLocalDateTime(@Nonnull DateTimePattern pattern,
                                                @Nonnull String datetime)
            throws IllegalArgumentException, DateTimeParseException {
        checkFormatParam(pattern, datetime);
        return LocalDateTime.parse(datetime, pattern.getFormatter());
    }

    private static void checkFormatParam(TemporalPattern<?> pattern, String str) {
        if (pattern == null)
            throw new IllegalArgumentException("pattern cannot null");
        if (StringSupport.isNullOrEmpty(str))
            throw new IllegalArgumentException("str cannot null or empty.");
        if (str.length() != pattern.getPattern().length())
            throw new IllegalArgumentException("str and pattern length no match.");
    }

    /**
     * @param date Date
     * @return LocalDateTime
     */
    public static LocalDateTime toLocalDateTime(@Nonnull Date date) {
        return LocalDateTime.ofInstant(date.toInstant(), SYS_DEFAULT);
    }

    /**
     * @param date   Date
     * @param zoneId ZoneId
     * @return LocalDateTime
     */
    public static LocalDateTime toLocalDateTime(@Nonnull Date date,
                                                @Nonnull ZoneId zoneId) {
        return LocalDateTime.ofInstant(date.toInstant(), zoneId);
    }

    /**
     * @param pattern DatePattern
     * @return String
     */
    public static String fmtDate(@Nonnull SpecifiedDatePattern pattern) {
        return pattern.getFormatter().format(LocalDate.now());
    }

    /**
     * @param date    DatePattern
     * @param pattern LocalDate
     * @return String
     */
    public static String fmtDate(@Nonnull SpecifiedDatePattern pattern,
                                 @Nonnull LocalDate date) {
        return pattern.getFormatter().format(date);
    }

    /**
     * @param pattern TimePattern
     * @return String
     */
    public static String fmtTime(@Nonnull TimePattern pattern) {
        return pattern.getFormatter().format(LocalTime.now());
    }

    /**
     * @param time    TimePattern
     * @param pattern LocalTime
     * @return String
     */
    public static String fmtTime(@Nonnull TimePattern pattern,
                                 @Nonnull LocalTime time) {
        return pattern.getFormatter().format(time);
    }

    /**
     * @param datetime DateTimePattern
     * @param pattern  LocalDateTime
     * @return String
     */
    public static String fmtDateTime(@Nonnull DateTimePattern pattern,
                                     @Nonnull LocalDateTime datetime) {
        return pattern.getFormatter().format(datetime);
    }

    /**
     * @param pattern DateTimePattern
     * @return String
     */
    public static String fmtDateTime(@Nonnull DateTimePattern pattern) {
        return pattern.getFormatter().format(LocalDateTime.now());
    }

    /**
     * @return LocalDate
     */
    public static LocalDate currentDate() {
        return LocalDate.now();
    }

    /**
     * @return LocalDate
     */
    public static LocalDate previousDate() {
        return previousDate(LocalDate.now());
    }

    /**
     * @param date LocalDate
     * @return LocalDate
     */
    public static LocalDate previousDate(LocalDate date) {
        return date.minusDays(1);
    }

    /**
     * @return LocalDate
     */
    public static LocalDate nextDate() {
        return nextDate(LocalDate.now());
    }

    /**
     * @param date LocalDate
     * @return LocalDate
     */
    public static LocalDate nextDate(LocalDate date) {
        return date.plusDays(1);
    }

    public static void main(String[] args) {

        LocalDateTime dateTime = LocalDateTime.now();
        System.out.println(Integer.MAX_VALUE);
        System.out.println(Long.MAX_VALUE);
        System.out.println(date(dateTime.toLocalDate()));
        System.out.println(timeOfHour(dateTime.toLocalTime()));
        System.out.println(timeOfMinute(dateTime.toLocalTime()));
        System.out.println(timeOfSecond(dateTime.toLocalTime()));
        System.out.println(timeOfMillisecond(dateTime.toLocalTime()));
        System.out.println(timeOfMicrosecond(dateTime.toLocalTime()));
        System.out.println(timeOfNanosecond(dateTime.toLocalTime()));
        System.out.println(datetimeOfHour(dateTime));
        System.out.println(datetimeOfMinute(dateTime));
        System.out.println(datetimeOfSecond(dateTime));
        System.out.println(datetimeOfMillisecond(dateTime));
        System.out.println(toLocalDate(20161223));
        System.out.println(toLocalTime(234554987));
        System.out.println(toLocalDateTime(20161223234554987L));
        System.out.println(yearDay());
        System.out.println(LocalDate.now());
        System.out.println(LocalDate.ofEpochDay(System.currentTimeMillis() / (24 * 60 * 60 * 1000)));
        System.out.println(System.currentTimeMillis() / (24 * 60 * 60 * 1000));

    }

}
