package io.flatf.common.thread;

import io.flatf.common.log4j2.Log4j2LoggerFactory;
import org.slf4j.Logger;

import javax.annotation.Nonnull;
import java.time.Duration;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;

public final class Sleep {

    private static final Logger log = Log4j2LoggerFactory.getLogger(Sleep.class);

    private Sleep() {
    }

    /**
     * Use LockSupport.park()
     */
    public static void park() {
        LockSupport.park();
    }

    /**
     * Use LockSupport.parkNanos(1)
     */
    public static void parkNano() {
        LockSupport.parkNanos(1);
    }

    /**
     * Use LockSupport.parkNanos(long nanos)
     *
     * @param nanos the maximum number of nanoseconds to wait
     */
    public static void parkNanos(long nanos) {
        LockSupport.parkNanos(nanos);
    }

    /**
     * @param millis long
     */
    public static void millisIgnored(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            log.error("Sleep::millisIgnored({}) throw InterruptedException -> {}",
                    millis, e.getMessage(), e);
        }
    }

    /**
     * @param millis long
     * @param nanos  int
     */
    public static void millisIgnored(long millis, int nanos) {
        try {
            Thread.sleep(millis, nanos);
        } catch (InterruptedException e) {
            log.error("Sleep::millisIgnored({}, {}) throw InterruptedException -> {}",
                    millis, nanos, e.getMessage(), e);
        }
    }

    /**
     * @param timeUnit TimeUnit
     * @param time     long
     */
    public static void timeIgnored(@Nonnull TimeUnit timeUnit, long time) {
        try {
            timeUnit.sleep(time);
        } catch (InterruptedException e) {
            log.error("Sleep::timeIgnored({}, {}) throw InterruptedException -> {}",
                    timeUnit, time, e.getMessage(), e);
        }
    }

    /**
     * @param millis long
     * @throws RuntimeInterruptedException e
     */
    public static void millis(long millis) throws RuntimeInterruptedException {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            log.error("Sleep::millis({}) throw InterruptedException -> {}",
                    millis, e.getMessage(), e);
            throw new RuntimeInterruptedException(e);
        }
    }

    /**
     * @param millis long
     * @param nanos  int
     * @throws RuntimeInterruptedException e
     */
    public static void millis(long millis, int nanos) throws RuntimeInterruptedException {
        try {
            Thread.sleep(millis, nanos);
        } catch (InterruptedException e) {
            log.error("Sleep::millis({}, {}) throw InterruptedException -> {}",
                    millis, nanos, e.getMessage(), e);
            throw new RuntimeInterruptedException(e);
        }
    }

    /**
     * @param duration     Duration
     * @throws RuntimeInterruptedException e
     */
    public static void time(@Nonnull Duration duration) throws RuntimeInterruptedException {
        time(TimeUnit.NANOSECONDS, duration.toNanos());
    }

    /**
     * @param timeUnit TimeUnit
     * @param time     long
     * @throws RuntimeInterruptedException e
     */
    public static void time(@Nonnull TimeUnit timeUnit, long time) throws RuntimeInterruptedException {
        try {
            timeUnit.sleep(time);
        } catch (InterruptedException e) {
            log.error("Sleep::time({}, {}) throw InterruptedException -> {}",
                    timeUnit, time, e.getMessage(), e);
            throw new RuntimeInterruptedException(e);
        }
    }


}
