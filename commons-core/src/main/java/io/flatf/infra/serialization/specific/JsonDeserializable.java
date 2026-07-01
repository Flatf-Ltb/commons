package io.flatf.infra.serialization.specific;

import javax.annotation.Nonnull;

@FunctionalInterface
public interface JsonDeserializable<T extends JsonDeserializable<T>> {

    @Nonnull
    T fromJson(@Nonnull String json);

}
