package io.flatf.infra.serialization.specific;

import io.flatf.infra.serialization.api.Serializer;

import java.io.File;

@FunctionalInterface
public interface FileSerializer<T> extends Serializer<T, File> {
}
