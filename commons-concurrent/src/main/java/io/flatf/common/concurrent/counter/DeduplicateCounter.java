package io.flatf.common.concurrent.counter;

import io.flatf.common.annotation.thread.LockHeld;
import io.flatf.common.collections.Capacity;
import io.flatf.common.collections.MutableSets;
import org.eclipse.collections.api.set.ImmutableSet;
import org.eclipse.collections.api.set.MutableSet;

import javax.annotation.concurrent.ThreadSafe;

/**
 * 去除重复数据的计数器, 使用Set去除重复
 *
 * @param <T>
 * @author yellow013
 */
@ThreadSafe
public final class DeduplicateCounter<T extends Comparable<T>> {

    private final MutableSet<T> deRepeatSet = MutableSets.newUnifiedSet(Capacity.HEX_40);

    private volatile int count;
    private final int initCount;

    private volatile boolean isArchived = false;

    public DeduplicateCounter() {
        this(0);
    }

    public DeduplicateCounter(int initCount) {
        this.initCount = initCount;
    }

    /**
     * @param t T
     * @return DeduplicateCounter<T>
     */
    @LockHeld
    public synchronized DeduplicateCounter<T> add(T t) {
        if (deRepeatSet.add(t))
            count = deRepeatSet.size();
        return this;
    }

    /**
     * @param t T
     * @return DeduplicateCounter<T>
     */
    @LockHeld
    public synchronized DeduplicateCounter<T> subtract(T t) {
        if (deRepeatSet.remove(t))
            count = deRepeatSet.size();
        return this;
    }

    /**
     * 清空计数结果
     *
     * @return DeduplicateCounter<T>
     */
    @LockHeld
    public synchronized DeduplicateCounter<T> clear() {
        deRepeatSet.clear();
        count = 0;
        return this;
    }

    /**
     * @return boolean
     */
    public boolean isArchived() {
        return isArchived;
    }

    /**
     * @return ImmutableSet<T>
     */
    public synchronized ImmutableSet<T> getDeRepeatSet() {
        return deRepeatSet.toImmutable();
    }

    /**
     * @return long
     */
    public synchronized long count() {
        return (long) initCount + count;
    }

    /**
     * 清空数据, 但保留计数结果
     */
    public synchronized void archive() {
        this.isArchived = true;
        this.deRepeatSet.clear();
    }

    public static void main(String[] args) {

        DeduplicateCounter<String> deRepeatCounter = new DeduplicateCounter<>(100);
        System.out.println(deRepeatCounter.add("").add("add").add("sub").add("").add("aaa").add("aaa").count());

    }

}
