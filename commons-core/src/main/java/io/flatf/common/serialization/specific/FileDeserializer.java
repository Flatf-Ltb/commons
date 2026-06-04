package io.flatf.common.serialization.specific;

import io.flatf.common.serialization.api.Deserializer;

import java.io.File;

@FunctionalInterface
public interface FileDeserializer<T> extends Deserializer<File, T> {
}