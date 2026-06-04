package io.flatf.common.number;

import javax.annotation.concurrent.ThreadSafe;
import java.util.concurrent.ThreadLocalRandom;

import static java.lang.Math.abs;

@ThreadSafe
public final class ThreadSafeRandoms {

    /**
     * @return int
     */
    public static int randomInt() {
        return ThreadLocalRandom.current().nextInt();
    }

    /**
     * @return unsigned int
     */
    public static int randomUnsignedInt() {
        int next;
        do {
            next = ThreadLocalRandom.current().nextInt();
        } while (next == Integer.MIN_VALUE);
        return abs(next);
    }

    /**
     * @return long
     */
    public static long randomLong() {
        return ThreadLocalRandom.current().nextLong();
    }

    /**
     * @return unsigned long
     */
    public static long randomUnsignedLong() {
        long next;
        do {
            next = ThreadLocalRandom.current().nextLong();
        } while (next == Long.MIN_VALUE);
        return abs(next);
    }

    /**
     * @return double
     */
    public static double randomDouble() {
        return ThreadLocalRandom.current().nextDouble();
    }

    /**
     * @return unsigned double
     */
    public static double randomUnsignedDouble() {
        return abs(ThreadLocalRandom.current().nextDouble());
    }

}
