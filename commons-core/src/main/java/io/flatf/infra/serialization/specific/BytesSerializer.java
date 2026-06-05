package io.flatf.infra.serialization.specific;

import io.flatf.infra.serialization.api.Serializer;

@FunctionalInterface
public interface BytesSerializer<T> extends Serializer<T, byte[]> {
}
