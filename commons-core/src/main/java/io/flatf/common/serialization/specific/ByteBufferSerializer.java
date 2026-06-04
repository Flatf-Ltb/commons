package io.flatf.common.serialization.specific;

import io.flatf.common.serialization.api.Serializer;

import java.nio.ByteBuffer;

@FunctionalInterface
public interface ByteBufferSerializer<T> extends Serializer<T, ByteBuffer> {
}
