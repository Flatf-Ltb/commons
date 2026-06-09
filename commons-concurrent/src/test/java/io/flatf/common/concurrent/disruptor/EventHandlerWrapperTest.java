package io.flatf.common.concurrent.disruptor;

import io.flatf.common.concurrent.disruptor.EventExceptionCallback.EventExceptionContext;
import io.flatf.common.concurrent.disruptor.EventExceptionCallback.EventExceptionStage;
import org.junit.Test;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;

public class EventHandlerWrapperTest {

    @Test
    public void sendsFailureToEventExceptionCallback() throws Exception {
        BlockingQueue<EventExceptionContext> contexts = new LinkedBlockingQueue<>();
        IllegalStateException failure = new IllegalStateException("wrapper failed");
        EventHandlerWrapper<ReusableLongEvent> wrapper = EventHandlerWrapper
                .<ReusableLongEvent>builder()
                .whenException(contexts::add)
                .build(event -> {
                    throw failure;
                });

        ReusableLongEvent event = new ReusableLongEvent();
        event.set(42L);

        wrapper.onEvent(event, 9L, true);

        EventExceptionContext context = contexts.take();
        assertEquals("EventHandlerWrapper", context.getEventbusName());
        assertEquals(EventExceptionStage.EVENT, context.getStage());
        assertEquals(9L, context.getSequence());
        assertEquals(ReusableLongEvent.class.getName(), context.getEventType());
        assertEquals("ReusableLongEvent{value=42}", context.getEventSnapshot());
        assertSame(failure, context.getException());
    }
}
