package com.lmax.disruptor;

import com.lmax.disruptor.EventPoller.PollState;
import org.junit.Test;

import java.util.ArrayList;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class EventPollerTest {
    @Test
    public void shouldPollForEvents() throws Exception {

        final Sequence gatingSequence = new Sequence();
        final SingleProducerSequencer sequencer = new SingleProducerSequencer(16, new BusySpinWaitStrategy());

        final EventPoller.Handler<Object> handler = (Object event, long sequence, boolean endOfBatch) -> false;

        final Object[] data = new Object[16];
        final DataProvider<Object> provider = (long sequence) -> data[(int) sequence];

        final EventPoller<Object> poller = sequencer.newPoller(provider, gatingSequence);
        final Object event = new Object();
        data[0] = event;

        assertThat(poller.poll(handler), is(PollState.IDLE));

        // Publish Event.
        sequencer.publish(sequencer.next());
        assertThat(poller.poll(handler), is(PollState.GATING));

        gatingSequence.incrementAndGet();
        assertThat(poller.poll(handler), is(PollState.PROCESSING));
    }

    @Test
    public void shouldSuccessfullyPollWhenBufferIsFull() throws Exception {

        final ArrayList<byte[]> events = new ArrayList<>();

        final EventPoller.Handler<byte[]> handler = (byte[] event, long sequence, boolean endOfBatch) -> {
            events.add(event);
            return !endOfBatch;
        };

        EventFactory<byte[]> factory = () -> new byte[1];

        final RingBuffer<byte[]> ringBuffer = RingBuffer.createMultiProducer(factory, 4, new SleepingWaitStrategy());

        final EventPoller<byte[]> poller = ringBuffer.newPoller();
        ringBuffer.addGatingSequences(poller.getSequence());

        int count = 4;

        for (byte i = 1; i <= count; ++i) {
            long next = ringBuffer.next();
            ringBuffer.get(next)[0] = i;
            ringBuffer.publish(next);
        }

        // think of another thread
        poller.poll(handler);

        assertThat(events.size(), is(4));
    }
}
