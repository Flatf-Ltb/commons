package io.flatf.infra.serialization.specific;

import io.flatf.infra.serialization.api.Serializer;

import java.nio.ByteBuffer;

@FunctionalInterface
public interface ByteBufferSerializer<T> extends Serializer<T, ByteBuffer> {
}
