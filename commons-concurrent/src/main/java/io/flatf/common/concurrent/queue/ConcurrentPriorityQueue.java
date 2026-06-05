package io.flatf.common.concurrent.queue;

import org.jctools.queues.MessagePassingQueue;
import org.jctools.queues.MpmcArrayQueue;

import javax.annotation.CheckForNull;
import javax.annotation.Nonnull;
import javax.annotation.concurrent.ThreadSafe;

import static io.flatf.common.util.BitOperator.minPow2;

@ThreadSafe
public final class ConcurrentPriorityQueue<E> implements Comparable<ConcurrentPriorityQueue<E>> {

    private final long sequence;

    //private final AtomicInteger counter = new AtomicInteger(0);

    private final MessagePassingQueue<E> priorityQueue;

    private final MessagePassingQueue<E> normalQueue;

    public ConcurrentPriorityQueue(long sequence, int priorityQueueSize, int normalQueueSize) {
        this.sequence = sequence;
        this.priorityQueue = new MpmcArrayQueue<>(minPow2(priorityQueueSize));
        this.normalQueue = new MpmcArrayQueue<>(minPow2(normalQueueSize));
    }

    public long getSequence() {
        return sequence;
    }

    public boolean priorityOffer(@Nonnull E e) {
//        boolean offer = priorityQueue.offer(e);
//        if (offer) {
//            counter.incrementAndGet();
//        }
//        return offer;
        return priorityQueue.offer(e);
    }

    public boolean offer(@Nonnull E e) {
//        boolean offer = normalQueue.offer(e);
//        if (offer) {
//            counter.incrementAndGet();
//        }
//        return offer;
        return normalQueue.offer(e);
    }

    public int size() {
        return priorityQueue.size() + normalQueue.size();
    }

    @CheckForNull
    public E poll() {
        if (!priorityQueue.isEmpty()) {
            return priorityQueue.poll();
        } else if (!normalQueue.isEmpty()) {
            return normalQueue.poll();
        } else {
            return null;
        }
    }

    public boolean isEmpty() {
        return priorityQueue.isEmpty() && normalQueue.isEmpty();
    }

    @Override
    public int compareTo(ConcurrentPriorityQueue<E> o) {
        return Long.compare(sequence, o.sequence);
    }

}
