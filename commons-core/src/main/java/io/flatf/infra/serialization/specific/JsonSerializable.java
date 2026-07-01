package io.flatf.infra.serialization.specific;

import javax.annotation.Nonnull;

@FunctionalInterface
public interface JsonSerializable {

	@Nonnull
	String toJson();

}
