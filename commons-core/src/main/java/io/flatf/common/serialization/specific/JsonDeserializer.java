package io.flatf.common.serialization.specific;

import io.flatf.common.serialization.api.Deserializer;

@FunctionalInterface
public interface JsonDeserializer<R> extends Deserializer<String, R> {
}