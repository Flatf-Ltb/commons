package io.flatf.foundation.transport.api;

@FunctionalInterface
public interface IndexedMessageConsumer<M> {

    void accept(int index, M message);

}
