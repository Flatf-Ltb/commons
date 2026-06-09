package io.flatf.common.concurrent.disruptor;

/**
 * Contract for events stored in a {@link RingEventbus}.
 *
 * <p>Disruptor reuses slot objects. Every event object accepted by {@link RingEventbus}
 * must reset its observable state before each publish to avoid stale field leakage.</p>
 */
public interface ReusableEvent {

    /**
     * Reset the reused event slot to an empty state before a translator writes the next event.
     */
    void clear();

    /**
     * Return a stable diagnostic snapshot of the current event state.
     *
     * <p>The returned string must describe the event state at call time and must not expose mutable
     * event objects to asynchronous error callbacks.</p>
     *
     * @return Current event snapshot
     */
    String snapshot();

}
