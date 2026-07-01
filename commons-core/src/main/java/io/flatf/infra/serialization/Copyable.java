package io.flatf.infra.serialization;

@FunctionalInterface
public interface Copyable<T extends Copyable<T>> {

    void copyFrom(T source);

}
