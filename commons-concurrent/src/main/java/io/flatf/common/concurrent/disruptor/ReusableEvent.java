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

}
