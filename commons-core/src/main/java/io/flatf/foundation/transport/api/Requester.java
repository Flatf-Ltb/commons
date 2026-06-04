package io.flatf.foundation.transport.api;

import io.flatf.foundation.transport.exception.RequestException;

public interface Requester<T> extends Transport {

	/**
	 * 
	 * @return <T> T t
	 */
	T request() throws RequestException;

}
