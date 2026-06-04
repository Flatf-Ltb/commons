package io.flatf.common.concurrent.disruptor.base;

import com.lmax.disruptor.EventHandler;
import io.flatf.common.functional.Processor;
import io.flatf.common.log4j2.Log4j2LoggerFactory;
import org.slf4j.Logger;

import java.util.function.BiConsumer;

import static java.util.Objects.requireNonNullElse;

/**
 * 事件处理器的包装
 *
 * @author yellow013
 */
public final class EventHandlerWrapper<E> implements EventHandler<E> {

    private static final Logger LOG = Log4j2LoggerFactory.getLogger(EventHandlerWrapper.class);

    private final Processor<E> processor;

    private final Logger log;
    private final boolean crashOnFailure;
    private final BiConsumer<E, Throwable> exceptionHandler;

    private EventHandlerWrapper(Processor<E> processor, Builder<E> builder) {
        this.processor = processor;
        this.log = requireNonNullElse(builder.logger, LOG);
        this.exceptionHandler = builder.exceptionHandler;
        this.crashOnFailure = builder.crashOnFailure && builder.exceptionHandler == null;
    }

    @Override
    public void onEvent(E event, long sequence, boolean endOfBatch) throws Exception {
        try {
            processor.process(event);
        } catch (Exception e) {
            log.error("EventHandler process event -> {}, sequence==[{}], endOfBatch==[{}], Processor -> {}, Throw exception -> [{}]",
                    event, sequence, endOfBatch, processor.getClass().getSimpleName(), e.getMessage(), e);
            if (crashOnFailure)
                throw e;
            if (exceptionHandler != null)
                exceptionHandler.accept(event, e);
        }
    }

    public static <E> Builder<E> builder() {
        return new Builder<>();
    }

    /**
     * @param <E>
     */
    public static class Builder<E> {

        private Logger logger;
        private boolean crashOnFailure = false;
        private BiConsumer<E, Throwable> exceptionHandler;

        public Builder<E> logger(Logger logger) {
            this.logger = logger;
            return this;
        }

        public Builder<E> crashOnFailure() {
            this.crashOnFailure = true;
            return this;
        }

        public Builder<E> whenException(BiConsumer<E, Throwable> exceptionHandler) {
            this.exceptionHandler = exceptionHandler;
            this.crashOnFailure = false;
            return this;
        }

        public EventHandlerWrapper<E> build(Processor<E> processor) {
            return new EventHandlerWrapper<>(processor, this);
        }

    }


}
