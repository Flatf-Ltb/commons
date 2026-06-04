package io.flatf.common.lang.exception;

import java.io.Serial;

public final class NativeLibraryException extends Exception {

    @Serial
    private static final long serialVersionUID = -5877471358569299269L;

    public NativeLibraryException(String msg, Throwable cause) {
        super(msg, cause);
    }

}