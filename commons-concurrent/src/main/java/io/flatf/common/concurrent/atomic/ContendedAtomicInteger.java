package io.flatf.common.concurrent.atomic;

import java.util.concurrent.atomic.AtomicIntegerArray;

/**
 * 缓存行填充的原子 int，规避伪共享(false sharing)。详见 {@link ContendedAtomicLong}。
 *
 * <p>Adapted from Conversant Disruptor (Apache License 2.0)。
 */
public final class ContendedAtomicInteger {

    private static final int CACHE_LINE_INTS = ContendedAtomicLong.CACHE_LINE / Integer.BYTES;

    private final AtomicIntegerArray contendedArray;

    public ContendedAtomicInteger(final int init) {
        contendedArray = new AtomicIntegerArray(2 * CACHE_LINE_INTS);
        set(init);
    }

    public int get() {
        return contendedArray.get(CACHE_LINE_INTS);
    }

    public void set(final int i) {
        contendedArray.set(CACHE_LINE_INTS, i);
    }

    public boolean compareAndSet(final int expect, final int i) {
        return contendedArray.compareAndSet(CACHE_LINE_INTS, expect, i);
    }

    @Override
    public String toString() {
        return Integer.toString(get());
    }
}
