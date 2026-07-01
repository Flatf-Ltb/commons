package io.flatf.infra.serialization.specific;

import javax.annotation.Nonnull;

@FunctionalInterface
public interface BytesSerializable {

    @Nonnull
    byte[] toBytes();

}
