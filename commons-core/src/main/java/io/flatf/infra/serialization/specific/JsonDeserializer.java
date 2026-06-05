package io.flatf.infra.serialization.specific;

import io.flatf.infra.serialization.api.Deserializer;

@FunctionalInterface
public interface JsonDeserializer<R> extends Deserializer<String, R> {
}
