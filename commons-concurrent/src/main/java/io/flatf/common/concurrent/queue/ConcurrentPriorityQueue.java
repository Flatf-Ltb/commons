package io.flatf.common.concurrent.queue;

import io.flatf.common.thread.Sleep;
import io.flatf.common.thread.Threads;
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
        this.normalQueue = new MpmcArrayQueue<>(minPow2(priorityQueueSize));
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


    public static void main(String[] args) {

        ConcurrentPriorityQueue<String> queue = new ConcurrentPriorityQueue<>(0, 256, 1024);

        Threads.startNewMaxPriorityThread("test0", () -> {
            for (int i = 0; i < 50; i++) {
                if (i % 6 == 0) {
                    queue.priorityOffer("TEST[0] priority put : " + i);
                } else {
                    queue.offer("TEST[0] put : " + i);
                }
                Sleep.millis(2);
            }
        });
        Threads.startNewMaxPriorityThread("test1", () -> {
            for (int i = 0; i < 50; i++) {
                if (i % 7 == 0) {
                    queue.priorityOffer("TEST[1] priority put : " + i);
                } else {
                    queue.offer("TEST[1] put : " + i);
                }
                Sleep.millis(3);
            }
        });
        Threads.startNewMaxPriorityThread("test2", () -> {
            do {
                String e = queue.poll();
                if (e != null) {
                    System.out.println(e);
                } else {
                    System.out.println("Grab null sleep 1 ms");
                    Sleep.millis(1);
                }
            } while (true);
        });
    }
}
