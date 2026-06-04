package io.flatf.common.state;

import java.io.Serial;

public final class StartupException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = -5641797236046682009L;

    public StartupException(String msg) {
        super(msg);
    }

    public StartupException(String msg, Throwable cause) {
        super(msg, cause);
    }

}
