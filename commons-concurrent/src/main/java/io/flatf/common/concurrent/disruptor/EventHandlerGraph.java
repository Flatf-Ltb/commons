package io.flatf.common.concurrent.disruptor;

import com.lmax.disruptor.EventHandler;
import com.lmax.disruptor.ExceptionHandler;
import com.lmax.disruptor.dsl.Disruptor;
import io.flatf.common.concurrent.disruptor.EventExceptionHandler.EventExceptionContext;
import io.flatf.common.log4j2.Log4j2LoggerFactory;
import org.eclipse.collections.api.list.MutableList;
import org.slf4j.Logger;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import static io.flatf.common.collections.MutableLists.newFastList;
import static java.util.Objects.requireNonNull;

/**
 * [事件处理器] GRAPH
 *
 * @param <E> 事件类型
 */
public final class EventHandlerGraph<E extends ReusableEvent> {

    private static final Logger log = Log4j2LoggerFactory.getLogger(EventHandlerGraph.class);

    private final MutableList<EventHandler<E>[]> handlersList;

    private final EventExceptionHandler exceptionHandler;

    private EventHandlerGraph(@Nonnull MutableList<EventHandler<E>[]> handlersList,
                              @Nullable EventExceptionHandler exceptionHandler) {
        this.handlersList = handlersList;
        this.exceptionHandler = exceptionHandler;
    }

    /**
     * RingEventbus异常处理代理, 将异常信息通过日志记录并回调给用户定义的异常处理器
     * @param eventbusName 事件总线名称, 用于日志记录和回调上下文
     * @param handler 用户定义的异常处理器, 可为null
     * @param <E>
     */
    private record ExceptionHandleProxy<E extends ReusableEvent>(
        String eventbusName,
        EventExceptionHandler handler
    ) implements ExceptionHandler<E> {

        @Override
        public void handleEventException(Throwable ex, long sequence, E event) {
            EventExceptionContext context = EventExceptionContext.event(eventbusName, ex, sequence, event);
            log.error("{} handleEventException -> sequence==[{}], event==[{}], exception message==[{}]",
                eventbusName, sequence, context.eventSnapshot(), ex.getMessage(), ex);
            notifyCallback(context);
        }

        @Override
        public void handleOnStartException(Throwable ex) {
            log.error("{} handleOnStartException -> {}", eventbusName, ex.getMessage(), ex);
            notifyCallback(EventExceptionContext.start(eventbusName, ex));
        }

        @Override
        public void handleOnShutdownException(Throwable ex) {
            log.error("{} handleOnShutdownException -> {}", eventbusName, ex.getMessage(), ex);
            notifyCallback(EventExceptionContext.shutdown(eventbusName, ex));
        }

        private void notifyCallback(EventExceptionContext context) {
            if (handler == null)
                return;
            try {
                handler.onException(context);
            } catch (Throwable throwable) {
                log.error("RingEventbus exception callback failed -> eventbus==[{}], stage==[{}], message==[{}]",
                    eventbusName, context.stage(), throwable.getMessage(), throwable);
            }
        }

    }

    public void deploy(Disruptor<E> disruptor) {
        deploy(disruptor, "Anonymous");
    }

    public void deploy(Disruptor<E> disruptor, String eventbusName) {
        disruptor.setDefaultExceptionHandler(new ExceptionHandleProxy<>(eventbusName, exceptionHandler));
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
    public static <E extends ReusableEvent> Builder<E> with(@Nonnull EventHandler<E>... handlers) {
        return new Builder<E>().then(handlers);
    }

    private static <E extends ReusableEvent> EventHandler<E>[] requireHandlers(EventHandler<E>[] handlers) {
        if (handlers == null || handlers.length == 0)
            throw new IllegalArgumentException("handlers must not be empty");
        for (int i = 0; i < handlers.length; i++)
            requireNonNull(handlers[i], "handlers[" + i + "]");
        return handlers;
    }

    /**
     * [事件处理器] GRAPH构建器
     * @param <E> Event type
     */
    public static class Builder<E extends ReusableEvent> {

        private EventExceptionHandler exceptionHandler;
        private final MutableList<EventHandler<E>[]> eventHandlers = newFastList();

        private Builder() {
        }

        @SafeVarargs
        public final Builder<E> then(EventHandler<E>... handlers) {
            this.eventHandlers.add(requireHandlers(handlers));
            return this;
        }

        public Builder<E> whenException(EventExceptionHandler exceptionHandler) {
            this.exceptionHandler = exceptionHandler;
            return this;
        }

        public EventHandlerGraph<E> build() {
            if (eventHandlers.isEmpty())
                throw new IllegalArgumentException("handler graph must contain at least one handler stage");
            return new EventHandlerGraph<>(eventHandlers, exceptionHandler);
        }

    }

}
