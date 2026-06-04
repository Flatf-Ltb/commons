package io.flatf.common.collections.window;

import java.io.Serial;

/**
 * Thrown when the key for a request already exists.
 *
 * @author joelauer (twitter: @jjlauer or
 * <a href="http://twitter.com/jjlauer" target=
 * window>http://twitter.com/jjlauer</a>)
 */
public class DuplicateKeyException extends Exception {

    @Serial
    private static final long serialVersionUID = 730740888169468027L;

    public DuplicateKeyException(String msg) {
        super(msg);
    }

}