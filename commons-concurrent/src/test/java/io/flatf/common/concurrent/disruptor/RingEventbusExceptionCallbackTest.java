package io.flatf.common.concurrent.disruptor;

import com.lmax.disruptor.EventFactory;
import io.flatf.common.concurrent.disruptor.EventExceptionCallback.EventExceptionContext;
import io.flatf.common.concurrent.disruptor.EventExceptionCallback.EventExceptionStage;
import org.junit.Test;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

public class RingEventbusExceptionCallbackTest {

    @Test
    public void invokesExceptionCallbackWhenHandlerFails() throws Exception {
        BlockingQueue<EventExceptionContext> contexts = new LinkedBlockingQueue<>();
        IllegalStateException failure = new IllegalStateException("handler failed");
        RingEventbus<ReusableLongEvent> eventbus = RingEventbus
                .singleProducer(ReusableLongEvent.FACTORY)
                .name("exception-callback")
                .whenException(contexts::add)
                .buildWith((event, sequence, endOfBatch) -> {
                    throw failure;
                });

        try {
            eventbus.publish((event, sequence) -> event.set(10L));

            EventExceptionContext context = contexts.poll(5, TimeUnit.SECONDS);
            assertNotNull(context);
            assertTrue(EventExceptionContext.class.isRecord());
            assertEquals("exception-callback", context.getEventbusName());
            assertEquals(EventExceptionStage.EVENT, context.getStage());
            assertEquals(0L, context.getSequence());
            assertSame(failure, context.getException());
            assertEquals(ReusableLongEvent.class.getName(), context.getEventType());
            assertEquals("ReusableLongEvent{value=10}", context.getEventSnapshot());
        } finally {
            eventbus.stop();
        }
    }

    @Test
    public void capturesReusableEventSnapshotInsteadOfToString() throws Exception {
        BlockingQueue<EventExceptionContext> contexts = new LinkedBlockingQueue<>();
        RingEventbus<SnapshotEvent> eventbus = RingEventbus
                .singleProducer(SnapshotEvent.FACTORY)
                .name("snapshot-callback")
                .whenException(contexts::add)
                .buildWith((event, sequence, endOfBatch) -> {
                    throw new IllegalStateException("snapshot expected");
                });

        try {
            eventbus.publish((event, sequence) -> event.value = 20L);

            EventExceptionContext context = contexts.poll(5, TimeUnit.SECONDS);
            assertNotNull(context);
            assertEquals("snapshot=20", context.eventSnapshot());
        } finally {
            eventbus.stop();
        }
    }

    private static final class SnapshotEvent implements ReusableEvent {

        private static final EventFactory<SnapshotEvent> FACTORY = SnapshotEvent::new;

        private long value = -1L;

        @Override
        public void clear() {
            this.value = -1L;
        }

        @Override
        public String snapshot() {
            return "snapshot=" + value;
        }

        @Override
        public String toString() {
            return "toString=" + value;
        }
    }
}
