package io.flatf.common.sequence;

import javax.annotation.concurrent.ThreadSafe;

import static io.flatf.common.constant.TimeConst.NANOS_PER_MICROS;
import static io.flatf.common.constant.TimeConst.NANOS_PER_MILLIS;
import static java.lang.System.nanoTime;

/**
 * Use the System.nanoTime() native function
 *
 * @author yellow013
 */
@ThreadSafe
public final class SysNanoSequence {

    /**
     * @return System.nanoTime() / NANOS_PER_MILLIS
     */
    public static long getMillis() {
        return nanoTime() / NANOS_PER_MILLIS;
    }

    /**
     * @return System.nanoTime() / NANOS_PER_MICROS
     */
    public static long getMicros() {
        return nanoTime() / NANOS_PER_MICROS;
    }

    /**
     * @return System.nanoTime()
     */
    public static long getNanos() {
        return nanoTime();
    }

}
