package io.flatf.common.sequence;

import io.flatf.common.util.BitFormatter;

import javax.annotation.concurrent.ThreadSafe;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;

import static java.lang.System.currentTimeMillis;

/**
 * @author yellow013
 */
@ThreadSafe
public final class EpochCycle {

    private final long cycleMillis;

    /**
     * @param cycle Duration
     */
    public EpochCycle(Duration cycle) {
        this(cycle.toMillis());
    }

    /**
     * @param cycleMillis long
     */
    public EpochCycle(long cycleMillis) {
        this.cycleMillis = Math.max(cycleMillis, 1L);
    }

    /**
     * @return long
     */
    public long toCycle() {
        return toCycle(currentTimeMillis());
    }

    /**
     * @param epochMillis long
     * @return long
     */
    public long toCycle(long epochMillis) {
        return epochMillis < 0 ? 0 : (epochMillis / cycleMillis);
    }

    /**
     * @param cycle long
     * @return long
     */
    public long toEpochMillis(long cycle) {
        return cycle * cycleMillis;
    }

    public static void main(String[] args) {

        long l = -1L >>> 16;

        System.out.println(BitFormatter.longBinaryFormat(l));

        System.out.println(ZonedDateTime.ofInstant(Instant.ofEpochMilli(l), ZoneOffset.UTC));

        System.out.println(BitFormatter
                .longBinaryFormat(ZonedDateTime.of(LocalDate.of(3000, 1, 1),
                        LocalTime.MIN, ZoneOffset.UTC).toEpochSecond() * 1000));

    }

}
