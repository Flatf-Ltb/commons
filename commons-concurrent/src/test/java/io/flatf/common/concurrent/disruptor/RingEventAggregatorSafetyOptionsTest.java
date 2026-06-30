package io.flatf.common.concurrent.disruptor;

import io.flatf.common.concurrent.disruptor.EventExceptionHandler.EventExceptionContext;
import org.junit.Test;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.FutureTask;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

public class RingEventAggregatorSafetyOptionsTest {

    @Test
    public void aggregatorBuilderPassesExceptionCallbackToEventbus() throws Exception {
        BlockingQueue<EventExceptionContext> contexts = new LinkedBlockingQueue<>();
        IllegalStateException failure = new IllegalStateException("aggregator failed");
        ThrowingAggregator aggregator = new ThrowingAggregator(
            RingEventAggregator.<ReusableLongEvent>singleProducer()
                .name("aggregator-exception")
                .whenException(contexts::add),
            failure);

        try {
            aggregator.publish(7L);

            EventExceptionContext context = contexts.poll(5, TimeUnit.SECONDS);
            assertNotNull(context);
            assertEquals("aggregator-exception", context.eventbusName());
            assertSame(failure, context.exception());
            assertEquals("ReusableLongEvent{value=7}", context.eventSnapshot());
        } finally {
            aggregator.stop();
        }
    }

    @Test
    public void aggregatorBuilderPassesSingleProducerVerificationToEventbus() throws Exception {
        CapturingAggregator aggregator = new CapturingAggregator(
            RingEventAggregator.<ReusableLongEvent>singleProducer()
                .name("aggregator-single-producer")
                .assertSingleProducer());

        try {
            aggregator.publish(1L);

            Throwable thrown = callFromOtherThread(() -> aggregator.publish(2L));

            assertTrue(thrown instanceof IllegalStateException);
            assertTrue(thrown.getMessage().contains("singleProducer"));
        } finally {
            aggregator.stop();
        }
    }

    private static Throwable callFromOtherThread(Runnable publish) throws Exception {
        FutureTask<Throwable> task = new FutureTask<>(() -> {
            try {
                publish.run();
                return null;
            } catch (Throwable throwable) {
                return throwable;
            }
        });
        Thread thread = new Thread(task, "other-aggregator-publisher");
        thread.start();
        return task.get(5, TimeUnit.SECONDS);
    }

    private static class CapturingAggregator extends RingEventAggregator<ReusableLongEvent> {

        CapturingAggregator(Builder<ReusableLongEvent> builder) {
            super(builder, ReusableLongEvent.FACTORY);
        }

        void publish(long value) {
            eventbus.publish((event, sequence) -> event.set(value));
        }

        void stop() {
            eventbus.stop();
        }

        @Override
        public void onEvent(ReusableLongEvent event, long sequence, boolean endOfBatch) {
        }
    }

    private static final class ThrowingAggregator extends CapturingAggregator {

        private final RuntimeException failure;

        ThrowingAggregator(Builder<ReusableLongEvent> builder, RuntimeException failure) {
            super(builder);
            this.failure = failure;
        }

        @Override
        public void onEvent(ReusableLongEvent event, long sequence, boolean endOfBatch) {
            throw failure;
        }
    }
}
