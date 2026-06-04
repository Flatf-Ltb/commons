package io.flatf.common.functional;

import java.util.function.Supplier;

/**
 * @param <T>
 * @author yellow013
 */
@FunctionalInterface
public interface Formatter<T> extends Supplier<T> {

    T format();

    @Override
    default T get() {
        return format();
    }

}
