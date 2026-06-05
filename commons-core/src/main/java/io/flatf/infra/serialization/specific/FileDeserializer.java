package io.flatf.infra.serialization.specific;

import io.flatf.infra.serialization.api.Deserializer;

import java.io.File;

@FunctionalInterface
public interface FileDeserializer<T> extends Deserializer<File, T> {
}
