package io.flatf.infra.api;

import io.flatf.common.annotation.thread.AsyncFunction;
import io.flatf.infra.exception.ConnectionBreakException;
import io.flatf.infra.exception.ReceiverStartException;

public interface Receiver extends Transport, Runnable {

    /**
     * Start receive
     *
     * @throws ReceiverStartException e
     */
    @AsyncFunction
    void receive() throws ReceiverStartException;

    /**
     * @return whether reconnect is supported by this receiver implementation
     */
    default boolean reconnectSupported() {
        return false;
    }

    /**
     * Reconnect
     *
     * @throws ConnectionBreakException e0
     * @throws ReceiverStartException   e1
     */
    default void reconnect() throws ConnectionBreakException, ReceiverStartException {
        throw new UnsupportedOperationException(getClass().getName() + " does not support reconnect");
    }

    @Override
    default void run() {
        receive();
    }

}
