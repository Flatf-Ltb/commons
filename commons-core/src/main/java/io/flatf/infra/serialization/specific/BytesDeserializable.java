package io.flatf.infra.serialization.specific;

import javax.annotation.Nonnull;

@Nonnull
@FunctionalInterface
public interface BytesDeserializable<T extends BytesDeserializable<T>> {

    @Nonnull
    T fromBytes(@Nonnull byte[] bytes);

}
