package io.flatf.common.serialization.specific;

import io.flatf.common.serialization.api.Serializer;

@FunctionalInterface
public interface JsonSerializer<T> extends Serializer<T, String> {
}
