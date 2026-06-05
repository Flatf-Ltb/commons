package io.flatf.infra.transport.api;

import io.flatf.infra.transport.exception.RequestException;

public interface Requester<T> extends Transport {

	/**
	 *
	 * @return <T> T t
	 */
	T request() throws RequestException;

}
