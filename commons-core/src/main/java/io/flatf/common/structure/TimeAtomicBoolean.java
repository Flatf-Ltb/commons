package io.flatf.common.structure;

import java.time.Duration;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Wrapper around Java AtomicBoolean to include the DateTime of when the state
 * last changed, effectively providing a "time" of the current state.
 */
public class TimeAtomicBoolean {

    private final AtomicBoolean value;

    private final Boolean expectedValue;

    private long updatedTime;

    /**
     * Creates a new instance with the initialValue. This constructor does not set up
     * an expectedValue, so either value will be ok.
     *
     * @param initialValue The initial value of the boolean.
     */
    public TimeAtomicBoolean(boolean initialValue) {
        this.value = new AtomicBoolean(initialValue);
        this.expectedValue = null;
        this.updatedTime = System.currentTimeMillis();
    }

    /**
     * Creates a new instance with the initialValue. The expectedValue is the value
     * we would expect if this is in a good state.
     *
     * @param initialValue  The initial value of the boolean.
     * @param expectedValue The expected value of the boolean if this was in a good
     *                      state.
     */
    public TimeAtomicBoolean(boolean initialValue, boolean expectedValue) {
        this.value = new AtomicBoolean(initialValue);
        this.expectedValue = expectedValue;
        this.updatedTime = System.currentTimeMillis();
    }

    /**
     * Returns the expected value of this state boolean. If this instance does not
     * have an expectedValue set, this will always return the current value.
     *
     * @return boolean
     */
    public boolean getExpected() {
        return Objects.requireNonNullElseGet(expectedValue, this::get);
    }

    /**
     * Tests whether this state boolean is what we would expect it to be. If no
     * expectedValue is actually set, this will always return true.
     *
     * @return boolean
     */
    public boolean isExpected() {
        // if no expectedValue is set, this is always true
        if (this.expectedValue == null)
            return true;
        return (this.expectedValue == get());
    }

    /**
     * Sets to the given value and returns the previous value. If the previous value
     * is different from the new value, the internal "valueTime" will be updated.
     *
     * @param newValue The new boolean value
     * @return boolean
     */
    public boolean getAndSet(boolean newValue) {
        boolean prev = value.getAndSet(newValue);
        // did the state change?
        if (prev != newValue)
            updatedTime = System.currentTimeMillis();
        return prev;
    }

    /**
     * Unconditionally sets to the given value.
     *
     * @param newValue The new boolean value
     */
    public void set(boolean newValue) {
        // call internal method in order to track the time
        getAndSet(newValue);
    }

    /**
     * Returns the current value.
     *
     * @return Returns the current value.
     */
    public boolean get() {
        return value.get();
    }

    /**
     * Returns the timestamp (num of milliseconds via System.currentTimeMillis) of
     * the last time this boolean changed state. In order to calculate the total
     * number of milliseconds this boolean has retained this value, you'll need to
     * do your own call to System.currentTimeMillis() and subtract this value.
     *
     * @return long
     */
    public long getUpdatedMillis() {
        return updatedTime;
    }

    /**
     * Returns a DateTime that represents the last time this boolean changed state.
     *
     * @return ZonedDateTime
     */
    public ZonedDateTime getUpdatedTime() {
        return ZonedDateTime.ofInstant(Instant.ofEpochMilli(updatedTime), ZoneOffset.UTC);
    }

    /**
     * @param zoneId ZoneId
     * @return ZonedDateTime
     */
    public ZonedDateTime getValueDateTime(ZoneId zoneId) {
        return ZonedDateTime.ofInstant(Instant.ofEpochMilli(updatedTime), zoneId);
    }

    /**
     * Based on the time this method is called (System.currentTimeMillis), this
     * method returns a Period that represents the duration of time this value has
     * retained this value.
     *
     * @see #getDurationMills()
     */
    public Duration getDuration() {
        long now = System.currentTimeMillis();
        return Duration.ofMillis(now - updatedTime);
    }

    /**
     * Based on the time this method is called (System.currentTimeMillis), this
     * method returns a long that represents the duration of time this value has
     * retained this value.
     *
     * @see #getDuration()
     */
    public long getDurationMills() {
        return (System.currentTimeMillis() - updatedTime);
    }

    @Override
    public String toString() {
        return value.toString();
    }

}
