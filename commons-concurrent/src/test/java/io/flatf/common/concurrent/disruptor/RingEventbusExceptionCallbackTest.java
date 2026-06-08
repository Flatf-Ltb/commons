package io.flatf.common.concurrent.disruptor;

import io.flatf.common.concurrent.disruptor.EventExceptionCallback.EventExceptionContext;
import io.flatf.common.concurrent.disruptor.EventExceptionCallback.EventExceptionStage;
import org.junit.Test;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;

public class RingEventbusExceptionCallbackTest {

    @Test
    public void invokesExceptionCallbackWhenHandlerFails() throws Exception {
        BlockingQueue<EventExceptionContext<ReusableLongEvent>> contexts = new LinkedBlockingQueue<>();
        IllegalStateException failure = new IllegalStateException("handler failed");
        RingEventbus<ReusableLongEvent> eventbus = RingEventbus
                .singleProducer(ReusableLongEvent.FACTORY)
                .name("exception-callback")
                .onException(contexts::add)
                .withHandler((event, sequence, endOfBatch) -> {
                    throw failure;
                });

        try {
            eventbus.publish((event, sequence) -> event.set(10L));

            EventExceptionContext<ReusableLongEvent> context = contexts.poll(5, TimeUnit.SECONDS);
            assertNotNull(context);
            assertEquals("exception-callback", context.getEventbusName());
            assertEquals(EventExceptionStage.EVENT, context.getStage());
            assertEquals(0L, context.getSequence());
            assertSame(failure, context.getException());
            assertEquals(10L, context.getEvent().get());
        } finally {
            eventbus.stop();
        }
    }
}
