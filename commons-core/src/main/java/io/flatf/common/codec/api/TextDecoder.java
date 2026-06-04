package io.flatf.common.codec.api;

@FunctionalInterface
public interface TextDecoder<C extends CharSequence, R> extends Decoder<C, R> {
}