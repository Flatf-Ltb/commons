package io.flatf.common.serialization.specific;

import io.flatf.common.serialization.api.Deserializer;

import java.nio.ByteBuffer;

@FunctionalInterface
public interface ByteBufferDeserializer<R> extends Deserializer<ByteBuffer, R> {
}