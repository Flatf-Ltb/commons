package io.flatf.common.concurrent.disruptor;

import static java.util.Objects.requireNonNull;

/**
 * Callback invoked when RingEventbus processing or lifecycle handling fails.
 *
 * @param <E> event type
 */
@FunctionalInterface
public interface EventExceptionCallback<E extends ReusableEvent> {

    void onException(EventExceptionContext<E> context);

    /**
     * Exception context exposed by RingEventbus without leaking the Disruptor exception API.
     *
     * @param <E> event type
     */
    final class EventExceptionContext<E extends ReusableEvent> {

        private static final long NO_SEQUENCE = -1L;

        private final String eventbusName;
        private final EventExceptionStage stage;
        private final long sequence;
        private final E event;
        private final Throwable exception;

        private EventExceptionContext(String eventbusName, EventExceptionStage stage,
                                      long sequence, E event, Throwable exception) {
            this.eventbusName = eventbusName;
            this.stage = requireNonNull(stage, "stage");
            this.sequence = sequence;
            this.event = event;
            this.exception = requireNonNull(exception, "exception");
        }

        public static <E extends ReusableEvent> EventExceptionContext<E> event(
            String eventbusName, Throwable exception, long sequence, E event) {
            return new EventExceptionContext<>(eventbusName, EventExceptionStage.EVENT, sequence, event, exception);
        }

        public static <E extends ReusableEvent> EventExceptionContext<E> start(
            String eventbusName, Throwable exception) {
            return new EventExceptionContext<>(eventbusName, EventExceptionStage.START, NO_SEQUENCE, null, exception);
        }

        public static <E extends ReusableEvent> EventExceptionContext<E> shutdown(
            String eventbusName, Throwable exception) {
            return new EventExceptionContext<>(eventbusName, EventExceptionStage.SHUTDOWN, NO_SEQUENCE, null, exception);
        }

        public String getEventbusName() {
            return eventbusName;
        }

        public EventExceptionStage getStage() {
            return stage;
        }

        public long getSequence() {
            return sequence;
        }

        public E getEvent() {
            return event;
        }

        public Throwable getException() {
            return exception;
        }

    }


    enum EventExceptionStage {

        EVENT,

        START,

        SHUTDOWN

    }

}
