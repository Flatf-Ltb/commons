package io.flatf.foundation.transport.exception;

import java.io.Serial;

public final class ConnectionFailedException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = -410726947350896210L;

    /**
     * @param msg String
     */
    public ConnectionFailedException(String msg) {
        super(msg);
    }

    /**
     * @param cause Throwable
     */
    public ConnectionFailedException(Throwable cause) {
        super(cause);
    }

    /**
     * @param msg   String
     * @param cause Throwable
     */
    public ConnectionFailedException(String msg, Throwable cause) {
        super(msg, cause);
    }

}
