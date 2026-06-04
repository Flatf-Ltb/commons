package io.flatf.common.util;

import io.flatf.common.log4j2.Log4j2LoggerFactory;
import org.slf4j.Logger;

import javax.annotation.Nullable;
import java.io.Closeable;
import java.io.IOException;
import java.util.function.Consumer;

public final class ResourceUtil {

    private static final Logger log = Log4j2LoggerFactory.getLogger(ResourceUtil.class);

    private ResourceUtil() {
    }

    /**
     * @param obj Object
     * @throws Exception e
     */
    public static <O> void close(@Nullable O obj) throws Exception {
        if (obj != null) {
            if (obj instanceof AutoCloseable autoCloseable)
                close(autoCloseable);
            if (obj instanceof Closeable closeable)
                close(closeable);
        } else
            logNull();
    }

    /**
     * @param closeable AutoCloseable
     * @throws Exception e
     */
    public static void close(@Nullable AutoCloseable closeable) throws Exception {
        if (closeable != null)
            closeable.close();
    }

    /**
     * @param closeable        AutoCloseable
     * @param exceptionHandler Consumer<Exception>
     */
    public static void close(@Nullable AutoCloseable closeable, Consumer<Exception> exceptionHandler) {
        if (closeable != null)
            try {
                closeable.close();
            } catch (Exception e) {
                exceptionHandler.accept(e);
            }
    }

    /**
     * @param closeable Closeable
     * @throws IOException ioe
     */
    public static void close(@Nullable Closeable closeable) throws IOException {
        if (closeable != null)
            closeable.close();
    }

    /**
     * @param closeable        Closeable
     * @param exceptionHandler Consumer<IOException>
     */
    public static void close(@Nullable Closeable closeable, Consumer<IOException> exceptionHandler) {
        if (closeable != null)
            try {
                closeable.close();
            } catch (IOException e) {
                exceptionHandler.accept(e);
            }
    }

    /**
     * @param obj O
     */
    public static <O> void closeIgnoreException(@Nullable O obj) {
        if (obj != null)
            try {
                close(obj);
            } catch (Exception e) {
                log.error("Close object -> [{}] an {} was thrown!", obj.getClass().getName(),
                        e.getClass().getSimpleName(), e);
            }
        else
            logNull();
    }


    /**
     * @param objs O array
     */
    @SafeVarargs
    public static <O> void closeIgnoreException(@Nullable O... objs) {
        if (objs != null)
            for (O obj : objs)
                closeIgnoreException(obj);
        else
            logNull();
    }

    private static void logNull() {
        log.warn("The objects to be closed is NULL");
    }

}
