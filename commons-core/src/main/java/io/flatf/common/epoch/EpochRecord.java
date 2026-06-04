package io.flatf.common.epoch;

import static io.flatf.common.epoch.EpochUtil.getEpochTime;
import static io.flatf.common.epoch.EpochUnit.MICROS;
import static io.flatf.common.epoch.EpochUnit.MILLIS;
import static io.flatf.common.epoch.EpochUnit.NANOS;
import static io.flatf.common.epoch.EpochUnit.SECOND;

public record EpochRecord(
        long epochTime,
        EpochUnit epochUnit
) {

    public static EpochRecord nowEpochRecord(long epochTime, EpochUnit epochUnit) {
        return new EpochRecord(epochTime, epochUnit);
    }

    public static EpochRecord nowEpochRecord(EpochUnit epochUnit) {
        return new EpochRecord(getEpochTime(epochUnit), epochUnit);
    }

    public static EpochRecord nowEpochSecond() {
        return new EpochRecord(getEpochTime(SECOND), SECOND);
    }

    public static EpochRecord nowEpochMillis() {
        return new EpochRecord(getEpochTime(MILLIS), MILLIS);
    }

    public static EpochRecord nowEpochMicros() {
        return new EpochRecord(getEpochTime(MICROS), MICROS);
    }

    public static EpochRecord nowEpochNanos() {
        return new EpochRecord(getEpochTime(NANOS), NANOS);
    }

}
