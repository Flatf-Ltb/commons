package io.flatf.common.thread;

import java.io.Serial;

public class RuntimeInterruptedException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = -4687520758683166486L;

    public RuntimeInterruptedException(String message, InterruptedException exception) {
        super(message, exception);
    }

    public RuntimeInterruptedException(InterruptedException exception) {
        super("Catch [InterruptedException] and conversion to [RuntimeInterruptedException]\n message : " + exception.getMessage(), exception);
    }

}
