package io.flatf.infra.api;

import io.flatf.infra.exception.PublishFailedException;

public interface Proxy<T, M> extends Transport, Receiver, Publisher<T, M> {

	@Override
	default void publish(M msg) throws PublishFailedException {
		getUpstream().publish(msg);
	}

	@Override
	default void publish(T target, M msg) throws PublishFailedException {
		getUpstream().publish(target, msg);
	}

	Publisher<T, M> getUpstream();

}
