package io.flatf.common.constant;

public interface TimeConst {

    /* ******************* Hours ********************/
    /**
     * Hours per day.
     */
    int HOURS_PER_DAY = 24;


    /* ******************* Minutes ********************/
    /**
     * Minutes per hour.
     */
    int MINUTES_PER_HOUR = 60;

    /**
     * Minutes per day.
     */
    int MINUTES_PER_DAY = MINUTES_PER_HOUR * HOURS_PER_DAY;


    /* ******************* Seconds ********************/
    /**
     * Seconds per minute.
     */
    int SECONDS_PER_MINUTE = 60;

    /**
     * Seconds per hour.
     */
    int SECONDS_PER_HOUR = SECONDS_PER_MINUTE * MINUTES_PER_HOUR;

    /**
     * Seconds per day.
     */
    int SECONDS_PER_DAY = SECONDS_PER_MINUTE * MINUTES_PER_DAY;


    /* ******************* Milliseconds ********************/
    /**
     * Milliseconds per Seconds.
     */
    int MILLIS_PER_SECONDS = 1000;

    /**
     * Milliseconds per minute.
     */
    int MILLIS_PER_MINUTE = MILLIS_PER_SECONDS * SECONDS_PER_MINUTE;

    /**
     * Milliseconds per hour.
     */
    int MILLIS_PER_HOUR = MILLIS_PER_SECONDS * SECONDS_PER_HOUR;

    /**
     * Milliseconds per day.
     */
    int MILLIS_PER_DAY = MILLIS_PER_SECONDS * SECONDS_PER_DAY;


    /* ******************* Microseconds ********************/
    /**
     * Microseconds per Milliseconds.
     */
    long MICROS_PER_MILLIS = 1000L;

    /**
     * Milliseconds per Seconds.
     */
    long MICROS_PER_SECONDS = 1000_000L;

    /**
     * Microseconds per minute.
     */
    long MICROS_PER_MINUTE = MICROS_PER_SECONDS * SECONDS_PER_MINUTE;

    /**
     * Microseconds per hour.
     */
    long MICROS_PER_HOUR = MICROS_PER_SECONDS * SECONDS_PER_HOUR;

    /**
     * Microseconds per day.
     */
    long MICROS_PER_DAY = MICROS_PER_SECONDS * SECONDS_PER_DAY;


    /* ******************* Nanoseconds ********************/
    /**
     * Nanoseconds per Microseconds.
     */
    long NANOS_PER_MICROS = 1000L;

    /**
     * Nanoseconds per Milliseconds.
     */
    long NANOS_PER_MILLIS = 1000_000L;

    /**
     * Nanoseconds per second.
     */
    long NANOS_PER_SECOND = 1000_000_000L;

    /**
     * Nanoseconds per minute.
     */
    long NANOS_PER_MINUTE = NANOS_PER_SECOND * SECONDS_PER_MINUTE;

    /**
     * Nanoseconds per hour.
     */
    long NANOS_PER_HOUR = NANOS_PER_SECOND * SECONDS_PER_HOUR;

    /**
     * Nanoseconds per day.
     */
    long NANOS_PER_DAY = NANOS_PER_SECOND * SECONDS_PER_DAY;


}
