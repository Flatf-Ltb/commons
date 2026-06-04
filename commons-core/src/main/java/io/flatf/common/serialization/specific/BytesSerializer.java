package io.flatf.common.serialization.specific;

import io.flatf.common.serialization.api.Serializer;

@FunctionalInterface
public interface BytesSerializer<T> extends Serializer<T, byte[]> {
}