package io.flatf.common.concurrent.queue;

import io.flatf.common.annotation.AbstractFunction;
import io.flatf.common.annotation.thread.SpinLock;
import io.flatf.common.functional.Processor;
import io.flatf.common.log4j2.Log4j2LoggerFactory;
import io.flatf.common.thread.Sleep;
import io.flatf.common.thread.Threads;
import org.jctools.queues.MpscArrayQueue;
import org.jctools.queues.SpscArrayQueue;
import org.slf4j.Logger;

import java.util.Queue;

import static io.flatf.common.thread.Threads.getCurrentThreadName;
import static io.flatf.common.util.StringSupport.requireNonEmptyElse;
import static java.lang.Math.max;

/**
 * @param <E> Single consumer queue use jctools implements
 * @author yellow013
 */
public abstract class JctSingleConsumerQueue<E> extends SingleConsumerQueue<E> implements Runnable {

    /**
     * Logger
     */
    private static final Logger log = Log4j2LoggerFactory.getLogger(JctSingleConsumerQueue.class);

    /**
     * internal queue
     */
    protected final Queue<E> queue;

    /**
     * consumer runnable
     */
    protected final Runnable consumer;

    /**
     * waiting strategy
     */
    private final WaitingStrategy strategy;

    /**
     * sleep millis
     */
    private final long sleepMillis;

    /**
     * Single Producer Single Consumer Queue
     *
     * @return Builder
     */
    public static QueueBuilder spsc() {
        return spsc(null);
    }

    /**
     * Single Producer Single Consumer Queue
     *
     * @param name String
     * @return Builder
     */
    public static QueueBuilder spsc(String name) {
        return new QueueBuilder(QueueType.SPSC, name);
    }

    /**
     * Multiple Producer Single Consumer Queue
     *
     * @return Builder
     */
    public static QueueBuilder mpsc() {
        return mpsc(null);
    }

    /**
     * Multiple Producer Single Consumer Queue
     *
     * @param name String
     * @return Builder
     */
    public static QueueBuilder mpsc(String name) {
        return new QueueBuilder(QueueType.MPSC, name);
    }

    /**
     * @param name        String
     * @param processor   Processor<E>
     * @param capacity    int
     * @param strategy    WaitingStrategy
     * @param sleepMillis long
     */
    protected JctSingleConsumerQueue(String name, Processor<E> processor, int capacity,
                                     WaitingStrategy strategy, long sleepMillis) {
        super(name, processor);
        this.queue = createQueue(capacity);
        this.strategy = strategy;
        this.sleepMillis = sleepMillis;
        this.consumer = () -> {
            try {
                while (isRunning() || !queue.isEmpty()) {
                    @SpinLock
                    E e = queue.poll();
                    if (e != null)
                        processor.process(e);
                    else
                        waiting();
                }
            } catch (Exception e) {
                throw new QueueWorkingException(name + " process thread throw exception", e);
            }
        };
    }

    @AbstractFunction
    protected abstract Queue<E> createQueue(int capacity);

    /**
     *
     */
    private void waiting() {
        if (strategy == WaitingStrategy.Sleep) {
            Sleep.millisIgnored(sleepMillis);
        }
    }

    @Override
    @SpinLock
    public boolean enqueue(E e) {
        if (isClosed.get()) {
            log.error("Queue -> {} : enqueue failure, This queue is closed", name);
            return false;
        }
        if (e == null) {
            log.error("Queue -> {} : enqueue element is null", name);
            return false;
        }
        while (!queue.offer(e))
            waiting();
        return true;
    }

    @Override
    public void run() {
        consumer.run();
    }

    @Override
    protected void start0() {
        Threads.startNewThread(name + "-SubThread", consumer);
        log.info("Queue -> {}, This queue is already working", name);
    }

    @Override
    protected void stop0() {
    }

    @Override
    public boolean isEmpty() {
        return queue.isEmpty();
    }

    /**
     * @param <E> Single Producer Single Consumer Queue
     * @author yellow013
     */
    private static final class JctSPSCQueue<E> extends JctSingleConsumerQueue<E> {

        private JctSPSCQueue(String queueName, int capacity, StartMode mode,
                             WaitingStrategy strategy, long sleepMillis,
                             Processor<E> processor) {
            super(requireNonEmptyElse(queueName, "jct-spsc-q[" + getCurrentThreadName() + "]"),
                    processor, max(capacity, 16), strategy, sleepMillis);
            startWith(mode);
        }

        @Override
        protected SpscArrayQueue<E> createQueue(int capacity) {
            return new SpscArrayQueue<>(capacity);
        }

        @Override
        public QueueType getQueueType() {
            return QueueType.SPSC;
        }

    }

    /**
     * @param <E> Multiple Producer Single Consumer Queue
     * @author yellow013
     */
    private static final class JctMPSCQueue<E> extends JctSingleConsumerQueue<E> {

        private JctMPSCQueue(String queueName, int capacity, StartMode mode,
                             WaitingStrategy strategy, long sleepMillis,
                             Processor<E> processor) {
            super(requireNonEmptyElse(queueName, "jct-mpsc-q[" + getCurrentThreadName() + "]"),
                    processor, max(capacity, 16), strategy, sleepMillis);
            startWith(mode);
        }

        @Override
        protected Queue<E> createQueue(int capacity) {
            return new MpscArrayQueue<>(capacity);
        }

        @Override
        public QueueType getQueueType() {
            return QueueType.MPSC;
        }

    }

    /**
     * Jct QueueBuilder
     *
     * @author yellow013
     */
    public static class QueueBuilder {

        private final QueueType type;
        private final String queueName;
        private StartMode mode = StartMode.auto();
        private WaitingStrategy strategy = WaitingStrategy.Spin;
        private long sleepMillis = 5;
        private int capacity = 32;

        private QueueBuilder(QueueType type, String queueName) {
            this.type = type;
            this.queueName = queueName;
        }

        public QueueBuilder startMode(StartMode mode) {
            this.mode = mode;
            return this;
        }

        public QueueBuilder spinStrategy() {
            this.strategy = WaitingStrategy.Spin;
            return this;
        }

        public QueueBuilder sleepStrategy(long sleepMillis) {
            this.strategy = WaitingStrategy.Sleep;
            if (sleepMillis > 0)
                this.sleepMillis = sleepMillis;
            else
                this.sleepMillis = 1;
            return this;
        }

        public QueueBuilder capacity(int capacity) {
            this.capacity = capacity;
            return this;
        }

        public final <E> JctSingleConsumerQueue<E> process(Processor<E> processor) {
            return switch (type) {
                case SPSC -> new JctSPSCQueue<>(queueName, capacity, mode, strategy, sleepMillis, processor);
                case MPSC -> new JctMPSCQueue<>(queueName, capacity, mode, strategy, sleepMillis, processor);
                default -> throw new IllegalArgumentException("Error QueueType value[ " + type + "]");
            };
        }

    }

}
