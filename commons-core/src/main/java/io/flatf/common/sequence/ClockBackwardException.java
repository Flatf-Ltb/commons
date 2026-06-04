package io.flatf.common.sequence;

import java.io.Serial;

/**
 * 时钟回退抛出此异常
 *
 * @author yellow013
 */
public final class ClockBackwardException extends IllegalStateException {

    @Serial
    private static final long serialVersionUID = -5012855755917563428L;

    private final long lastEpochMillis;

    private final long currentEpochMillis;

    ClockBackwardException(long lastEpochMillis, long currentEpochMillis) {
        super("The clock moved backwards, Refusing to generate seq for " + (lastEpochMillis - currentEpochMillis) + "millis");
        this.currentEpochMillis = currentEpochMillis;
        this.lastEpochMillis = lastEpochMillis;
    }

    public long getLastEpochMillis() {
        return lastEpochMillis;
    }

    public long getCurrentEpochMillis() {
        return currentEpochMillis;
    }

}
