package io.flatf.common.concurrent.disruptor;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import static java.util.Objects.requireNonNull;

/**
 * Callback invoked when RingEventbus processing or lifecycle handling fails.
 */
@FunctionalInterface
public interface EventExceptionCallback {

    void onException(EventExceptionContext context);

    /**
     * Exception context exposed by RingEventbus without leaking the Disruptor exception API.
     */
    record EventExceptionContext(
        @Nonnull String eventbusName,
        @Nonnull EventExceptionStage stage,
        long sequence,
        @Nullable String eventType,
        @Nullable String eventSnapshot,
        @Nonnull Throwable exception
    ) {

        private static final long NO_SEQUENCE = -1L;

        public EventExceptionContext {
            requireNonNull(eventbusName, "eventbusName");
            requireNonNull(stage, "stage");
            requireNonNull(exception, "exception");
        }

        public static <E extends ReusableEvent> EventExceptionContext event(
            String eventbusName, Throwable exception, long sequence, E event) {
            return new EventExceptionContext(
                eventbusName, EventExceptionStage.EVENT, sequence,
                event == null ? null : event.getClass().getName(),
                eventSnapshot(event), exception);
        }

        public static EventExceptionContext start(String eventbusName,
                                                  Throwable exception) {
            return new EventExceptionContext(eventbusName, EventExceptionStage.START,
                NO_SEQUENCE, null, null, exception);
        }

        public static EventExceptionContext shutdown(String eventbusName,
                                                     Throwable exception) {
            return new EventExceptionContext(eventbusName, EventExceptionStage.SHUTDOWN,
                NO_SEQUENCE, null, null, exception);
        }

        private static <E extends ReusableEvent> String eventSnapshot(E event) {
            if (event == null)
                return null;
            try {
                return event.snapshot();
            } catch (Throwable throwable) {
                return "<event snapshot failed: " + throwable.getClass().getName() + ": "
                       + throwable.getMessage() + ">";
            }
        }

    }


    enum EventExceptionStage {

        EVENT,

        START,

        SHUTDOWN

    }

}
