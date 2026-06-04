package io.flatf.common.collections.window;

/**
 * Interface for listening to events triggered by a window.
 *
 * @author joelauer (twitter: @jjlauer or
 * <a href="http://twitter.com/jjlauer" target=
 * window>http://twitter.com/jjlauer</a>)
 */
public interface WindowListener<K, R, P> {

    /**
     * Called when a future has been automatically expired by a window in its
     * internal monitoring task. Since the thread that will call this method is
     * potentially shared by any window in the JVM, it's best to handle this event
     * as quickly as possible.
     *
     * @param future The future that expired based on its ExpireTimestamp
     */
    void expired(WindowFuture<K, R, P> future);

}
