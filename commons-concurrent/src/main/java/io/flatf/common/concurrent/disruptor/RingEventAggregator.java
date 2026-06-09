package io.flatf.common.concurrent.disruptor;

import com.lmax.disruptor.EventFactory;
import com.lmax.disruptor.EventHandler;
import com.lmax.disruptor.WaitStrategy;
import io.flatf.common.thread.RunnableComponent.StartMode;

/**
 * Ring Event Station
 * 融合了 RingEventbus 和 EventHandler 的抽象类
 *
 * @param <E> Event Type
 */
public abstract class RingEventAggregator<E extends ReusableEvent> implements EventHandler<E> {

    protected final RingEventbus<E> eventbus;

    protected RingEventAggregator(Builder<E> builder, EventFactory<E> factory) {
        var eventbusBuilder = builder.isSingleProducer
            ? RingEventbus.singleProducer(factory)
            : RingEventbus.multiProducer(factory);
        eventbusBuilder.size(builder.size)
            .name(builder.name)
            .waitStrategy(builder.waitStrategy);
        if (builder.startMode != null)
            eventbusBuilder.startMode(builder.startMode);
        if (builder.verifySingleProducer != null)
            eventbusBuilder.verifySingleProducer(builder.verifySingleProducer);
        if (builder.exceptionCallback != null)
            eventbusBuilder.whenException(builder.exceptionCallback);
        this.eventbus = eventbusBuilder.buildWith(this);
    }

    public static <E extends ReusableEvent> Builder<E> singleProducer() {
        return new Builder<>(true);
    }

    public static <E extends ReusableEvent> Builder<E> multiProducer() {
        return new Builder<>(false);
    }

    /**
     * Builder for RingEventAggregator
     * @param <E>
     */
    public static class Builder<E extends ReusableEvent> {

        private final boolean isSingleProducer;
        private String name = "event-aggregator";
        private int size = 128;
        private WaitStrategy waitStrategy = SimpleWaitStrategy.YIELDING.getInstance();
        private StartMode startMode;
        private Boolean verifySingleProducer;
        private EventExceptionCallback exceptionCallback;

        private Builder(boolean isSingleProducer) {
            this.isSingleProducer = isSingleProducer;
        }

        public Builder<E> size(int size) {
            this.size = size;
            return this;
        }

        public Builder<E> name(String name) {
            this.name = name;
            return this;
        }

        public Builder<E> waitStrategy(WaitStrategy waitStrategy) {
            this.waitStrategy = waitStrategy;
            return this;
        }

        public Builder<E> startMode(StartMode startMode) {
            this.startMode = startMode;
            return this;
        }

        public Builder<E> verifySingleProducer() {
            return verifySingleProducer(true);
        }

        public Builder<E> verifySingleProducer(boolean verifySingleProducer) {
            this.verifySingleProducer = verifySingleProducer;
            return this;
        }

        public Builder<E> whenException(EventExceptionCallback exceptionCallback) {
            this.exceptionCallback = exceptionCallback;
            return this;
        }

    }

}
