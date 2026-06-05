package io.flatf.infra.transport.exception;

import java.io.Serial;

public final class ReceiverStartException extends IllegalStateException {

    @Serial
    private static final long serialVersionUID = 5792361596324344884L;

    /**
     * @param msg String
     */
    public ReceiverStartException(String msg) {
        super(msg);
    }

    /**
     * @param cause Throwable
     */
    public ReceiverStartException(Throwable cause) {
        super(cause);
    }

    /**
     * @param msg   String
     * @param cause Throwable
     */
    public ReceiverStartException(String msg, Throwable cause) {
        super(msg, cause);
    }

}
