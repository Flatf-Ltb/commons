package io.flatf.infra.transport.api;

@FunctionalInterface
public interface IndexedMessageConsumer<M> {

    void accept(int index, M message);

}
