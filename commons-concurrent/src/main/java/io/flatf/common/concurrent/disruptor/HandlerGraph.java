package io.flatf.common.concurrent.disruptor;

import com.lmax.disruptor.EventHandler;
import com.lmax.disruptor.ExceptionHandler;
import com.lmax.disruptor.dsl.Disruptor;
import io.flatf.common.log4j2.Log4j2LoggerFactory;
import org.eclipse.collections.api.list.MutableList;
import org.slf4j.Logger;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import static io.flatf.common.collections.MutableLists.newFastList;
import static java.util.Objects.requireNonNullElse;

/**
 * [事件处理器] GRAPH
 *
 * @param <E> 事件类型
 */
public final class HandlerGraph<E> {

    private static final Logger log = Log4j2LoggerFactory.getLogger(HandlerGraph.class);

    private final ExceptionHandler<E> exceptionHandler;

    private final MutableList<EventHandler<E>[]> handlersList;

    private HandlerGraph(@Nonnull MutableList<EventHandler<E>[]> handlersList,
                         @Nullable ExceptionHandler<E> exceptionHandler) {
        this.handlersList = handlersList;
        this.exceptionHandler = exceptionHandler;
    }

    private static class ExceptionLogger<E> implements ExceptionHandler<E> {

        @Override
        public void handleEventException(Throwable ex, long sequence, E event) {
            log.error("handleEventException -> sequence==[{}], event==[{}], exception message==[{}]",
                    sequence, event, ex.getMessage(), ex);
        }

        @Override
        public void handleOnStartException(Throwable ex) {
            log.error("handleOnStartException -> {}", ex.getMessage(), ex);
        }

        @Override
        public void handleOnShutdownException(Throwable ex) {
            log.error("handleOnShutdownException -> {}", ex.getMessage(), ex);
        }
    }

    public void deploy(Disruptor<E> disruptor) {
        disruptor.setDefaultExceptionHandler(requireNonNullElse(exceptionHandler, new ExceptionLogger<>()));
        if (handlersList.size() > 1) {
            var handlerGroup = disruptor.handleEventsWith(handlersList.getFirst());
            for (int i = 1; i < handlersList.size(); i++)
                handlerGroup.then(handlersList.get(i));
        } else {
            // With set single event
            disruptor.handleEventsWith(handlersList.getFirst());
        }
    }

    @SafeVarargs
    public static <E> HandlerGraphWizard<E> with(@Nonnull EventHandler<E>... handlers) {
        return new HandlerGraphWizard<E>().then(handlers);
    }

    /**
     * [事件处理器] GRAPH构建器
     * @param <E> Event type
     */
    public static class HandlerGraphWizard<E> {

        private ExceptionHandler<E> exceptionHandler;
        private final MutableList<EventHandler<E>[]> eventHandlers = newFastList();

        private HandlerGraphWizard() {
        }

        @SafeVarargs
        public final HandlerGraphWizard<E> then(EventHandler<E>... handlers) {
            this.eventHandlers.add(handlers);
            return this;
        }

        public HandlerGraphWizard<E> whenException(ExceptionHandler<E> exceptionHandler) {
            this.exceptionHandler = exceptionHandler;
            return this;
        }

        public HandlerGraph<E> build() {
            return new HandlerGraph<>(eventHandlers, exceptionHandler);
        }

    }


}
