package com.conversantmedia.util.concurrent;

import javax.annotation.Nonnull;
import java.io.Serial;
import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Queue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * Single thread implementation of disruptor
 */
public final class PushPullBlockingQueue<E> extends PushPullConcurrentQueue<E>
        implements Serializable, Iterable<E>,
        Collection<E>, BlockingQueue<E>, Queue<E> {

    @Serial
    private static final long serialVersionUID = 5553142203892135792L;

    // locking objects used for independent locking
    // of not empty, not full status, for java BlockingQueue support
    // if MultithreadConcurrentQueue is used directly, these calls are
    // optimized out and have no impact on timing values
    //
    private final Condition queueNotFullCondition;

    private final Condition queueNotEmptyCondition;

    /**
     * <p>
     * Construct a blocking queue of the given fixed capacity.
     * </p>
     * Note: actual capacity will be the next power of two larger than capacity.
     *
     * @param capacity maximum capacity of this queue
     */

    public PushPullBlockingQueue(final int capacity) {
        this(capacity, SpinPolicy.WAITING);
    }

    /**
     * <p>
     * Construct a blocking queue with a given fixed capacity
     * </p>
     * Note: actual capacity will be the next power of two larger than capacity.
     * <p>
     * Waiting locking may be used in servers that are tuned for it, waiting locking
     * provides a high performance locking implementation which is approximately a
     * factor of 2 improvement in throughput (40M/s for 1-1 thread transfers)
     * <p>
     * However waiting locking is more CPU aggressive and causes servers that may be
     * configured with far too many threads to show very high load averages. This is
     * probably not as detrimental as it is annoying.
     *
     * @param capacity   - the queue capacity, power of two is suggested
     * @param spinPolicy - determine the level of cpu aggressiveness in waiting
     */
    public PushPullBlockingQueue(final int capacity, final SpinPolicy spinPolicy) {
        super(capacity);

        switch (spinPolicy) {
            case BLOCKING:
                queueNotFullCondition = new QueueNotFull();
                queueNotEmptyCondition = new QueueNotEmpty();
                break;
            case SPINNING:
                queueNotFullCondition = new SpinningQueueNotFull();
                queueNotEmptyCondition = new SpinningQueueNotEmpty();
                break;
            case WAITING:
            default:
                queueNotFullCondition = new WaitingQueueNotFull();
                queueNotEmptyCondition = new WaitingQueueNotEmpty();
        }
    }

    /**
     * <p>
     * Construct a blocking queue of the given fixed capacity
     * </p>
     * <p>
     * Note: actual capacity will be the next power of two larger than capacity.
     * </p>
     * The values from the collection, c, are appended to the queue in iteration
     * order. If the number of elements in the collection exceeds the actual
     * capacity, then the additional elements overwrite the previous ones until all
     * elements have been written once.
     *
     * @param capacity maximum capacity of this queue
     * @param c        A collection to use to populate initial values
     */
    public PushPullBlockingQueue(final int capacity, Collection<? extends E> c) {
        this(capacity);
        for (final E e : c) {
            offer(e);
        }
    }

    @Override
    public boolean offer(@Nonnull E e) {
        try {
            return super.offer(e);
        } finally {
            queueNotEmptyCondition.signal();
        }
    }

    @Override
    public E poll() {
        final E e = super.poll();
        // not full now
        queueNotFullCondition.signal();
        return e;
    }

    @Override
    public int remove(final E[] e) {
        final int n = super.remove(e);
        // queue can not be full
        queueNotFullCondition.signal();
        return n;
    }

    @Override
    public E remove() {
        return poll();
    }

    @Override
    public E element() {
        final E val = peek();
        if (val != null)
            return val;
        throw new NoSuchElementException("No element found.");
    }

    @Override
    public void put(@Nonnull E e) throws InterruptedException {
        // add object, wait for space to become available
        while (!offer(e)) {
            if (Thread.currentThread().isInterrupted()) {
                throw new InterruptedException();
            }
            queueNotFullCondition.await();
        }
    }

    @Override
    public boolean offer(E e, long timeout, @Nonnull TimeUnit unit) throws InterruptedException {
        for (; ; ) {
            if (offer(e)) {
                return true;
            } else {

                // wait for available capacity and try again
                if (!Condition.waitStatus(timeout, unit, queueNotFullCondition))
                    return false;
            }
        }
    }

    @Nonnull
    @Override
    public E take() throws InterruptedException {
        for (; ; ) {
            E pollObj = poll();
            if (pollObj != null) {
                return pollObj;
            }
            if (Thread.currentThread().isInterrupted()) {
                throw new InterruptedException();
            }
            queueNotEmptyCondition.await();
        }
    }

    @Override
    public E poll(long timeout, @Nonnull TimeUnit unit) throws InterruptedException {
        for (; ; ) {
            E pollObj = poll();
            if (pollObj != null) {
                return pollObj;
            } else {
                // wait for the queue to have at least one element or time out
                if (!Condition.waitStatus(timeout, unit, queueNotEmptyCondition))
                    return null;
            }
        }
    }

    @Override
    public void clear() {
        super.clear();
        queueNotFullCondition.signal();
    }

    @Override
    public int remainingCapacity() {
        return size - size();
    }

    @Override
    public int drainTo(@Nonnull Collection<? super E> c) {
        return drainTo(c, size());
    }

    @Override
    // drain the whole queue at once
    public int drainTo(@Nonnull Collection<? super E> c, int maxElements) {

        // required by spec
        if (this == c)
            throw new IllegalArgumentException("Can not drain to self.");

        /*
         * This employs a "batch" mechanism to load all objects from the ring in a
         * single update. This could have significant cost savings in comparison with
         * poll, however it does require a memory allocation.
         */

        // save out the values - java should allocate this object on the stack
        @SuppressWarnings("unchecked") final E[] pollObj = (E[]) new Object[Math.min(size(), maxElements)];

        final int nEle = remove(pollObj);
        int nRead = 0;

        for (int i = 0; i < nEle; i++) {
            if (c.add(pollObj[i]))
                nRead++;
            // else invalid state -- object is lost -- see javadoc for drainTo
        }

        // only return the number that was actually added to the collection
        return nRead;
    }

    @Nonnull
    @Override
    public Object[] toArray() {
        @SuppressWarnings("unchecked") final E[] e = (E[]) new Object[size()];
        toArray(e);

        return e;

    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> T[] toArray(@Nonnull T[] a) {
        remove((E[]) a);
        return a;
    }

    @Override
    public boolean add(@Nonnull E e) {
        if (offer(e))
            return true;
        throw new IllegalStateException("queue is full");
    }

    @Override
    public boolean remove(Object o) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        for (final Object o : c) {
            if (!contains(o))
                return false;
        }
        return true;
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        boolean rc = false;
        for (final E e : c) {
            if (offer(e)) {
                rc = true;
            }
        }
        return rc;
    }

    @Override
    public boolean removeAll(@Nonnull Collection<?> c) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean retainAll(@Nonnull Collection<?> c) {
        throw new UnsupportedOperationException();
    }

    @Nonnull
    @Override
    public Iterator<E> iterator() {
        return new RingIter();
    }

    private boolean isFull() {
        final long queueStart = tail.sum() - size;
        return head.sum() == queueStart;
    }

    private final class RingIter implements Iterator<E> {
        int dx = 0;

        E lastObj = null;

        private RingIter() {

        }

        @Override
        public boolean hasNext() {
            return dx < size();
        }

        @Override
        public E next() {
            final long pollPos = head.sum();
            final int slot = (int) ((pollPos + dx++) & mask);
            lastObj = buffer[slot];
            return lastObj;
        }

        @Override
        public void remove() {
            PushPullBlockingQueue.this.remove(lastObj);
        }
    }

    // condition used for signaling queue is full
    private final class QueueNotFull extends AbstractCondition {

        @Override
        // @return boolean - true if the queue is full
        public boolean test() {
            return isFull();
        }
    }

    // condition used for signaling queue is empty
    private final class QueueNotEmpty extends AbstractCondition {
        @Override
        // @return boolean - true if the queue is empty
        public boolean test() {
            return isEmpty();
        }
    }

    // condition used for signaling queue is full
    private final class WaitingQueueNotFull extends AbstractWaitingCondition {

        @Override
        // @return boolean - true if the queue is full
        public boolean test() {
            return isFull();
        }
    }

    // condition used for signaling queue is empty
    private final class WaitingQueueNotEmpty extends AbstractWaitingCondition {
        @Override
        // @return boolean - true if the queue is empty
        public boolean test() {
            return isEmpty();
        }
    }

    // condition used for signaling queue is full
    private final class SpinningQueueNotFull extends AbstractSpinningCondition {

        @Override
        // @return boolean - true if the queue is full
        public boolean test() {
            return isFull();
        }
    }

    // condition used for signaling queue is empty
    private final class SpinningQueueNotEmpty extends AbstractSpinningCondition {
        @Override
        // @return boolean - true if the queue is empty
        public boolean test() {
            return isEmpty();
        }
    }

}
