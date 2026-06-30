package io.flatf.common.concurrent.atomic;

import java.util.concurrent.atomic.AtomicLongArray;

/**
 * 缓存行填充的原子 long，规避伪共享(false sharing)，适合高频更新的热点计数器/序列号。
 *
 * <p>通过把真实值放在一个跨多个缓存行的数组中间，使其独占缓存行，避免与相邻字段在同一行被并发写而互相失效。
 * 不依赖 {@code jdk.internal.vm.annotation.Contended}（无需 {@code --add-exports}）。
 *
 * <p>Adapted from Conversant Disruptor (Apache License 2.0)。
 */
public final class ContendedAtomicLong {

    static final int CACHE_LINE = Integer.getInteger("Intel.CacheLineSize", 64); // bytes

    private static final int CACHE_LINE_LONGS = CACHE_LINE / Long.BYTES;

    private final AtomicLongArray contendedArray;

    public ContendedAtomicLong(final long init) {
        contendedArray = new AtomicLongArray(2 * CACHE_LINE_LONGS);
        set(init);
    }

    public void set(final long l) {
        contendedArray.set(CACHE_LINE_LONGS, l);
    }

    public long get() {
        return contendedArray.get(CACHE_LINE_LONGS);
    }

    public boolean compareAndSet(final long expect, final long l) {
        return contendedArray.compareAndSet(CACHE_LINE_LONGS, expect, l);
    }

    @Override
    public String toString() {
        return Long.toString(get());
    }
}
