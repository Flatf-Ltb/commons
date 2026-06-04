package io.flatf.infra.exception;

import java.io.Serial;

public final class InitializeFailureException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = -3673823194109364790L;

    /**
     * @param msg String
     */
    public InitializeFailureException(String msg) {
        super(msg);
    }

    /**
     * @param cause Throwable
     */
    public InitializeFailureException(Throwable cause) {
        super(cause);
    }

    /**
     * @param msg   String
     * @param cause Throwable
     */
    public InitializeFailureException(String msg, Throwable cause) {
        super(msg, cause);
    }

}
