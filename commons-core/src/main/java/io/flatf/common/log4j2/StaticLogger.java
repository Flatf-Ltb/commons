package io.flatf.common.log4j2;

import org.slf4j.Logger;

public final class StaticLogger {

    private static final Logger LOG = Log4j2LoggerFactory.getLogger(StaticLogger.class);

    private StaticLogger() {
    }

    /**
     * @param msg  String
     * @param args Object[]
     */
    public static void error(String msg, Object... args) {
        LOG.error(msg, args);
    }

    /**
     * @param msg  String
     * @param args Object[]
     */
    public static void warn(String msg, Object... args) {
        LOG.warn(msg, args);
    }

    /**
     * @param msg  String
     * @param args Object[]
     */
    public static void info(String msg, Object... args) {
        LOG.info(msg, args);
    }

    /**
     * @param msg  String
     * @param args Object[]
     */
    public static void debug(String msg, Object... args) {
        LOG.debug(msg, args);
    }

}
