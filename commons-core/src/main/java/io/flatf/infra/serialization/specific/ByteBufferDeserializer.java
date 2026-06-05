package io.flatf.infra.serialization.specific;

import io.flatf.infra.serialization.api.Deserializer;

import java.nio.ByteBuffer;

@FunctionalInterface
public interface ByteBufferDeserializer<R> extends Deserializer<ByteBuffer, R> {
}
