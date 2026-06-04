package io.flatf.foundation.transport.exception;

import java.io.Serial;

public final class RequestException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = -1535413038727671000L;

    /**
     * @param msg String
     */
    public RequestException(String msg) {
        super(msg);
    }

    /**
     * @param cause Throwable
     */
    public RequestException(Throwable cause) {
        super(cause);
    }

    /**
     * @param msg   String
     * @param cause Throwable
     */
    public RequestException(String msg, Throwable cause) {
        super(msg, cause);
    }

}
