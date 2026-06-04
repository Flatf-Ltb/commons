package io.flatf.common.serialization.api;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.function.Function;

@FunctionalInterface
public interface Deserializer<T, R> extends Function<T, R> {

    @Override
    @Nonnull
    default R apply(T t) {
        return deserialization(t);
    }

    @Nonnull
    default R deserialization(@Nonnull T source) throws RuntimeException {
        return deserialization(source, null);
    }

    @Nonnull
    R deserialization(@Nonnull T source, @Nullable R reuse) throws RuntimeException;

}
