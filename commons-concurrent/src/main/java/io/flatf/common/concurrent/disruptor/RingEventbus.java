package io.flatf.common.concurrent.disruptor;

import com.lmax.disruptor.EventFactory;
import com.lmax.disruptor.EventHandler;
import com.lmax.disruptor.EventTranslator;
import com.lmax.disruptor.EventTranslatorOneArg;
import com.lmax.disruptor.EventTranslatorThreeArg;
import com.lmax.disruptor.EventTranslatorTwoArg;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.WaitStrategy;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;
import io.flatf.common.concurrent.disruptor.EventPublisher.EventPublisherArg1;
import io.flatf.common.concurrent.disruptor.EventPublisher.EventPublisherArg2;
import io.flatf.common.concurrent.disruptor.EventPublisher.EventPublisherArg3;
import io.flatf.common.thread.RunnableComponent;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import org.slf4j.Logger;

import java.time.LocalDateTime;
import java.util.concurrent.atomic.AtomicReference;

import static com.lmax.disruptor.dsl.ProducerType.MULTI;
import static com.lmax.disruptor.dsl.ProducerType.SINGLE;
import static io.flatf.common.concurrent.disruptor.ReflectionEventFactory.newReflectionFactory;
import static io.flatf.common.concurrent.disruptor.SimpleWaitStrategy.YIELDING;
import static io.flatf.common.datetime.pattern.impl.DateTimePattern.YYMMDD_L_HHMMSSSSS;
import static io.flatf.common.lang.Validator.nonNull;
import static io.flatf.common.lang.Validator.requiredLength;
import static io.flatf.common.log4j2.Log4j2LoggerFactory.getLogger;
import static io.flatf.common.thread.ThreadFactoryImpl.ofPlatform;
import static io.flatf.common.thread.ThreadPriority.MAX;
import static io.flatf.common.util.BitOperator.minPow2;
import static io.flatf.common.util.StringSupport.requireNonEmptyElse;

/**
 * @param <E>
 * @author yellow013
 * <p>
 * 多写和单写模式的 Disruptor，提供了 publish 方法和 构建EventPublisher 的工厂方法。
 * 提供了多种参数的重载，支持不同数量的参数传递给事件处理器。
 */
public final class RingEventbus<E extends ReusableEvent> extends RunnableComponent {

    private static final Logger log = getLogger(RingEventbus.class);

    /**
     * 系统属性键: 是否对 SINGLE 生产者模式做"发布线程亲和"断言(检测单生产者总线被多线程并发 publish,
     * 命中即抛 {@link IllegalStateException})。默认 false; 仅在 {@code ProducerType.SINGLE} 下生效, MULTI 为 no-op。
     */
    public static final String ASSERT_SINGLE_PRODUCER_PROPERTY = "flatf.disruptor.assert-single-producer-thread";

    /**
     * 旧键, 仅为向后兼容保留; 请改用 {@link #ASSERT_SINGLE_PRODUCER_PROPERTY}。
     *
     * @deprecated 已更名为 {@link #ASSERT_SINGLE_PRODUCER_PROPERTY}
     */
    @Deprecated
    public static final String VERIFY_SINGLE_PRODUCER_PROPERTY = "flatf.disruptor.verifySingleProducer";

    private final Disruptor<E> disruptor;

    private final RingBuffer<E> buffer;

    private final AtomicReference<Thread> publisherThread;

    private RingEventbus(@Nullable String name, int size,
                         @Nonnull StartMode mode, boolean assertSinglePublisher,
                         @Nonnull ProducerType type,
                         @Nonnull EventFactory<E> factory,
                         @Nonnull WaitStrategy strategy,
                         @Nonnull EventHandlerGraph<E> graph) {
        super(requireNonEmptyElse(name, "eventbus-[" + YYMMDD_L_HHMMSSSSS.fmt(LocalDateTime.now()) + "]"));
        this.publisherThread = type == SINGLE && assertSinglePublisher ? new AtomicReference<>() : null;
        this.disruptor = new Disruptor<>(
            // EventFactory, 队列容量
            factory, adjustSize(size),
            // ThreadFactory
            ofPlatform(this.name + "-worker").priority(MAX).build(),
            // 生产者类型, Waiting策略
            type, strategy);
        graph.deploy(this.disruptor, this.name);
        this.buffer = this.disruptor.getRingBuffer();
        startWith(mode);
    }

    private void assertPublisherThread() {
        if (publisherThread == null)
            return;
        Thread current = Thread.currentThread();
        Thread owner = publisherThread.get();
        if (owner == current)
            return;
        if (owner == null && publisherThread.compareAndSet(null, current))
            return;
        owner = publisherThread.get();
        String ownerName = owner == null ? "unknown" : owner.getName();
        throw new IllegalStateException(
            "RingEventbus [" + name + "] is configured as singleProducer, but publish was called from multiple threads."
            + " owner=[" + ownerName + "], current=[" + current.getName() + "]");
    }

    /**
     * 调整队列容量, 最小16, 最大65536, 其他输入参数自动调整为最接近的2次幂
     *
     * @param size buffer size
     * @return int
     */
    private int adjustSize(int size) {
        if (size < 16)
            return 16;
        if (size > 65536)
            return 65536;
        else
            return minPow2(size);
    }

    @Override
    protected void start0() {
        disruptor.start();
        log.info("Disruptor::start() call succeed, {} is start", name);
    }

    @Override
    protected void stop0() {
        disruptor.shutdown();
        log.info("Disruptor::shutdown() call succeed, {} is shutdown", name);
    }

    /**
     * @param eventType EventFactory<E>
     * @param <E>       Class type
     * @return Wizard<E>
     */
    public static <E extends ReusableEvent> Builder<E> multiProducer(Class<E> eventType) {
        return new Builder<>(MULTI, newReflectionFactory(eventType, log));
    }

    /**
     * @param factory EventFactory<E>
     * @param <E>     Class type
     * @return Wizard<E>
     */
    public static <E extends ReusableEvent> Builder<E> multiProducer(EventFactory<E> factory) {
        return new Builder<>(MULTI, factory);
    }

    /**
     * @param eventType EventFactory<E>
     * @param <E>       Class type
     * @return Wizard<E>
     */
    public static <E extends ReusableEvent> Builder<E> singleProducer(Class<E> eventType) {
        return new Builder<>(SINGLE, newReflectionFactory(eventType, log));
    }

    /**
     * @param factory EventFactory<E>
     * @param <E>     Class type
     * @return Wizard<E>
     */
    public static <E extends ReusableEvent> Builder<E> singleProducer(EventFactory<E> factory) {
        return new Builder<>(SINGLE, factory);
    }

    /**
     * @param translator EventTranslatorOneArg<E, A>
     * @param <A>        another object type
     * @return the new EventPublisher<E, A> object
     */
    public <A> EventPublisherArg1<E, A> newPublisher(
        @Nonnull EventTranslatorOneArg<E, A> translator) {
        return EventPublisher.newPublisher(buffer, translator, this::assertPublisherThread);
    }

    /**
     * @param translator EventTranslatorTwoArg<E, A0, A1>
     * @param <A0>       another 0 object type
     * @param <A1>       another 1 object type
     * @return EventPublisherArg2<E, A0, A1>
     */
    public <A0, A1> EventPublisherArg2<E, A0, A1> newPublisher(
        @Nonnull EventTranslatorTwoArg<E, A0, A1> translator) {
        return EventPublisher.newPublisher(buffer, translator, this::assertPublisherThread);
    }

    /**
     * @param translator EventTranslatorThreeArg<E, A0, A1, A2>
     * @param <A0>       another 0 object type
     * @param <A1>       another 1 object type
     * @param <A2>       another 2 object type
     * @return EventPublisherArg3<E, A0, A1, A2>
     * @throws IllegalStateException ise
     */
    public <A0, A1, A2> EventPublisherArg3<E, A0, A1, A2> newPublisher(
        @Nonnull EventTranslatorThreeArg<E, A0, A1, A2> translator) throws IllegalStateException {
        return EventPublisher.newPublisher(this.buffer, translator, this::assertPublisherThread);
    }


    /**
     * @param translator EventTranslator<E>
     */
    public void publish(EventTranslator<E> translator) {
        assertPublisherThread();
        buffer.publishEvent((event, sequence) -> {
            event.clear();
            translator.translateTo(event, sequence);
        });
    }

    /**
     * @param translator EventTranslatorOneArg<E, A>
     * @param arg        A
     * @param <A>        Arg type
     */
    public <A> void publish(EventTranslatorOneArg<E, A> translator, A arg) {
        assertPublisherThread();
        buffer.publishEvent((event, sequence, value) -> {
            event.clear();
            translator.translateTo(event, sequence, value);
        }, arg);
    }

    /**
     * @param translator EventTranslatorTwoArg<E, A0, A1>
     * @param arg0       A0
     * @param arg1       A1
     * @param <A0>       A0 Type
     * @param <A1>       A1 Type
     */
    public <A0, A1> void publish(EventTranslatorTwoArg<E, A0, A1> translator, A0 arg0, A1 arg1) {
        assertPublisherThread();
        buffer.publishEvent((event, sequence, value0, value1) -> {
            event.clear();
            translator.translateTo(event, sequence, value0, value1);
        }, arg0, arg1);
    }

    /**
     * @param translator EventTranslatorThreeArg<E, A0, A1, A2>
     * @param arg0       A0
     * @param arg1       A1
     * @param arg2       A2
     * @param <A0>       A0 Type
     * @param <A1>       A1 Type
     * @param <A2>       A2 Type
     */
    public <A0, A1, A2> void publish(EventTranslatorThreeArg<E, A0, A1, A2> translator, A0 arg0, A1 arg1, A2 arg2) {
        assertPublisherThread();
        buffer.publishEvent((event, sequence, value0, value1, value2) -> {
            event.clear();
            translator.translateTo(event, sequence, value0, value1, value2);
        }, arg0, arg1, arg2);
    }

    /**
     * Builder for RingEventbus
     * @param <E>
     */
    public static class Builder<E extends ReusableEvent> {

        protected final EventFactory<E> factory;
        protected final ProducerType type;

        protected String name;
        protected int size = 256;
        protected StartMode startMode = StartMode.auto();
        protected WaitStrategy strategy = YIELDING.getInstance();
        @SuppressWarnings("deprecation")
        protected boolean assertSingleProducer = Boolean.getBoolean(ASSERT_SINGLE_PRODUCER_PROPERTY)
            || Boolean.getBoolean(VERIFY_SINGLE_PRODUCER_PROPERTY);
        protected EventExceptionCallback exceptionCallback;

        private Builder(ProducerType type, EventFactory<E> factory) {
            this.type = type;
            this.factory = factory;
        }

        public Builder<E> name(String name) {
            this.name = name;
            return this;
        }

        public Builder<E> size(int size) {
            this.size = size;
            return this;
        }

        public Builder<E> waitStrategy(SimpleWaitStrategy strategy) {
            return waitStrategy(strategy.getInstance());
        }

        public Builder<E> waitStrategy(WaitStrategy strategy) {
            this.strategy = strategy;
            return this;
        }

        public Builder<E> startMode(StartMode startMode) {
            this.startMode = startMode;
            return this;
        }

        public Builder<E> assertSingleProducer() {
            return assertSingleProducer(true);
        }

        public Builder<E> assertSingleProducer(boolean assertSingleProducer) {
            this.assertSingleProducer = assertSingleProducer;
            return this;
        }

        /** @deprecated 改用 {@link #assertSingleProducer()} */
        @Deprecated
        public Builder<E> verifySingleProducer() {
            return assertSingleProducer(true);
        }

        /** @deprecated 改用 {@link #assertSingleProducer(boolean)} */
        @Deprecated
        public Builder<E> verifySingleProducer(boolean verifySingleProducer) {
            return assertSingleProducer(verifySingleProducer);
        }

        public Builder<E> whenException(EventExceptionCallback exceptionCallback) {
            this.exceptionCallback = exceptionCallback;
            return this;
        }

        @SafeVarargs
        public final RingEventbus<E> buildBroadcastWith(EventHandler<E>... handlers) {
            requiredLength(handlers, 1, "handlers");
            return new RingEventbus<>(name, size, startMode, assertSingleProducer, type, factory, strategy,
                EventHandlerGraph.with(handlers)
                    .whenException(exceptionCallback)
                    .build());
        }

        @SafeVarargs
        public final RingEventbus<E> buildPipelineWith(EventHandler<E>... handlers) {
            requiredLength(handlers, 1, "handlers");
            var wizard = EventHandlerGraph.with(handlers[0]);
            for (int i = 1; i < handlers.length; i++)
                wizard.then(handlers[i]);
            return new RingEventbus<>(name, size, startMode, assertSingleProducer, type, factory, strategy,
                wizard.whenException(exceptionCallback)
                    .build());
        }

        public RingEventbus<E> buildWith(EventHandler<E> handler) {
            nonNull(handler, "handler");
            return new RingEventbus<>(name, size, startMode, assertSingleProducer, type, factory, strategy,
                EventHandlerGraph.with(handler)
                    .whenException(exceptionCallback)
                    .build());
        }

        public RingEventbus<E> buildWith(EventHandlerGraph<E> graph) {
            nonNull(graph, "graph");
            return new RingEventbus<>(name, size, startMode, assertSingleProducer,
                type, factory, strategy, graph);
        }

    }


}
