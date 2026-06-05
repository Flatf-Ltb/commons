package io.flatf.infra.serialization;

public interface Copyable<T extends Copyable<T>> {

    void copyFrom(T source);

}
