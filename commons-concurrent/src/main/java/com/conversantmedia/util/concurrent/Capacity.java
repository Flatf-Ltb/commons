package com.conversantmedia.util.concurrent;

final class Capacity {

    public static final int MAX_POWER2 = (1 << 30);

    /**
     * return the next power of two after @param capacity or capacity if it is
     * already
     */
    public static int getCapacity(int capacity) {
        int c = 1;
        if (capacity >= MAX_POWER2) {
            c = MAX_POWER2;
        } else {
            while (c < capacity)
                c <<= 1;
        }

        if (isPowerOf2(c)) {
            return c;
        } else {
            throw new RuntimeException("Capacity is not a power of 2.");
        }
    }

    /**
     * define power of 2 slightly strangely to include 1, i.e. capacity 1 is allowed
     */
    private static boolean isPowerOf2(final int p) {
        return (p & (p - 1)) == 0;
    }
}
