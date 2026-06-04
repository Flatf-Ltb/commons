package io.flatf.common.sys;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadInfo;
import java.lang.management.ThreadMXBean;
import java.lang.reflect.Method;

public final class SystemUtil {

    /**
     * PID value if a process id could not be determined. This value should be equal
     * to a kernel-only process id for the platform so that it does not indicate a
     * real process id.
     */
    public static final long PID_NOT_FOUND = 0;

    private static final String SUN_PID_PROP_NAME = "sun.java.launcher.pid";

    private static final String OS_NAME;

    private static final long PID;

    static {
        OS_NAME = System.getProperty("os.name").toLowerCase();
        long pid = PID_NOT_FOUND;
        try {
            final Class<?> processHandleClass = Class.forName("java.lang.ProcessHandle");
            final Method currentMethod = processHandleClass.getMethod("current");
            final Object processHandle = currentMethod.invoke(null);
            final Method pidMethod = processHandleClass.getMethod("pid");
            pid = (long) pidMethod.invoke(processHandle);
        } catch (final Throwable ignore) {
            try {
                final String pidPropertyValue = System.getProperty(SUN_PID_PROP_NAME);
                if (null != pidPropertyValue) {
                    pid = Long.parseLong(pidPropertyValue);
                } else {
                    final String jvmName = ManagementFactory.getRuntimeMXBean().getName();
                    pid = Long.parseLong(jvmName.split("@")[0]);
                }
            } catch (final Throwable ignore2) {
            }
        }
        PID = pid;
    }

    private SystemUtil() {
    }

    /**
     * Get the name of the operating system as a lower case String.
     * <p>
     * This is what is returned from
     * {@code System.getProperty("os.name").toLowerCase()}.
     *
     * @return the name of the operating system as a lower case String.
     */
    public static String getOsName() {
        return OS_NAME;
    }

    /**
     * Return the current process id from the OS.
     *
     * @return current process id or {@link #PID_NOT_FOUND} if PID was not able to be found.
     * @see #PID_NOT_FOUND
     */
    public static long getPid() {
        return PID;
    }

    /**
     * Is the operating system likely to be Windows based on {@link #getOsName()}.
     *
     * @return true if the operating system is likely to be Windows based on
     * {@link #getOsName()}.
     */
    public static boolean isWindows() {
        return OS_NAME.startsWith("win");
    }

    /**
     * Is the operating system likely to be Linux based on {@link #getOsName()}.
     *
     * @return true if the operating system is likely to be Linux based on
     * {@link #getOsName()}.
     */
    public static boolean isLinux() {
        return OS_NAME.contains("linux");
    }

    /**
     * Get a formatted dump of all threads with associated state and stack traces.
     *
     * @return a formatted dump of all threads with associated state and stack
     * traces.
     */
    public static String threadDump() {
        StringBuilder builder = new StringBuilder();
        ThreadMXBean threadMXBean = ManagementFactory.getThreadMXBean();
        for (ThreadInfo threadInfo : threadMXBean.getThreadInfo(threadMXBean.getAllThreadIds(), Integer.MAX_VALUE)) {
            builder.append('[').append(threadInfo.getThreadName()).append("] : ").append(threadInfo.getThreadState());
            for (StackTraceElement stackTraceElement : threadInfo.getStackTrace()) {
                builder.append("\n    at ").append(stackTraceElement.toString());
            }
            builder.append("\n\n");
        }
        return builder.toString();
    }

    public static void main(String[] args) {

        System.out.println(SystemUtil.getOsName());
        System.out.println(SystemUtil.isLinux());
        System.out.println(SystemUtil.isWindows());
        System.out.println(SystemUtil.getPid());
        System.out.println(SystemUtil.threadDump());

    }

}
