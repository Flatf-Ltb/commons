package io.flatf.common.concurrent.disruptor;

import io.flatf.common.concurrent.disruptor.EventPublisher.EventPublisherArg1;
import org.junit.Test;

import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertTrue;

public class RingEventbusSingleProducerVerificationTest {

    @Test
    public void enabledSingleProducerVerificationRejectsDirectPublishFromAnotherThread() throws Exception {
        RingEventbus<ReusableLongEvent> eventbus = RingEventbus
                .singleProducer(ReusableLongEvent.FACTORY)
                .name("single-producer-direct-verification")
                .verifySingleProducer()
                .buildWith((event, sequence, endOfBatch) -> {
                });

        try {
            eventbus.publish((event, sequence) -> event.set(1L));

            Throwable thrown = publishFromOtherThread(eventbus);

            assertTrue(thrown instanceof IllegalStateException);
            assertTrue(thrown.getMessage().contains("singleProducer"));
        } finally {
            eventbus.stop();
        }
    }

    @Test
    public void systemPropertyEnablesSingleProducerVerification() throws Exception {
        withProperty(RingEventbus.VERIFY_SINGLE_PRODUCER_PROPERTY, "true", () -> {
            RingEventbus<ReusableLongEvent> eventbus = RingEventbus
                    .singleProducer(ReusableLongEvent.FACTORY)
                    .name("single-producer-system-property-verification")
                    .buildWith((event, sequence, endOfBatch) -> {
                    });

            try {
                eventbus.publish((event, sequence) -> event.set(1L));

                Throwable thrown = publishFromOtherThread(eventbus);

                assertTrue(thrown instanceof IllegalStateException);
                assertTrue(thrown.getMessage().contains("singleProducer"));
            } finally {
                eventbus.stop();
            }
        });
    }

    @Test
    public void enabledSingleProducerVerificationRejectsPublisherFromAnotherThread() throws Exception {
        RingEventbus<ReusableLongEvent> eventbus = RingEventbus
                .singleProducer(ReusableLongEvent.FACTORY)
                .name("single-producer-publisher-verification")
                .verifySingleProducer()
                .buildWith((event, sequence, endOfBatch) -> {
                });
        EventPublisherArg1<ReusableLongEvent, Long> publisher =
                eventbus.newPublisher((event, sequence, value) -> event.set(value));

        try {
            publisher.publish(1L);

            Throwable thrown = publishFromOtherThread(publisher);

            assertTrue(thrown instanceof IllegalStateException);
            assertTrue(thrown.getMessage().contains("singleProducer"));
        } finally {
            eventbus.stop();
        }
    }

    @Test
    public void multiProducerIgnoresSingleProducerVerification() throws Exception {
        RingEventbus<ReusableLongEvent> eventbus = RingEventbus
                .multiProducer(ReusableLongEvent.FACTORY)
                .name("multi-producer-with-verification")
                .verifySingleProducer()
                .buildWith((event, sequence, endOfBatch) -> {
                });

        try {
            eventbus.publish((event, sequence) -> event.set(1L));

            Throwable thrown = publishFromOtherThread(eventbus);

            assertTrue("multiProducer should not reject publisher thread changes", thrown == null);
        } finally {
            eventbus.stop();
        }
    }

    private static Throwable publishFromOtherThread(RingEventbus<ReusableLongEvent> eventbus) throws Exception {
        return callFromOtherThread(() -> eventbus.publish((event, sequence) -> event.set(2L)));
    }

    private static Throwable publishFromOtherThread(EventPublisherArg1<ReusableLongEvent, Long> publisher)
            throws Exception {
        return callFromOtherThread(() -> publisher.publish(2L));
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
        Thread thread = new Thread(task, "other-publisher");
        thread.start();
        return task.get(5, TimeUnit.SECONDS);
    }

    private static void withProperty(String key, String value, ThrowingRunnable runnable) throws Exception {
        String previous = System.getProperty(key);
        System.setProperty(key, value);
        try {
            runnable.run();
        } finally {
            if (previous == null)
                System.clearProperty(key);
            else
                System.setProperty(key, previous);
        }
    }

    @FunctionalInterface
    private interface ThrowingRunnable {
        void run() throws Exception;
    }
}
