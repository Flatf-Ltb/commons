package io.flatf.common.concurrent.queue;

import io.flatf.common.collections.queue.Queue;

import javax.annotation.CheckForNull;
import javax.annotation.Nonnull;
import java.util.function.Predicate;

/**
 * 
 * @author yellow013
 *
 * @param <E>
 */
public interface MultiConsumerQueue<E> extends Queue<E> {

	@CheckForNull
	E dequeue();

	default E poll() {
		return dequeue();
	}

	default boolean pollAndApply(@Nonnull PollFunction<E> function) {
		return function.apply(dequeue());
	}

	@FunctionalInterface
	interface PollFunction<E> extends Predicate<E> {

		boolean apply(E e);

		@Override
		default boolean test(E e) {
			return apply(e);
		}

	}

}
