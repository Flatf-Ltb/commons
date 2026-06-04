package io.flatf.common.lang.exception;

import java.io.IOException;

public class RuntimeIOException extends RuntimeException {

    public RuntimeIOException(final String msg) {
        super(msg);
    }

    public RuntimeIOException(final IOException ioe) {
        super(ioe.getMessage(), ioe);
    }

    public RuntimeIOException(final String msg, final IOException ioe) {
        super(msg, ioe);
    }

}
