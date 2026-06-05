package io.flatf.infra.serialization.specific;

import javax.annotation.Nonnull;

public interface JsonDeserializable<T extends JsonDeserializable<T>> {

	@Nonnull
	T fromJson(@Nonnull String json);

}
