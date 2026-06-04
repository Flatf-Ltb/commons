package io.flatf.common.codec.api;

@FunctionalInterface
public interface TextEncoder<T, C extends CharSequence> extends Encoder<T, C> {
}