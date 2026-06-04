package io.flatf.foundation.transport.exception;

import java.io.Serial;

public final class PublishFailedException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = -4306166538549739230L;

    /**
     * @param msg String
     */
    public PublishFailedException(String msg) {
        super(msg);
    }

    /**
     * @param cause Throwable
     */
    public PublishFailedException(Throwable cause) {
        super(cause);
    }

    /**
     * @param msg   String
     * @param cause Throwable
     */
    public PublishFailedException(String msg, Throwable cause) {
        super(msg, cause);
    }

}
