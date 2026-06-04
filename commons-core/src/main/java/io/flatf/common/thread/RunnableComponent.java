package io.flatf.common.thread;

import io.flatf.common.annotation.AbstractFunction;
import org.slf4j.Logger;

import javax.annotation.concurrent.ThreadSafe;
import java.io.Serial;
import java.util.concurrent.atomic.AtomicBoolean;

import static io.flatf.common.log4j2.Log4j2LoggerFactory.getLogger;
import static io.flatf.common.thread.Threads.startNewMaxPriorityThread;

@ThreadSafe
public abstract class RunnableComponent {

    private static final Logger log = getLogger(RunnableComponent.class);

    /**
     * Running flag
     */
    protected final AtomicBoolean isRunning = new AtomicBoolean(false);

    /**
     * Closed flag
     */
    protected final AtomicBoolean isClosed = new AtomicBoolean(false);

    /**
     * name
     */
    protected final String name;

    protected RunnableComponent(String name) {
        this.name = name;
    }

    /**
     * @return name String
     */
    public String getName() {
        return name;
    }

    /**
     * @return isRunning boolean
     */
    public boolean isRunning() {
        return isRunning.get();
    }

    /**
     * @return isClosed boolean
     */
    public boolean isClosed() {
        return isClosed.get();
    }

    /**
     * @throws Exception e
     */
    @AbstractFunction
    protected abstract void start0() throws Exception;

    /**
     * 启动组件
     */
    public void start() throws ComponentStartException {
        if (isRunning.compareAndSet(false, true)) {
            try {
                start0();
                log.info("{}::start() call succeeded", name);
            } catch (Exception e) {
                isRunning.set(false);
                log.error("{}::start throw exception -> {}", name, e.getMessage(), e);
                throw new ComponentStartException(name, e.getMessage(), e);
            }
        } else
            log.warn("{}::start() call failed, component already started", name);
    }

    /**
     * @throws Exception e
     */
    @AbstractFunction
    protected abstract void stop0() throws Exception;

    /**
     * 停止运行
     */
    public void stop() throws ComponentStopException {
        isRunning.set(false);
        if (isClosed.compareAndSet(false, true)) {
            try {
                stop0();
            } catch (Exception e) {
                log.error("{}::stop throw exception -> {}", name, e.getMessage(), e);
                throw new ComponentStopException(name, e.getMessage(), e);
            }
        } else
            log.warn("{}::stop() call failed, component already stopped", name);
    }

    /**
     * @param mode StartMode
     */
    public void startWith(final StartMode mode) {
        if (mode.immediately)
            start();
        else if (mode.delayMillis > 0)
            // 使用独立线程启动
            startNewMaxPriorityThread(name + "-worker", () -> {
                // 休眠指定延迟时间
                Sleep.millis(mode.delayMillis);
                start();
            });
        else
            log.warn("{}, Start mode is [Manual], waiting call start...", name);
    }


    /**
     * @author yellow013
     */
    public static final class StartMode {

        public static StartMode auto() {
            return new StartMode(true, 0L);
        }

        public static StartMode manual() {
            return new StartMode(false, 0L);
        }

        public static StartMode delay(long delayMillis) {
            return new StartMode(false, Math.max(delayMillis, 1L));
        }

        private final boolean immediately;

        private final long delayMillis;

        private StartMode(boolean immediately, long delayMillis) {
            this.immediately = immediately;
            this.delayMillis = delayMillis;
        }

        @Override
        public String toString() {
            if (immediately)
                return "Auto";
            else if (delayMillis > 0)
                return "Delay(" + delayMillis + "ms)";
            else
                return "Manual";
        }

    }

    /**
     * @author yellow013
     */
    public static class ComponentStartException extends RuntimeException {

        @Serial
        private static final long serialVersionUID = -5059741051462133930L;

        public ComponentStartException(String componentName, String msg, Throwable cause) {
            super("Component -> [" + componentName + "] start failed, Msg: " + msg, cause);
        }

    }

    /**
     * @author yellow013
     */
    public static class ComponentStopException extends RuntimeException {

        @Serial
        private static final long serialVersionUID = -5298836449881727747L;

        public ComponentStopException(String componentName, String msg, Throwable cause) {
            super("Component -> [" + componentName + "] start failed, Msg: " + msg, cause);
        }

    }

}
