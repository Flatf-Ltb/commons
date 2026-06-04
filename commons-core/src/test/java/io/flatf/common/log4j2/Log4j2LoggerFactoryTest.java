package io.flatf.common.log4j2;

import io.flatf.common.log4j2.Log4j2Configurator.LogLevel;
import org.junit.Test;
import org.slf4j.Logger;

import java.time.LocalDateTime;

public class Log4j2LoggerFactoryTest {

    static {
        System.out.println(System.getProperty("user.home"));
//		Log4j2Configurator.setLogFilename("test");
        Log4j2Configurator.useInfoLogLevel();
//		Log4j2Configurator.setFileSizeOfMb(128);
    }

    private static final Logger log = Log4j2LoggerFactory.getLogger(Log4j2LoggerFactory.class);

    @Test
    public void testLogger() {

        Exception e = new NullPointerException("test null point");

        log.error("this is error", e);
        log.warn("this is warn");
        log.info("this is info");
        log.debug("this is debug");

        System.out.println(LocalDateTime.now());

        // loop();

    }

    public void loop() {
        for (; ; ) {
            log.error("error log info");
            log.warn("warn log info");
            log.info("info log info");
            log.debug("debug log info");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Test
    public void testBizLogger() {
        Logger logger0 = Log4j2LoggerFactory.getBizLogger("text0", LogLevel.ERROR);
        for (int i = 0; i < 10; i++) {
            logger0.debug("logger1 -> {}", i);
            logger0.info("logger0 -> {}", i + 10);
            logger0.error("logger0 -> {}", i + 20);
        }

        Logger logger1 = Log4j2LoggerFactory.getBizLogger("text1", LogLevel.INFO);
        for (int i = 0; i < 10; i++) {
            logger1.debug("logger1 -> {}", i);
            logger1.info("logger1 -> {}", i + 10);
            logger1.error("logger1 -> {}", i + 20);
        }
    }


}
