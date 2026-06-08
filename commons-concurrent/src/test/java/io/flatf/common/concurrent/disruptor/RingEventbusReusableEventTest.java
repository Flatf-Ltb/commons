package io.flatf.common.concurrent.disruptor;

import com.lmax.disruptor.EventFactory;
import org.junit.Test;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;

public class RingEventbusReusableEventTest {

    private static final Object NULL_MARKER = new Object();

    @Test
    public void publishClearsReusedEventBeforeTranslatorWritesNextEvent() throws Exception {
        BlockingQueue<Object> markers = new LinkedBlockingQueue<>();
        RingEventbus<ResidueEvent> eventbus = RingEventbus
                .singleProducer(ResidueEvent.FACTORY)
                .name("reusable-event-clear")
                .size(16)
                .withHandler((event, sequence, endOfBatch) ->
                        markers.add(event.marker == null ? NULL_MARKER : event.marker));

        try {
            eventbus.publish((event, sequence) -> {
                event.value = 1L;
                event.marker = "previous";
            });
            eventbus.publish((event, sequence) -> event.value = 2L);

            assertEquals("previous", markers.poll(5, TimeUnit.SECONDS));
            assertSame(NULL_MARKER, markers.poll(5, TimeUnit.SECONDS));
        } finally {
            eventbus.stop();
        }
    }

    private static final class ResidueEvent implements ReusableEvent {

        private static final EventFactory<ResidueEvent> FACTORY = ResidueEvent::new;

        private long value = -1L;
        private String marker;

        @Override
        public void clear() {
            this.value = -1L;
            this.marker = null;
        }
    }
}
