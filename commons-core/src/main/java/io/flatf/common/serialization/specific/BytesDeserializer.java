package io.flatf.common.serialization.specific;

import io.flatf.common.serialization.api.Deserializer;

@FunctionalInterface
public interface BytesDeserializer<R> extends Deserializer<byte[], R> {
}
