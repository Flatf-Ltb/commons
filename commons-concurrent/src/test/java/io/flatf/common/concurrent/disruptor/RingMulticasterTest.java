package io.flatf.common.concurrent.disruptor;

import io.flatf.common.concurrent.disruptor.EventPublisher.EventPublisherArg1;
import io.flatf.common.thread.Sleep;
import io.flatf.common.thread.Threads;
import org.junit.Test;

import java.util.concurrent.atomic.LongAdder;

import static io.flatf.common.concurrent.disruptor.SimpleWaitStrategy.YIELDING;
import static org.junit.Assert.assertEquals;

public class RingMulticasterTest {

    @Test
    public void test() {
        LongAdder p0 = new LongAdder();
        LongAdder p1 = new LongAdder();
        LongAdder p2 = new LongAdder();
        RingEventbus<ReusableLongEvent> multicaster = RingEventbus
                .singleProducer(ReusableLongEvent.FACTORY)
                .name("Test-Multicaster").waitStrategy(YIELDING.getInstance()).size(32)
                .buildBroadcastWith((event, sequence, endOfBatch) -> {
                    System.out.println("sequence -> " + sequence + " p0 - " + event.get() + " : " + endOfBatch);
                    p0.increment();
                }, (event, sequence, endOfBatch) -> {
                    System.out.println("sequence -> " + sequence + " p1 - " + event.get() + " : " + endOfBatch);
                    p1.increment();
                }, (event, sequence, endOfBatch) -> {
                    System.out.println("sequence -> " + sequence + " p2 - " + event.get() + " : " + endOfBatch);
                    p2.increment();
                });

        EventPublisherArg1<ReusableLongEvent, Long> pub =
                multicaster.newPublisher((ReusableLongEvent event, long sequence, Long l) -> event.set(l));

        Thread thread = Threads.startNewThread(() -> {
            for (long l = 0L; l < 1000; l++)
                pub.publish(l);
        });

        Sleep.millis(2000);

        System.out.println("p0 - " + p0.intValue());
        assertEquals(1000L, p0.intValue());

        System.out.println("p1 - " + p1.intValue());
        assertEquals(1000L, p1.intValue());

        System.out.println("p2 - " + p2.intValue());
        assertEquals(1000L, p2.intValue());

        multicaster.stop();
        thread.interrupt();
    }

}
