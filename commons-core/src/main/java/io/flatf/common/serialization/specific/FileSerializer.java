package io.flatf.common.serialization.specific;

import io.flatf.common.serialization.api.Serializer;

import java.io.File;

@FunctionalInterface
public interface FileSerializer<T> extends Serializer<T, File> {
}