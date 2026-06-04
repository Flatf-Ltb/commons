package io.flatf.common.thread;

import static java.lang.Thread.ofVirtual;

public final class Fibers {

    private Fibers() {
    }

    /**
     * @param task Runnable
     * @return java.lang.Thread
     */
    public static Thread newFiber(Runnable task) {
        return ofVirtual().unstarted(task);
    }

    /**
     * @param name String
     * @param task Runnable
     * @return java.lang.Thread
     */
    public static Thread newFiber(String name, Runnable task) {
        return ofVirtual().name(name).unstarted(task);
    }

    /**
     * @param task Runnable
     * @return java.lang.Thread
     */
    public static Thread startNewFiber(Runnable task) {
        return ofVirtual().start(task);
    }

    /**
     * @param name String
     * @param task Runnable
     * @return java.lang.Thread
     */
    public static Thread startNewFiber(String name, Runnable task) {
        return ofVirtual().name(name).start(task);
    }

}
