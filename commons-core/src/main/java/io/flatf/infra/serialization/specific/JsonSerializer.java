package io.flatf.infra.serialization.specific;

import io.flatf.infra.serialization.api.Serializer;

@FunctionalInterface
public interface JsonSerializer<T> extends Serializer<T, String> {
}
