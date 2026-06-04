package io.flatf.common.thread;

import io.flatf.common.collections.MutableLists;
import org.eclipse.collections.api.list.MutableList;

import java.io.Closeable;
import java.io.IOException;
import java.util.concurrent.ThreadPoolExecutor;

import static io.flatf.common.number.ThreadSafeRandoms.randomUnsignedInt;
import static java.lang.Runtime.getRuntime;

public final class ShutdownHooks {

    private static final ShutdownHooks INSTANCE = new ShutdownHooks();

    private ShutdownHooks() {
        getRuntime().addShutdownHook(
                new Thread(this::executeShutdownHook, "ShutdownHooksQueueHandleThread"));
    }

    private final MutableList<Runnable> shutdownTasks = MutableLists.newFastList(64);

    private void executeShutdownHook() {
        System.out.println("start execution all shutdown hook");
        try (ThreadPoolExecutor executor = CommonThreadPool.newBuilder().build()) {
            for (Runnable shutdownTask : shutdownTasks)
                executor.execute(shutdownTask);
            executor.shutdown();
            while (!executor.isTerminated())
                Sleep.millisIgnored(100);
            System.out.println("all shutdown hook execution completed");
        }
    }

    /**
     * @param task Runnable
     */
    public static synchronized void addSubTask(Runnable task) {
        INSTANCE.shutdownTasks.add(task);
    }

    /**
     * @param hook Runnable
     * @return Thread
     */
    public static Thread addShutdownHook(Runnable hook) {
        return addShutdownHook(
                "ShutdownHooksSubThread-" + randomUnsignedInt(), hook);
    }

    /**
     * @param threadName String
     * @param hook       Runnable
     * @return Thread
     */
    public static Thread addShutdownHook(String threadName, Runnable hook) {
        Thread thread = Threads.newThread(threadName, hook);
        getRuntime().addShutdownHook(thread);
        return thread;
    }

    /**
     * @param closeable Closeable
     */
    public static void closeResourcesWhenShutdown(Closeable closeable) {
        addSubTask(() -> {
            try {
                closeable.close();
            } catch (IOException ignored) {
            }
        });
    }

    public static void main(String[] args) {

        ShutdownHooks.addSubTask(new Thread(() -> System.out.println("关闭钩子1")));
        ShutdownHooks.addSubTask(new Thread(() -> System.out.println("关闭钩子2")));
        ShutdownHooks.addSubTask(new Thread(() -> System.out.println("关闭钩子3")));
        ShutdownHooks.addSubTask(new Thread(() -> System.out.println("关闭钩子4")));
        ShutdownHooks.addSubTask(new Thread(() -> System.out.println("关闭钩子5")));
        ShutdownHooks.addSubTask(new Thread(() -> System.out.println("关闭钩子6")));
        ShutdownHooks.addSubTask(new Thread(() -> System.out.println("关闭钩子7")));
        ShutdownHooks.addSubTask(new Thread(() -> System.out.println("关闭钩子8")));

    }

}
