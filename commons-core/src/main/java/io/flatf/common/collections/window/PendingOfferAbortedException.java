package io.flatf.common.collections.window;

import java.io.Serial;

/**
 * Thrown when a caller/thread is waiting for an offer to be accepted, but
 * abortPendingOffers() is called by a different thread. Rather than wait for
 * the offer to be accepted for the full offerTimeoutMillis, this is an
 * immediate timeout.
 * 
 * @author joelauer (twitter: @jjlauer or
 *         <a href="http://twitter.com/jjlauer" target=
 *         window>http://twitter.com/jjlauer</a>)
 */
public class PendingOfferAbortedException extends OfferTimeoutException {

	@Serial
	private static final long serialVersionUID = -3510062097127020066L;

	public PendingOfferAbortedException(String msg) {
		super(msg);
	}

	public PendingOfferAbortedException(String msg, Throwable cause) {
		super(msg, cause);
	}

}
