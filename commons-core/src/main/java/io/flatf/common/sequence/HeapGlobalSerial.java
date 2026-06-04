package io.flatf.common.sequence;

import java.util.concurrent.atomic.AtomicLong;

public final class HeapGlobalSerial {

    private static final AtomicLong ATOMIC = new AtomicLong(0L);

    private HeapGlobalSerial() {
    }

    /**
     * @return long
     */
    public static long next() {
        return ATOMIC.incrementAndGet();
    }

}
