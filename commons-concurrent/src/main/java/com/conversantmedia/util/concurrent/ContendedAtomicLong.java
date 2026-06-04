package com.conversantmedia.util.concurrent;

import java.util.concurrent.atomic.AtomicLongArray;

/**
 * Avoid false cache line sharing
 */
final class ContendedAtomicLong {

    static final int CACHE_LINE = Integer.getInteger("Intel.CacheLineSize", 64); // bytes

    private static final int CACHE_LINE_LONGS = CACHE_LINE / Long.BYTES;

    private final AtomicLongArray contendedArray;

    ContendedAtomicLong(final long init) {
        contendedArray = new AtomicLongArray(2 * CACHE_LINE_LONGS);

        set(init);
    }

    void set(final long l) {
        contendedArray.set(CACHE_LINE_LONGS, l);
    }

    long get() {
        return contendedArray.get(CACHE_LINE_LONGS);
    }

    public String toString() {
        return Long.toString(get());
    }

    public boolean compareAndSet(final long expect, final long l) {
        return contendedArray.compareAndSet(CACHE_LINE_LONGS, expect, l);
    }
}
