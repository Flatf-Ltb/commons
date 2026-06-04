package io.flatf.common.codec.api;

import java.nio.ByteBuffer;

@FunctionalInterface
public interface ByteBufferEncoder<T> extends Encoder<T, ByteBuffer> {
}