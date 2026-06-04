package io.flatf.common.collections.window;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.ref.WeakReference;
import java.util.List;

/**
 * Internal utility class to monitor the window and send events upstream to
 * listeners.
 *
 * @author joelauer (twitter: @jjlauer or
 * <a href="http://twitter.com/jjlauer" target=
 * window>http://twitter.com/jjlauer</a>)
 */
public class WindowMonitor<K, R, P> implements Runnable {

    private static final Logger logger = LoggerFactory.getLogger(Window.class);

    private final WeakReference<Window<K, R, P>> windowRef;
    private final String monitorThreadName;

    public WindowMonitor(Window<K, R, P> window, String monitorThreadName) {
        this.windowRef = new WeakReference<>(window);
        this.monitorThreadName = monitorThreadName;
    }

    @Override
    public void run() {
        String currentThreadName = null;
        try {
            if (this.monitorThreadName != null) {
                currentThreadName = Thread.currentThread().getName();
                Thread.currentThread().setName(monitorThreadName);
            }
            Window<K, R, P> window = windowRef.get();

            // check if the window using this monitor was GC ed
            if (window == null) {
                logger.error(
                        "The parent Window was garbage collected in this WindowMonitor(): missing call to Window.reset() to stop this monitoring thread (will throw exception to cancel this recurring execution!)");
                throw new IllegalStateException(
                        "Parent Window was garbage collected (missing call to Window.reset() somewhere in code)");
            }

            if (logger.isTraceEnabled())
                logger.trace("Monitor running... (current window.size [{}])", window.getSize());

            List<WindowFuture<K, R, P>> expired = window.cancelAllExpired();
            if (expired != null && !expired.isEmpty()) {
                if (logger.isTraceEnabled())
                    logger.trace("Monitor found [{}] requests expired ", expired.size());
                // process each expired request and pass up the chain to handlers
                for (WindowFuture<K, R, P> future : expired) {
                    for (UnwrappedWeakReference<WindowListener<K, R, P>> listenerRef : window.getListeners()) {
                        WindowListener<K, R, P> listener = listenerRef.get();
                        if (listener == null) {
                            // remove this reference from our array (no good anymore)
                            window.removeListener(null);
                        } else {
                            try {
                                listener.expired(future);
                            } catch (Throwable t) {
                                logger.error("Ignoring uncaught exception thrown in listener: ", t);
                            }
                        }
                    }
                }
            }
        } finally {
            if (currentThreadName != null) {
                // change the name of the thread back
                Thread.currentThread().setName(currentThreadName);
            }
        }
    }

}
