package io.flatf.common.thread;

import io.flatf.common.collections.MutableMaps;
import io.flatf.common.util.StringSupport;
import org.eclipse.collections.api.map.MutableMap;

import javax.annotation.Nonnull;
import java.lang.management.ManagementFactory;
import java.lang.management.ThreadInfo;
import java.lang.management.ThreadMXBean;

import static io.flatf.common.thread.ThreadPriority.MAX;
import static io.flatf.common.thread.ThreadPriority.MIN;
import static io.flatf.common.thread.ThreadPriority.getOrDefValue;
import static java.lang.Thread.ofPlatform;

public final class Threads {

    private Threads() {
    }

    /**
     * @param task Runnable
     * @return java.lang.Thread
     */
    public static Thread newThread(Runnable task) {
        return ofPlatform()
                .unstarted(task);
    }

    /**
     * @param name String
     * @param task Runnable
     * @return java.lang.Thread
     */
    public static Thread newThread(String name, Runnable task) {
        return ofPlatform()
                .name(name)
                .unstarted(task);
    }

    /**
     * @param priority ThreadPriority
     * @param task     Runnable
     * @return java.lang.Thread
     */
    public static Thread newThread(ThreadPriority priority, Runnable task) {
        return ofPlatform()
                .priority(getOrDefValue(priority))
                .unstarted(task);
    }

    /**
     * @param name     String
     * @param priority ThreadPriority
     * @param task     Runnable
     * @return java.lang.Thread
     */
    public static Thread newThread(String name, ThreadPriority priority, Runnable task) {
        return ofPlatform()
                .name(name)
                .priority(getOrDefValue(priority))
                .unstarted(task);
    }

    /**
     * @param task Runnable
     * @return java.lang.Thread
     */
    public static Thread newMaxPriorityThread(Runnable task) {
        return newThread(MAX, task);
    }

    /**
     * @param name String
     * @param task Runnable
     * @return java.lang.Thread
     */
    public static Thread newMaxPriorityThread(String name, Runnable task) {
        return newThread(name, MAX, task);
    }

    /**
     * @param task Runnable
     * @return java.lang.Thread
     */
    public static Thread newMinPriorityThread(Runnable task) {
        return newThread(MIN, task);
    }

    /**
     * @param name String
     * @param task Runnable
     * @return java.lang.Thread
     */
    public static Thread newMinPriorityThread(String name, Runnable task) {
        return newThread(name, MIN, task);
    }

    /// ################################################################################ ///

    /**
     * @param task Runnable
     * @return java.lang.Thread
     */
    public static Thread startNewThread(Runnable task) {
        return ofPlatform()
                .start(task);
    }

    /**
     * @param name String
     * @param task Runnable
     * @return java.lang.Thread
     */
    public static Thread startNewThread(String name, Runnable task) {
        return ofPlatform()
                .name(name)
                .start(task);
    }

    /**
     * @param priority ThreadPriority
     * @param task     Runnable
     * @return java.lang.Thread
     */
    public static Thread startNewThread(ThreadPriority priority, Runnable task) {
        return ofPlatform()
                .priority(getOrDefValue(priority))
                .start(task);
    }

    /**
     * @param name     String
     * @param priority ThreadPriority
     * @param task     Runnable
     * @return java.lang.Thread
     */
    public static Thread startNewThread(String name, ThreadPriority priority, Runnable task) {
        return ofPlatform()
                .name(name)
                .priority(getOrDefValue(priority))
                .start(task);
    }

    /**
     * @param task Runnable
     * @return java.lang.Thread
     */
    public static Thread startNewMaxPriorityThread(Runnable task) {
        return startNewThread(MAX, task);
    }

    /**
     * @param name String
     * @param task Runnable
     * @return java.lang.Thread
     */
    public static Thread startNewMaxPriorityThread(String name, Runnable task) {
        return startNewThread(name, MAX, task);
    }

    /**
     * @param task Runnable
     * @return java.lang.Thread
     */
    public static Thread startNewMinPriorityThread(Runnable task) {
        return startNewThread(MIN, task);
    }

    /**
     * @param name String
     * @param task Runnable
     * @return java.lang.Thread
     */
    public static Thread startNewMinPriorityThread(String name, Runnable task) {
        return startNewThread(name, MIN, task);
    }

    /// ################################################################################ ///

    /**
     * @throws RuntimeInterruptedException re
     */
    public static void join() throws RuntimeInterruptedException {
        join(Thread.currentThread());
    }

    /**
     * @param thread Thread
     * @throws RuntimeInterruptedException re
     */
    public static void join(Thread thread) throws RuntimeInterruptedException {
        try {
            thread.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeInterruptedException(e.getMessage(), e);
        }
    }

    /**
     * @return java.lang.Thread
     */
    public static Thread getCurrentThread() {
        return Thread.currentThread();
    }

    /**
     * @return String
     */
    public static String getCurrentThreadName() {
        return Thread.currentThread().getName();
    }


    /**
     * @return String
     */
    public static String dumpThreadInfo() {
        return dumpThreadInfo(getCurrentThread());
    }

    /**
     * @param thread Thread
     * @return String
     */
    public static String dumpThreadInfo(@Nonnull final Thread thread) {
        final ThreadMXBean threadMXBean = ManagementFactory.getThreadMXBean();
        final MutableMap<String, String> map = MutableMaps.newUnifiedMap();
        final ThreadInfo threadInfo = threadMXBean.getThreadInfo(thread.threadId());
        map.put("threadId", StringSupport.toString(threadInfo.getThreadId()));
        map.put("threadName", threadInfo.getThreadName());
        map.put("threadState", StringSupport.toString(threadInfo.getThreadState()));
        map.put("priority", StringSupport.toString(threadInfo.getPriority()));
        map.put("lockName", threadInfo.getLockName());
        map.put("lockInfo", StringSupport.toString(threadInfo.getLockInfo()));
        map.put("lockOwnerId", StringSupport.toString(threadInfo.getLockOwnerId()));
        map.put("lockOwnerName", threadInfo.getLockOwnerName());
        map.put("waitedCount", StringSupport.toString(threadInfo.getWaitedCount()));
        map.put("waitedTime", StringSupport.toString(threadInfo.getWaitedTime()));
        map.put("blockedCount", StringSupport.toString(threadInfo.getBlockedCount()));
        map.put("blockedTime", StringSupport.toString(threadInfo.getBlockedTime()));
        return map.toString();
    }

    public static void main(String[] args) {
        System.out.println(getCurrentThreadName());
        startNewThread("Test0", () -> System.out.println(getCurrentThreadName()));
        Sleep.millis(2000);
        startNewThread("Test1", () -> System.out.println(dumpThreadInfo()));
        Sleep.millis(2000);
    }

}
