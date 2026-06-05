package io.flatf.common.sys;

import org.slf4j.Logger;

import java.io.File;

import static java.lang.System.getProperties;
import static java.lang.System.out;

public final class SysProperties {

    /**
     * @param key String
     * @return String
     */
    public static String get(String key) {
        return System.getProperty(key);
    }

    /**
     * System.getProperty("os.name")
     */
    public static final String OS_NAME = System.getProperty("os.name");

    public static boolean isWindows() {
        return OS_NAME.toLowerCase().startsWith("windows");
    }

    public static boolean isLinux() {
        return OS_NAME.toLowerCase().startsWith("linux");
    }

    /**
     * System.getProperty("java.home")
     */
    public static final String JAVA_HOME = System.getProperty("java.home");

    /**
     * System.getProperty("java.class.path")
     */
    public static final String JAVA_CLASS_PATH = System.getProperty("java.class.path");

    /**
     * System.getProperty("java.version")
     */
    public static final String JAVA_VERSION = System.getProperty("java.version");

    /**
     * System.getProperty("java.vm.name")
     */
    public static final String JAVA_VM_NAME = System.getProperty("java.vm.name");

    /**
     * System.getProperty("java.runtime.name")
     */
    public static final String JAVA_RUNTIME_NAME = System.getProperty("java.runtime.name");

    /**
     * System.getProperty("java.runtime.version")
     */
    public static final String JAVA_RUNTIME_VERSION = System.getProperty("java.runtime.version");

    /*
     * System.getProperty("java.library.path")
     */
    public static final String JAVA_LIBRARY_PATH = System.getProperty("java.library.path");

    /**
     * System.getProperty("java.io.tmpdir")
     */
    public static final String JAVA_IO_TMPDIR = System.getProperty("java.io.tmpdir");

    /**
     * tmpdir file
     */
    public static final File JAVA_IO_TMPDIR_FILE = new File(JAVA_IO_TMPDIR);

    /**
     * System.getProperty("user.name")
     */
    public static final String USER_NAME = System.getProperty("user.name");

    /**
     * System.getProperty("user.home")
     */
    public static final String USER_HOME = System.getProperty("user.home");

    /**
     * user.home file
     */
    public static final File USER_HOME_FILE = new File(USER_HOME);

    /**
     * ${user.home}/.config/
     */
    public static final File USER_HOME_CONFIG_FOLDER = new File(USER_HOME_FILE, ".config");

    /**
     * System.getProperty("user.dir")
     */
    public static final String USER_DIR = System.getProperty("user.dir");

    /**
     * user.dir file
     */
    public static final File USER_DIR_FILE = new File(USER_DIR);

    /**
     * System.getProperty("user.timezone")
     */
    public static final String USER_TIMEZONE = System.getProperty("user.timezone");

    /**
     * System.getProperty("user.language")
     */
    public static final String USER_LANGUAGE = System.getProperty("user.language");

    /**
     * System.getProperty("user.country")
     */
    public static final String USER_COUNTRY = System.getProperty("user.country");

    /**
     *
     */
    public static void showAll() {
        showAll(null);
    }

    /**
     * @param log Logger
     */
    public static void showAll(Logger log) {
        if (log != null) {
            getProperties().forEach((key, value) -> log.info("{} -> {}", key, value));
        } else {
            getProperties().forEach((key, value) -> out.println(key + " -> " + value));
        }
    }

}
