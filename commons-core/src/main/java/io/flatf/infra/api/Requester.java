package io.flatf.infra.api;

import io.flatf.infra.exception.RequestException;

public interface Requester<T> extends Transport {

	/**
	 *
	 * @return <T> T t
	 */
	T request() throws RequestException;

}
