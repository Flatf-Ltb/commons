package io.flatf.common.datetime;

import io.flatf.common.constant.TimeZoneConst;
import io.flatf.common.epoch.EpochUtil;
import io.flatf.common.epoch.EpochUnit;
import io.flatf.common.epoch.HighResolutionEpoch;
import io.flatf.common.serialization.specific.JsonSerializable;

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
                        floorMod(epoch, MICROS_PER_SECONDS));
                case NANOS -> this.instant = ofEpochSecond(floorDiv(epoch, NANOS_PER_SECOND),
                        floorMod(epoch, NANOS_PER_SECOND));
                default -> throw new IllegalStateException("[" + unit + "] is illegal");
            }
        }
    }

    @Override
    public int compareTo(Timestamp o) {
        return Long.compare(epoch, o.epoch);
    }

    private static final String epochField = "{\"epoch\" : ";
    private static final String epochUnitField = ", \"epochUnit\" : ";
    private static final String instantField = ", \"instant\" : ";
    private static final String end = "}";

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder(90);
        builder.append(epochField);
        builder.append(epoch);
        builder.append(epochUnitField);
        builder.append(unit);
        if (instant != null) {
            builder.append(instantField);
            builder.append(instant);
        }
        builder.append(end);
        return builder.toString();
    }

    @Nonnull
    @Override
    public String toJson() {
        return toString();
    }

    public static void main(String[] args) {

        System.out.println(TimeZoneConst.CST);

        Timestamp timestamp = Timestamp.nowWithMillis();
        System.out.println(timestamp);

        for (int i = 0; i < 100000; i++) {
            EpochUtil.getEpochMillis();
            Timestamp.nowWithMillis();
            Instant.now();
            i++;
            i--;
        }

        for (int i = 0; i < 10000; i++) {
            long l0_0 = System.nanoTime();
            // EpochTime.milliseconds();
            // EpochTimestamp.now();
            Instant.now();
            long l0_1 = System.nanoTime();
            long l0 = l0_1 - l0_0;
            System.out.println(l0);
        }

        long l1_0 = System.nanoTime();
        Timestamp.nowWithMillis();
        long l1_1 = System.nanoTime();

        long l2_0 = System.nanoTime();
        Instant.now();
        long l2_1 = System.nanoTime();

        long l1 = l1_1 - l1_0;
        long l2 = l2_1 - l2_0;

        System.out.println(l1);
        System.out.println(l2);

        Timestamp now = Timestamp.nowWithMillis();

        System.out.println(now.getEpoch());
        System.out.println(now.getInstant().getEpochSecond() * 1000000 + now.getInstant().getNano() / 1000);
        System.out.println(now.getInstant());
        System.out.println(now);

        System.out.println(Timestamp.nowWithMillis());
        System.out.println(Timestamp.withEpochMillis(47237547328L).getDateTimeWith(TimeZoneConst.UTC));
        System.out.println(Timestamp.withDateTime(LocalDateTime.now(), TimeZoneConst.CST));

    }

}
