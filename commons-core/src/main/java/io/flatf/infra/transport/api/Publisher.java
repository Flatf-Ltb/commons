package io.flatf.infra.transport.api;

import io.flatf.infra.transport.exception.PublishFailedException;

/**
 * @param <T> target type
 * @param <M> message type
 * @author yellow013
 */
public interface Publisher<T, M> extends Transport {

    /**
     * Publish to default location
     *
     * @param msg M
     * @throws PublishFailedException e
     */
    void publish(M msg) throws PublishFailedException;

    /**
     * Publish to target location
     *
     * @param target T
     * @param msg    M
     * @throws PublishFailedException e
     */
    default void publish(T target, M msg) throws PublishFailedException {
        throw new UnsupportedOperationException(getClass().getName() + " does not support targeted publish");
    }

    /**
     * @return whether the publisher supports a caller-specified target.
     */
    default boolean supportsTargetedPublish() {
        return true;
    }

}
