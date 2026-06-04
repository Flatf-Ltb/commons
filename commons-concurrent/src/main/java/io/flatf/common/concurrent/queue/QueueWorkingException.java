package io.flatf.common.concurrent.queue;

import java.io.Serial;

public final class QueueWorkingException extends RuntimeException {

	@Serial
	private static final long serialVersionUID = -117741434920742324L;

	public QueueWorkingException(String msg, Throwable throwable) {
		super(msg, throwable);
	}

}
