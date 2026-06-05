package io.flatf.infra.serialization.specific;

import io.flatf.infra.serialization.api.Deserializer;

@FunctionalInterface
public interface BytesDeserializer<R> extends Deserializer<byte[], R> {
}
