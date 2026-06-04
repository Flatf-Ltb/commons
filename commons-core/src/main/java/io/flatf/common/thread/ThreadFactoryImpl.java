package io.flatf.common.thread;

import javax.annotation.Nonnull;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Supplier;

import static io.flatf.common.datetime.pattern.impl.DateTimePattern.YYYYMMDD_L_HHMMSSSSS;
import static io.flatf.common.thread.ThreadPriority.NORM;
import static io.flatf.common.util.StringSupport.requireNonEmptyElse;
import static java.time.LocalDateTime.now;

public final class ThreadFactoryImpl implements ThreadFactory {

    private final String name;

    private final Supplier<String> incr;

    private final boolean isVirtual;

    private final boolean isDaemon;

    private final int priority;

    private ThreadFactoryImpl(String name, boolean isVirtual,
                              boolean isDaemon, int priority,
                              Supplier<String> incr) {
        this.name = requireNonEmptyElse(name,
                "ThreadFactoryImpl-[" + YYYYMMDD_L_HHMMSSSSS.fmt(now()) + "]");
        this.isVirtual = isVirtual;
        this.isDaemon = isDaemon;
        this.priority = priority;
        this.incr = incr;
    }

    @Override
    public Thread newThread(@Nonnull Runnable runnable) {
        return isVirtual
                ?
                Thread.ofVirtual()
                        .name(name + "-" + incr.get())
                        .unstarted(runnable)
                :
                Thread.ofPlatform()
                        .name(name + "-" + incr.get())
                        .priority(priority)
                        .daemon(isDaemon)
                        .unstarted(runnable);
    }

    public static Builder ofVirtual() {
        return ofVirtual("");
    }

    public static Builder ofVirtual(String name) {
        return new Builder(name, true);
    }

    public static Builder ofPlatform() {
        return ofPlatform("");
    }

    public static Builder ofPlatform(String name) {
        return new Builder(name, false);
    }

    public static final class Builder {

        private final String name;

        private final boolean isVirtual;

        private boolean isDaemon = false;

        private ThreadPriority priority = NORM;

        private Supplier<String> incr = new Supplier<>() {

            private final AtomicInteger incr = new AtomicInteger(0);

            @Override
            public String get() {
                return Integer.toString(incr.incrementAndGet());
            }
        };

        private Builder(String name, boolean isVirtual) {
            this.name = name;
            this.isVirtual = isVirtual;
        }

        public Builder daemon() {
            this.isDaemon = true;
            return this;
        }

        public Builder priority(@Nonnull ThreadPriority priority) {
            this.priority = priority;
            return this;
        }

        public Builder incrementer(@Nonnull Supplier<String> incr) {
            this.incr = incr;
            return this;
        }

        public ThreadFactory build() {
            return new ThreadFactoryImpl(name, isVirtual, isDaemon, priority.getValue(), incr);
        }

    }

}
