package io.flatf.common.sequence;

import io.flatf.common.thread.Threads;

import javax.annotation.concurrent.ThreadSafe;
import java.security.SecureRandom;
import java.util.concurrent.atomic.AtomicLong;

@ThreadSafe
public final class LocalSerial {

    private final AtomicLong serial;

    private LocalSerial(long initValue) {
        this.serial = new AtomicLong(initValue);
    }

    /**
     * @param initValue long
     * @return LocalSerial
     */
    public static LocalSerial newInstance(long initValue) {
        return new LocalSerial(initValue);
    }

    /**
     * @return LocalSerial
     */
    public static LocalSerial newInstance() {
        return new LocalSerial(0L);
    }

    /**
     * @return long
     */
    public long incrementAndGet() {
        return serial.incrementAndGet();
    }

    /**
     * @return long
     */
    public long getAndIncrement() {
        return serial.getAndIncrement();
    }

    public static void main(String[] args) {

        AtomicLong atomicLong = new AtomicLong(50);

        SecureRandom random = new SecureRandom();

        Threads.startNewThread(() -> {
            if (atomicLong.get() < 0) {
            } else if (atomicLong.get() > 100) {
            } else {
                if (random.nextLong() % 2 == 0) {
                    atomicLong.getAndAdd(1);
                } else {
                    atomicLong.getAndAdd(-1);
                }
            }
        });

    }

}
