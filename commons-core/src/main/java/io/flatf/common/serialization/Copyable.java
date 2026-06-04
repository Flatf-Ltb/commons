package io.flatf.common.serialization;

public interface Copyable<T extends Copyable<T>> {

    void copyFrom(T source);

}
