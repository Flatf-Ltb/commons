package io.flatf.common.collections.window;

import java.io.Serial;

/**
 * Thrown if an offer cannot be accepted within a specified amount of time.
 * 
 * @author joelauer (twitter: @jjlauer or
 *         <a href="http://twitter.com/jjlauer" target=
 *         window>http://twitter.com/jjlauer</a>)
 */
public class OfferTimeoutException extends Exception {

	@Serial
	private static final long serialVersionUID = 5509263104569242653L;

	public OfferTimeoutException(String msg) {
		super(msg);
	}

	public OfferTimeoutException(String msg, Throwable cause) {
		super(msg, cause);
	}

}
