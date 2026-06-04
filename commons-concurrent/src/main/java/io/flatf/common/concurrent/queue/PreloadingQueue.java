package io.flatf.common.concurrent.queue;

import io.flatf.common.annotation.thread.LockHeld;
import io.flatf.common.collections.queue.EventContainer;
import io.flatf.common.log4j2.Log4j2LoggerFactory;
import org.slf4j.Logger;

import javax.annotation.Nullable;
import javax.annotation.concurrent.ThreadSafe;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

@ThreadSafe
public class PreloadingQueue<E> implements MultiConsumerQueue<E> {

    private static final Logger log = Log4j2LoggerFactory.getLogger(PreloadingQueue.class);

    private final EventContainer<E>[] containers;

    private final int size;
    private final AtomicInteger count = new AtomicInteger();

    private volatile int readOffset;
    private volatile int writeOffset;

    private final ReentrantLock lock;

    private final Condition notEmpty;
    private final Condition notFull;

    @SuppressWarnings("unchecked")
    public PreloadingQueue(int size) {
        if (size <= 0) {
            throw new IllegalArgumentException("size is too big.");
        }
        this.containers = new EventContainer[size];
        for (int i = 0; i < size; i++)
            containers[i] = new EventContainer<>();
        this.size = size;
        this.lock = new ReentrantLock();
        this.notEmpty = lock.newCondition();
        this.notFull = lock.newCondition();
    }

    @Override
    @LockHeld
    public boolean enqueue(E e) {
        try {
            lock.lockInterruptibly();
            while (count.get() == size)
                notFull.await();
            containers[writeOffset].loading(e);
            if (++writeOffset == size)
                writeOffset = 0;
            count.incrementAndGet();
            notEmpty.signal();
            return true;
        } catch (InterruptedException ie) {
            log.error("PreloadingQueue.enqueue() -> {}", ie.getMessage());
            return false;
        } finally {
            lock.unlock();
        }
    }

    @Override
    @LockHeld
    @Nullable
    public E dequeue() {
        try {
            lock.lockInterruptibly();
            while (count.get() == 0)
                notEmpty.await();
            E e = containers[readOffset].unloading();
            if (++readOffset == size)
                readOffset = 0;
            count.decrementAndGet();
            notFull.signal();
            return e;
        } catch (InterruptedException ie) {
            log.error("PreloadingQueue.dequeue() : {}", ie.getMessage());
            return null;
        } finally {
            lock.unlock();
        }
    }

    @Override
    public String getQueueName() {
        return "PreloadingQueue";
    }

    @Override
    public boolean isEmpty() {
        return count.get() == 0;
    }

    @Override
    public QueueType getQueueType() {
        return QueueType.MPMC;
    }

}
