package io.flatf.common.concurrent.queue;

import io.flatf.common.annotation.thread.SpinLock;
import io.flatf.common.collections.Capacity;
import io.flatf.common.thread.Sleep;
import io.flatf.common.thread.Threads;
import io.flatf.common.util.StringSupport;
import org.jctools.queues.MpmcArrayQueue;

import javax.annotation.concurrent.ThreadSafe;

@ThreadSafe
public final class ConcurrentQueue<E> implements MultiConsumerQueue<E> {

    private final MpmcArrayQueue<E> queue;

    private final WaitingStrategy strategy;

    private final String queueName;

    public ConcurrentQueue(String queueName, Capacity capacity, WaitingStrategy strategy) {
        this.queue = new MpmcArrayQueue<>(Math.max(capacity.size(), 64));
        this.queueName = StringSupport.isNullOrEmpty(queueName)
                ? "ConcurrentQueue-" + Threads.getCurrentThreadName()
                : queueName;
        this.strategy = strategy == null ? WaitingStrategy.Sleep : strategy;
    }

    @Override
    @SpinLock
    public boolean enqueue(E e) {
        while (!queue.offer(e))
            waiting();
        return true;
    }

    @Override
    @SpinLock
    public E dequeue() {
        do {
            E e = queue.poll();
            if (e != null)
                return e;
            waiting();
        } while (true);
    }

    @Override
    public String getQueueName() {
        return queueName;
    }

    private void waiting() {
        switch (strategy) {
            case Spin, Blocking -> {
            }
            case Sleep -> Sleep.millis(10);
        }
    }

    @Override
    public boolean isEmpty() {
        return queue.isEmpty();
    }

    @Override
    public QueueType getQueueType() {
        return QueueType.MPMC;
    }

}
