package io.flatf.common.concurrent.disruptor;

import com.lmax.disruptor.BlockingWaitStrategy;
import com.lmax.disruptor.BusySpinWaitStrategy;
import com.lmax.disruptor.EventProcessor;
import com.lmax.disruptor.LiteBlockingWaitStrategy;
import com.lmax.disruptor.SleepingWaitStrategy;
import com.lmax.disruptor.WaitStrategy;
import com.lmax.disruptor.YieldingWaitStrategy;

import java.util.function.Supplier;

/**
 *
 */
public enum SimpleWaitStrategy {

    /**
     * Blocking strategy that uses a lock and condition variable for
     * {@link EventProcessor}s waiting on a barrier.
     *
     * <p>This strategy can be used when throughput and low latency are not as important as CPU resource.
     */
    BLOCKING(BlockingWaitStrategy::new),

    /**
     * Busy Spin strategy that uses a busy spin loop for
     * {@link com.lmax.disruptor.EventProcessor}s waiting on a barrier.
     *
     * <p>This strategy will use CPU resource to avoid syscalls which can introduce latency jitter.
     * <p>It is best used when threads can be bound to specific CPU cores.
     */
    BUSY_SPIN(BusySpinWaitStrategy::new),

    /**
     * Variation of the {@link BlockingWaitStrategy} that attempts to elide conditional wake-ups when
     * the lock is uncontended.  Shows performance improvements on micro benchmarks.
     *
     * <p>However, this wait strategy should be considered experimental as
     * I have not fully proved the correctness of the lock elision code.
     */
    LITE_BLOCKING(LiteBlockingWaitStrategy::new),


    /**
     * Sleeping strategy that initially spins, then uses a Thread.yield(), and
     * eventually sleep (<code>LockSupport.parkNanos(n)</code>) for the minimum
     * number of nanos the OS and JVM will allow while the
     * {@link com.lmax.disruptor.EventProcessor}s are waiting on a barrier.
     *
     * <p>This strategy is a good compromise between performance and CPU resource.
     * Latency spikes can occur after quiet periods.
     * <p>It will also reduce the impact on the producing thread as it will
     * not need to signal any conditional variables to wake up the event handling thread.
     */
    SLEEPING(SleepingWaitStrategy::new),

    /**
     * Yielding strategy that uses a Thread.yield() for
     * {@link com.lmax.disruptor.EventProcessor}s waiting on a barrier after initially spinning.
     *
     * <p>This strategy will use 100% CPU, but will more readily give up the
     * CPU than a busy spin strategy if other threads require CPU resource.
     */
    YIELDING(YieldingWaitStrategy::new),

    ;

    private final Supplier<WaitStrategy> supplier;

    SimpleWaitStrategy(Supplier<WaitStrategy> supplier) {
        this.supplier = supplier;
    }

    public WaitStrategy getInstance() {
        return supplier.get();
    }

}
