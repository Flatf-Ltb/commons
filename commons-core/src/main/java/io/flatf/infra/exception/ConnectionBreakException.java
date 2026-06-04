package io.flatf.infra.exception;

import java.io.Serial;

public final class ConnectionBreakException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 1822516879154638625L;

    /**
     * @param msg String
     */
    public ConnectionBreakException(String msg) {
        super(msg);
    }

    /**
     * @param cause Throwable
     */
    public ConnectionBreakException(Throwable cause) {
        super(cause);
    }

    /**
     * @param msg   String
     * @param cause Throwable
     */
    public ConnectionBreakException(String msg, Throwable cause) {
        super(msg, cause);
    }

}
