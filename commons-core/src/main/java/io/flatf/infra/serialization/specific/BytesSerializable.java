package io.flatf.infra.serialization.specific;

import javax.annotation.Nonnull;

public interface BytesSerializable {

    @Nonnull
    byte[] toBytes();

}
