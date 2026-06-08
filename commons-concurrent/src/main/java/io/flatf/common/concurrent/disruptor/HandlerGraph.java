package io.flatf.common.concurrent.disruptor;

import com.lmax.disruptor.EventHandler;
import com.lmax.disruptor.ExceptionHandler;
import com.lmax.disruptor.dsl.Disruptor;
import io.flatf.common.concurrent.disruptor.EventExceptionCallback.EventExceptionContext;
import io.flatf.common.log4j2.Log4j2LoggerFactory;
import org.eclipse.collections.api.list.MutableList;
import org.slf4j.Logger;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import static io.flatf.common.collections.MutableLists.newFastList;

/**
 * [事件处理器] GRAPH
 *
 * @param <E> 事件类型
 */
public final class HandlerGraph<E extends ReusableEvent> {

    private static final Logger log = Log4j2LoggerFactory.getLogger(HandlerGraph.class);

    private final EventExceptionCallback<E> exceptionCallback;

    private final MutableList<EventHandler<E>[]> handlersList;

    private HandlerGraph(@Nonnull MutableList<EventHandler<E>[]> handlersList,
                         @Nullable EventExceptionCallback<E> exceptionCallback) {
        this.handlersList = handlersList;
        this.exceptionCallback = exceptionCallback;
    }

    /**
     * RingEventbus异常处理代理, 将异常信息通过日志记录并回调给用户定义的异常处理器
     * @param eventbusName 事件总线名称, 用于日志记录和回调上下文
     * @param callback 用户定义的异常处理器, 可为null
     * @param <E>
     */
    private record ExceptionHandleProxy<E extends ReusableEvent>(
        String eventbusName,
        EventExceptionCallback<E> callback) implements ExceptionHandler<E> {

        @Override
        public void handleEventException(Throwable ex, long sequence, E event) {
            log.error("{} handleEventException -> sequence==[{}], event==[{}], exception message==[{}]",
                eventbusName, sequence, event, ex.getMessage(), ex);
            notifyCallback(EventExceptionContext.event(eventbusName, ex, sequence, event));
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

        private void notifyCallback(EventExceptionContext<E> context) {
            if (callback == null)
                return;
            try {
                callback.onException(context);
            } catch (Throwable throwable) {
                log.error("RingEventbus exception callback failed -> eventbus==[{}], stage==[{}], message==[{}]",
                    eventbusName, context.getStage(), throwable.getMessage(), throwable);
            }
        }

    }

    public void deploy(Disruptor<E> disruptor) {
        deploy(disruptor, "Anonymous");
    }

    public void deploy(Disruptor<E> disruptor, String eventbusName) {
        disruptor.setDefaultExceptionHandler(new ExceptionHandleProxy<>(eventbusName, exceptionCallback));
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
    public static <E extends ReusableEvent> HandlerGraphWizard<E> with(@Nonnull EventHandler<E>... handlers) {
        return new HandlerGraphWizard<E>().then(handlers);
    }

    /**
     * [事件处理器] GRAPH构建器
     * @param <E> Event type
     */
    public static class HandlerGraphWizard<E extends ReusableEvent> {

        private EventExceptionCallback<E> exceptionCallback;
        private final MutableList<EventHandler<E>[]> eventHandlers = newFastList();

        private HandlerGraphWizard() {
        }

        @SafeVarargs
        public final HandlerGraphWizard<E> then(EventHandler<E>... handlers) {
            this.eventHandlers.add(handlers);
            return this;
        }

        public HandlerGraphWizard<E> onException(EventExceptionCallback<E> exceptionCallback) {
            this.exceptionCallback = exceptionCallback;
            return this;
        }

        public HandlerGraph<E> build() {
            return new HandlerGraph<>(eventHandlers, exceptionCallback);
        }

    }

    HandlerGraph<E> withExceptionCallback(EventExceptionCallback<E> callback) {
        return new HandlerGraph<>(handlersList, callback);
    }


}
