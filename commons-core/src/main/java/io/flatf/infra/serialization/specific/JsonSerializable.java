package io.flatf.infra.serialization.specific;

import javax.annotation.Nonnull;

public interface JsonSerializable {

	@Nonnull
	String toJson();

}
