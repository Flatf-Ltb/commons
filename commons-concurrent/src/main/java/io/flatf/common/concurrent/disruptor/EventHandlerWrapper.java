package io.flatf.common.concurrent.disruptor;

import com.lmax.disruptor.EventHandler;
import io.flatf.common.concurrent.disruptor.EventExceptionCallback.EventExceptionContext;
import io.flatf.common.functional.Processor;
import io.flatf.common.log4j2.Log4j2LoggerFactory;
import org.slf4j.Logger;

import static java.util.Objects.requireNonNull;
import static java.util.Objects.requireNonNullElse;

/**
 * 事件处理器的包装
 *
 * @author yellow013
 */
public final class EventHandlerWrapper<E extends ReusableEvent> implements EventHandler<E> {

    private static final Logger LOG = Log4j2LoggerFactory.getLogger(EventHandlerWrapper.class);

    private final String eventbusName;
    private final Processor<E> processor;

    private final Logger log;
    private final boolean crashOnFailure;
    private final EventExceptionCallback exceptionHandler;

    private EventHandlerWrapper(String eventbusName, Processor<E> processor, Builder<E> builder) {
        this.eventbusName = requireNonNull(eventbusName, "eventbusName");
        this.processor = requireNonNull(processor, "processor");
        this.log = requireNonNullElse(builder.logger, LOG);
        this.exceptionHandler = builder.exceptionHandler;
        this.crashOnFailure = builder.crashOnFailure && builder.exceptionHandler == null;
    }

    @Override
    public void onEvent(E event, long sequence, boolean endOfBatch) throws Exception {
        try {
            processor.process(event);
        } catch (Exception e) {
            EventExceptionContext context = EventExceptionContext.event(eventbusName, e, sequence, event);
            log.error("EventHandler process event -> {}, sequence==[{}], endOfBatch==[{}], Processor -> {}, Throw exception -> [{}]",
                context.eventSnapshot(), sequence, endOfBatch, processor.getClass().getSimpleName(),
                e.getMessage(), e);
            if (crashOnFailure)
                throw e;
            notifyCallback(context);
        }
    }

    private void notifyCallback(EventExceptionContext context) {
        if (exceptionHandler == null)
            return;
        try {
            exceptionHandler.onException(context);
        } catch (Throwable throwable) {
            log.error("EventHandler exception callback failed -> stage==[{}], message==[{}]",
                context.stage(), throwable.getMessage(), throwable);
        }
    }

    public static <E extends ReusableEvent> Builder<E> builder() {
        return new Builder<>();
    }

    /**
     * @param <E>
     */
    public static class Builder<E extends ReusableEvent> {

        private String eventbusName = "EventHandlerWrapper";
        private Logger logger;
        private boolean crashOnFailure = false;
        private EventExceptionCallback exceptionHandler;

        public Builder<E> eventbusName(String eventbusName) {
            this.eventbusName = eventbusName;
            return this;
        }

        public Builder<E> logger(Logger logger) {
            this.logger = logger;
            return this;
        }

        public Builder<E> crashOnFailure() {
            this.crashOnFailure = true;
            return this;
        }

        public Builder<E> whenException(EventExceptionCallback exceptionHandler) {
            this.exceptionHandler = exceptionHandler;
            this.crashOnFailure = false;
            return this;
        }

        public EventHandlerWrapper<E> build(Processor<E> processor) {
            return new EventHandlerWrapper<>(eventbusName, processor, this);
        }

    }


}
