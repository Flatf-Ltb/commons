package io.flatf.common.codec;

import java.io.Serial;

/**
 * @author yellow013
 */
public final class DecodeException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 1498435101978546958L;

    public DecodeException(String msg) {
        super(msg);
    }

    public DecodeException(Throwable cause) {
        super(cause);
    }

    public DecodeException(String msg, Throwable cause) {
        super(msg, cause);
    }

}
