package io.flatf.common.collections.window;

import org.eclipse.collections.api.list.primitive.ImmutableLongList;
import org.eclipse.collections.api.list.primitive.MutableLongList;
import org.eclipse.collections.impl.list.mutable.primitive.LongArrayList;

import javax.annotation.concurrent.NotThreadSafe;
import java.util.LongSummaryStatistics;
import java.util.function.LongConsumer;
import java.util.stream.LongStream;

/**
 * 内部包含Long原始类型的数组, 长度不可变
 *
 * @author yellow013
 */
@NotThreadSafe
public final class LongRingWindow {

    private final int capacity;
    private final MutableLongList list;

    private int tail = -1;
    private int count = 0;

    private boolean isEmpty = true;
    private boolean isFull = false;

    public LongRingWindow(int capacity) {
        this.capacity = capacity;
        this.list = new LongArrayList(capacity);
    }

    public LongRingWindow(int capacity, long[] values) {
        this.capacity = capacity;
        this.list = new LongArrayList(capacity);
        for (long value : values)
            add(value);
    }

    /**
     * @return boolean
     */
    public boolean isEmpty() {
        return isEmpty;
    }

    /**
     * @return boolean
     */
    public boolean isFull() {
        return isFull;
    }

    /**
     * @return long
     */
    public long tail() {
        if (isEmpty)
            return 0L;
        return list.get(tail);
    }

    /**
     * @return long
     */
    public long head() {
        if (isEmpty)
            return 0L;
        if (isFull)
            return list.get(tail + 1 == capacity ? 0 : tail + 1);
        return list.get(tail - count + 1);
    }

    /**
     * @param value long
     * @return LongRingWindow
     */
    public LongRingWindow add(long value) {
        addTail(value);
        return this;
    }

    /**
     * @param value long
     */
    private void addTail(long value) {
        // 如果tail索引自增后等于容量, 重置索引为0
        if (++tail == capacity)
            tail = 0;
        updateStatus();
        list.set(tail, value);
    }

    /**
     * 更新窗口的当前状态
     */
    private void updateStatus() {
        if (!isFull) {
            // 如果窗口容量未满时
            if (++count == capacity)
                isFull = true;
        }
        if (isEmpty) {
            isEmpty = false;
        }
    }

    /**
     * @param consumer LongConsumer
     */
    public void each(LongConsumer consumer) {
        list.each(consumer::accept);
    }

    /**
     * @return int
     */
    public int count() {
        return count;
    }

    /**
     * @return long
     */
    public long sum() {
        return list.sum();
    }

    /**
     * @return long
     */
    public long max() {
        return list.maxIfEmpty(0L);
    }

    /**
     * @return long
     */
    public long min() {
        return list.minIfEmpty(0L);
    }

    /**
     * @return long
     */
    public long average() {
        return (long) list.averageIfEmpty(0L);
    }

    /**
     * @return long
     */
    public long median() {
        return (long) list.medianIfEmpty(0L);
    }

    /**
     * @return LongSummaryStatistics
     */
    public LongSummaryStatistics summaryStatistics() {
        return list.summaryStatistics();
    }

    /**
     * @return LongStream
     */
    public LongStream stream() {
        return list.primitiveStream();
    }

    /**
     * @return ImmutableLongList
     */
    public ImmutableLongList snapshot() {
        return list.toImmutable();
    }

    public static void main(String[] args) {

        LongRingWindow window = new LongRingWindow(10);

        for (int i = 1; i < 30; i++) {
            System.out.println("head -> " + window.head());
            System.out.println("tail -> " + window.tail());
            System.out.println("count -> " + window.count);
            System.out.println("isEmpty == " + window.isEmpty() + " , isFull == " + window.isFull());
            window.add(i);
            System.out.println("head -> " + window.head());
            System.out.println("tail -> " + window.tail());
            System.out.println("count -> " + window.count);
            System.out.println("isEmpty == " + window.isEmpty() + " , isFull == " + window.isFull());
            System.out.print("values -> ");
            window.each(value -> System.out.print(value + " , "));
            System.out.println();
            System.out.println();
        }

    }

}
