package io.flatf.common.datetime;

import io.flatf.common.epoch.EpochUtil;
import io.flatf.common.epoch.EpochUnit;
import io.flatf.common.epoch.HighResolutionEpoch;
import io.flatf.infra.serialization.specific.JsonSerializable;

import javax.annotation.Nonnull;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

import static io.flatf.common.constant.TimeConst.MICROS_PER_SECONDS;
import static io.flatf.common.constant.TimeConst.NANOS_PER_SECOND;
import static io.flatf.common.constant.TimeZoneConst.SYS_DEFAULT;
import static io.flatf.common.epoch.EpochUnit.MICROS;
import static io.flatf.common.epoch.EpochUnit.MILLIS;
import static io.flatf.common.epoch.EpochUnit.NANOS;
import static io.flatf.common.epoch.EpochUnit.SECOND;
import static java.lang.Math.floorDiv;
import static java.lang.Math.floorMod;
import static java.lang.System.currentTimeMillis;
import static java.time.Instant.ofEpochMilli;
import static java.time.Instant.ofEpochSecond;
import static java.time.ZonedDateTime.ofInstant;

/**
 * @author yellow013
 */
public final class Timestamp implements Comparable<Timestamp>, JsonSerializable {

    /**
     * Epoch
     */
    private final long epoch;

    /**
     * EpochUnit
     */
    private final EpochUnit unit;

    /**
     * java.time.Instant
     */
    private Instant instant;

    /**
     * @param epoch long
     * @param unit  EpochUnit
     */
    private Timestamp(long epoch, @Nonnull EpochUnit unit) {
        this.epoch = epoch;
        this.unit = unit;
    }

    public static Timestamp nowWithSecond() {
        return new Timestamp(EpochUtil.getEpochSeconds(), SECOND);
    }

    public static Timestamp nowWithMillis() {
        return new Timestamp(currentTimeMillis(), MILLIS);
    }

    public static Timestamp nowWithMicros() {
        return new Timestamp(HighResolutionEpoch.micros(), MICROS);
    }

    public static Timestamp nowWithNanos() {
        return new Timestamp(HighResolutionEpoch.nanos(), NANOS);
    }

    public static Timestamp withEpochSecond(long epochSecond) {
        return new Timestamp(epochSecond, SECOND);
    }

    public static Timestamp withEpochMillis(long epochMillis) {
        return new Timestamp(epochMillis, MILLIS);
    }

    public static Timestamp withEpochMicros(long epochMicros) {
        return new Timestamp(epochMicros, MICROS);
    }

    public static Timestamp withEpochNanos(long epochNanos) {
        return new Timestamp(epochNanos, NANOS);
    }

    public static Timestamp withDateTime(LocalDate date, LocalTime time) {
        return withDateTime(ZonedDateTime.of(date, time, SYS_DEFAULT));
    }

    public static Timestamp withDateTime(LocalDate date, LocalTime time, ZoneId zoneId) {
        return withDateTime(ZonedDateTime.of(date, time, zoneId));
    }

    public static Timestamp withDateTime(LocalDateTime datetime) {
        return withDateTime(ZonedDateTime.of(datetime, SYS_DEFAULT));
    }

    public static Timestamp withDateTime(LocalDateTime datetime, ZoneId zoneId) {
        return withDateTime(ZonedDateTime.of(datetime, zoneId));
    }

    public static Timestamp withDateTime(ZonedDateTime datetime) {
        return new Timestamp(datetime.toInstant().toEpochMilli(), MILLIS);
    }

    public long getEpoch() {
        return epoch;
    }

    public EpochUnit getUnit() {
        return unit;
    }

    public Instant getInstant() {
        newInstantOfEpochMillis();
        return instant;
    }

    /**
     * 根据指定时区获取时间
     *
     * @param zoneId ZoneId
     * @return ZonedDateTime
     */
    public ZonedDateTime getDateTimeWith(ZoneId zoneId) {
        newInstantOfEpochMillis();
        return ofInstant(instant, zoneId);
    }

    /**
     * 根据Epoch毫秒数生成Instant
     */
    private void newInstantOfEpochMillis() {
        if (instant == null) {
            switch (unit) {
                case SECOND -> this.instant = ofEpochSecond(epoch);
                case MILLIS -> this.instant = ofEpochMilli(epoch);
                case MICROS -> this.instant = ofEpochSecond(floorDiv(epoch, MICROS_PER_SECONDS),
                        floorMod(epoch, MICROS_PER_SECONDS) * 1000L);
                case NANOS -> this.instant = ofEpochSecond(floorDiv(epoch, NANOS_PER_SECOND),
                        floorMod(epoch, NANOS_PER_SECOND));
                default -> throw new IllegalStateException("[" + unit + "] is illegal");
            }
        }
    }

    @Override
    public int compareTo(Timestamp o) {
        return getInstant().compareTo(o.getInstant());
    }

    private static final String epochField = "{\"epoch\" : ";
    private static final String epochUnitField = ", \"epochUnit\" : ";
    private static final String instantField = ", \"instant\" : ";
    private static final String end = "}";

    @Override
    public String toString() {
        return toJson();
    }

    @Nonnull
    @Override
    public String toJson() {
        StringBuilder builder = new StringBuilder(100);
        builder.append(epochField);
        builder.append(epoch);
        builder.append(epochUnitField);
        builder.append('"');
        builder.append(unit);
        builder.append('"');
        builder.append(instantField);
        builder.append('"');
        builder.append(getInstant());
        builder.append('"');
        builder.append(end);
        return builder.toString();
    }

}
