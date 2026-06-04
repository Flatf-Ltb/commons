package io.flatf.common.lang.exception;

import java.io.Serial;

public final class NotImplementedFunctionException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 947213334800336824L;

    public NotImplementedFunctionException(Class<?> clazz, String functionName) {
        super(String.format("ERROR [%s] Not implemented [%s]", clazz.getName(), functionName));
    }

}
