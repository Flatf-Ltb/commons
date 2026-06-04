package io.flatf.infra.api;

@FunctionalInterface
public interface IndexedMessageConsumer<M> {

    void accept(int index, M message);

}
