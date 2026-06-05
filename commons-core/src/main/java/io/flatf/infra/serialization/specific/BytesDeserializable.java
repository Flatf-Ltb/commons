package io.flatf.infra.serialization.specific;

import javax.annotation.Nonnull;

@Nonnull
public interface BytesDeserializable<T extends BytesDeserializable<T>> {

    @Nonnull
    T fromBytes(@Nonnull byte[] bytes);

}
