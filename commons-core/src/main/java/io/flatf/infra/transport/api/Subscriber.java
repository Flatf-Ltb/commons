package io.flatf.infra.transport.api;

import io.flatf.common.annotation.thread.AsyncFunction;

public interface Subscriber extends Transport, Runnable {

    /**
     * Start to subscribe
     */
    @AsyncFunction
    void subscribe();

    /**
     * @return whether reconnect is supported by this subscriber implementation
     */
    default boolean reconnectSupported() {
        return false;
    }

    /**
     * Reconnect
     */
    default void reconnect() {
        throw new UnsupportedOperationException(getClass().getName() + " does not support reconnect");
    }

    @Override
    default void run() {
        subscribe();
    }

}
