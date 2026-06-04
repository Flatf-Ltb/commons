package io.flatf.common.log4j2;

import org.apache.logging.log4j.core.Filter;
import org.apache.logging.log4j.core.Filter.Result;
import org.apache.logging.log4j.core.filter.CompositeFilter;
import org.apache.logging.log4j.core.filter.ThresholdFilter;

import java.util.ArrayList;
import java.util.List;

/**
 * @author yellow013
 */
public final class Log4j2Configurator {

    /**
     * @param folder String
     */
    public static synchronized void setLogFolder(String folder) {
        System.setProperty("log4j2.folder", folder);
    }

    /**
     * @param filename String
     */
    public static synchronized void setLogFilename(String filename) {
        System.setProperty("log4j2.filename", filename);
    }

    /**
     *
     */
    public static synchronized void useInfoLogLevel() {
        setLogLevel(LogLevel.INFO);
    }

    /**
     *
     */
    public static synchronized void useWarnLogLevel() {
        setLogLevel(LogLevel.WARN);
    }

    /**
     *
     */
    public static synchronized void useErrorLogLevel() {
        setLogLevel(LogLevel.ERROR);
    }

    /**
     * @param level LogLevel
     */
    public static synchronized void setLogLevel(LogLevel level) {
        System.setProperty("log4j2.level", level.name());
    }

    /**
     * @param sizeOfMb int
     */
    public static synchronized void setFileSizeOfMb(int sizeOfMb) {
        System.setProperty("log4j2.sizeOfMb", Integer.toString(sizeOfMb));
    }

    /**
     * @return String
     */
    public static synchronized String getFolder() {
        return System.getProperty("log4j2.folder");
    }

    /**
     * @return String
     */
    public static synchronized String getFilename() {
        return System.getProperty("log4j2.filename");
    }

    /**
     * @return String
     */
    public static synchronized String getLogLevel() {
        return System.getProperty("log4j2.level");
    }

    /**
     * @return String
     */
    public static synchronized String getFileSizeOfMb() {
        return System.getProperty("log4j2.sizeOfMb");
    }

    /**
     * @author yellow013<br>
     *
     * <b>Use: org.apache.logging.log4j.Level </b><br>
     * <br>
     * Levels used for identifying the severity of an event. Levels are
     * organized from most specific to least:
     * <ul>
     * <li>{@link #FATAL} (most specific, little data)</li>
     * <li>{@link #ERROR}</li>
     * <li>{@link #WARN}</li>
     * <li>{@link #INFO}</li>
     * <li>{@link #DEBUG}</li>
     * </ul>
     * <p>
     * Typically, configuring a level in a filter or on a logger will cause
     * logging events of that level and those that are more specific to pass
     * through the filter. A special level, {@link #DEBUG}, is guaranteed to
     * capture all levels when used in logging configurations.
     */
    public enum LogLevel implements Comparable<LogLevel> {

        DEBUG(1, org.apache.logging.log4j.Level.DEBUG),

        INFO(2, org.apache.logging.log4j.Level.INFO),

        WARN(3, org.apache.logging.log4j.Level.WARN),

        ERROR(4, org.apache.logging.log4j.Level.ERROR),

        FATAL(5, org.apache.logging.log4j.Level.FATAL),

        ;

        private final int priority;
        private final org.apache.logging.log4j.Level level;

        LogLevel(int priority, org.apache.logging.log4j.Level level) {
            this.priority = priority;
            this.level = level;
        }

        public org.apache.logging.log4j.Level getLevel() {
            return level;
        }

        private Filter getFilter(Result mismatch) {
            return ThresholdFilter.createFilter(level, Result.ACCEPT, mismatch);
        }

        public static Filter getCompositeFilterWith(LogLevel level) {
            List<Filter> filters = new ArrayList<>();
            for (LogLevel logLevel : values()) {
                if (logLevel.priority < level.priority)
                    continue;
                filters.add(logLevel.getFilter(logLevel.priority == level.priority ? Result.DENY : Result.NEUTRAL));
            }
            return CompositeFilter.createFilters(filters.toArray(new Filter[0]));
        }

    }

    public static void main(String[] args) {

        Filter compositeFilter = LogLevel.getCompositeFilterWith(LogLevel.INFO);

        System.out.println(compositeFilter);

    }

}
