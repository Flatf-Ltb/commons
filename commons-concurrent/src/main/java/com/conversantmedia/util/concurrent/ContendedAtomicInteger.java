package com.conversantmedia.util.concurrent;

import java.util.concurrent.atomic.AtomicIntegerArray;

/**
 * Avoid false cache line sharing
 */
final class ContendedAtomicInteger {

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

    public String toString() {
        return Integer.toString(get());
    }
}
