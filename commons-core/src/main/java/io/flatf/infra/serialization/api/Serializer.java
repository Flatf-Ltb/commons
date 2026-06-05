package io.flatf.infra.serialization.api;

import javax.annotation.Nullable;
import java.util.function.Function;

@FunctionalInterface
public interface Serializer<T, R> extends Function<T, R> {

    @Nullable
    R serialize(T source);

    @Override
    default R apply(T t) {
        return serialize(t);
    }

}
