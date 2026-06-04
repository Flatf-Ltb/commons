package io.flatf.common.log4j2;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Filter;
import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.core.appender.RollingFileAppender;
import org.apache.logging.log4j.core.appender.rolling.CompositeTriggeringPolicy;
import org.apache.logging.log4j.core.appender.rolling.DefaultRolloverStrategy;
import org.apache.logging.log4j.core.appender.rolling.SizeBasedTriggeringPolicy;
import org.apache.logging.log4j.core.appender.rolling.TimeBasedTriggeringPolicy;
import org.apache.logging.log4j.core.config.AppenderRef;
import org.apache.logging.log4j.core.config.Configuration;
import org.apache.logging.log4j.core.config.LoggerConfig;
import org.apache.logging.log4j.core.filter.ThresholdFilter;
import org.apache.logging.log4j.core.layout.PatternLayout;

import java.io.File;

@Deprecated
public class LogUtil {

    /**
     * 日志打印的目录
     */
    private static final String LOG_DIR = "/tmp/biz-logs";

    /**
     * Logger Context对象
     */
    private static final LoggerContext CTX = (LoggerContext) LogManager.getContext(false);

    /**
     * Configuration对象
     */
    private static final Configuration CONF = CTX.getConfiguration();

    /**
     * 启动一个动态的logger
     *
     * @param loggerName String
     */
    private static void start(String loggerName) {
        // 创建一个展示的样式: PatternLayout, 还有其他的日志打印样式。
        PatternLayout layout = PatternLayout.newBuilder().withConfiguration(CONF)
                .withPattern("%d{yy-MM-dd HH:mm:ss.SSS} %-5level [%t] [%C{1}.%M] %msg%n").build();

        // 单个日志文件大小
        TimeBasedTriggeringPolicy tbtp = TimeBasedTriggeringPolicy.newBuilder().withInterval(1).withModulate(true)
                .build();
        // 大小分割
        SizeBasedTriggeringPolicy tp = SizeBasedTriggeringPolicy.createPolicy("255MB");
        CompositeTriggeringPolicy policyComposite = CompositeTriggeringPolicy.createPolicy(tbtp, tp);

        String loggerDir = LOG_DIR + File.separator + "quartz-" + loggerName;
        DefaultRolloverStrategy strategy = DefaultRolloverStrategy.newBuilder().withMax("30").withConfig(CONF).build();

        ThresholdFilter infoFilter = ThresholdFilter.createFilter(Level.INFO, Filter.Result.ACCEPT, Filter.Result.DENY);

        ThresholdFilter errorFilter = ThresholdFilter.createFilter(Level.ERROR, Filter.Result.ACCEPT,
                Filter.Result.NEUTRAL);

        RollingFileAppender appender = RollingFileAppender.newBuilder().withFileName(loggerDir + ".log")
                .withFilePattern(loggerDir + ".%d{yyyy-MM-dd}.%i.log.gz").withAppend(true).withPolicy(policyComposite)
                .withStrategy(strategy).setName(loggerName).setFilter(infoFilter).setFilter(errorFilter)
                .setLayout(layout).setConfiguration(CONF).build();
        appender.start();
        CONF.addAppender(appender);

        AppenderRef ref = AppenderRef.createAppenderRef(loggerName, null, null);

        LoggerConfig loggerConfig = LoggerConfig.createLogger(false, Level.INFO, loggerName, "true",
                new AppenderRef[]{ref}, null, CONF, null);
        loggerConfig.addAppender(appender, Level.INFO, null);
        CONF.addLogger(loggerName, loggerConfig);
        CTX.updateLoggers();
    }

    /**
     * 关闭动态创建的logger，避免内存不够用或者文件打开太多
     *
     * @param loggerName String
     */
    public static void stop(String loggerName) {
        synchronized (CONF) {
            CONF.getAppender(loggerName).stop();
            CONF.getLoggerConfig(loggerName).removeAppender(loggerName);
            CONF.removeLogger(loggerName);
            CTX.updateLoggers();
        }
    }

    /**
     * 获取Logger
     *
     * @param loggerName String
     */
    public static org.slf4j.Logger getLogger(String loggerName) {
        synchronized (CONF) {
            if (!CONF.getLoggers().containsKey(loggerName))
                start(loggerName);
        }
        return org.slf4j.LoggerFactory.getLogger(loggerName);
    }

}
