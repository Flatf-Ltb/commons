package io.flatf.common.concurrent.disruptor;

import com.lmax.disruptor.EventTranslatorOneArg;
import com.lmax.disruptor.EventTranslatorThreeArg;
import com.lmax.disruptor.EventTranslatorTwoArg;
import com.lmax.disruptor.RingBuffer;
import jakarta.annotation.Nonnull;

import static java.util.Objects.requireNonNull;

public final class EventPublisher {

    private static final Runnable NOOP = () -> {
    };

    private EventPublisher() {
    }

    /**
     * @param translator EventTranslatorOneArg<E, A>
     * @param <A>        another object type
     * @return the new EventPublisher<E, A> object
     */
    public static <E extends ReusableEvent, A> EventPublisherArg1<E, A> newPublisher(
        @Nonnull RingBuffer<E> buffer, @Nonnull EventTranslatorOneArg<E, A> translator) {
        return newPublisher(buffer, translator, NOOP);
    }

    public static <E extends ReusableEvent, A> EventPublisherArg1<E, A> newPublisher(
        @Nonnull RingBuffer<E> buffer, @Nonnull EventTranslatorOneArg<E, A> translator,
        @Nonnull Runnable beforePublish) {
        return new EventPublisherArg1<>(buffer, translator, beforePublish);
    }

    /**
     * @param translator EventTranslatorTwoArg<E, A0, A1>
     * @param <A0>       another 0 object type
     * @param <A1>       another 1 object type
     * @return EventPublisherArg2<E, A0, A1>
     * @throws IllegalStateException ise
     */
    public static <E extends ReusableEvent, A0, A1> EventPublisherArg2<E, A0, A1> newPublisher(
        @Nonnull RingBuffer<E> buffer, @Nonnull EventTranslatorTwoArg<E, A0, A1> translator) {
        return newPublisher(buffer, translator, NOOP);
    }

    public static <E extends ReusableEvent, A0, A1> EventPublisherArg2<E, A0, A1> newPublisher(
        @Nonnull RingBuffer<E> buffer, @Nonnull EventTranslatorTwoArg<E, A0, A1> translator,
        @Nonnull Runnable beforePublish) {
        return new EventPublisherArg2<>(buffer, translator, beforePublish);
    }

    /**
     * @param translator EventTranslatorThreeArg<E, A0, A1, A2>
     * @param <A0>       another 0 object type
     * @param <A1>       another 1 object type
     * @param <A2>       another 2 object type
     * @return EventPublisherArg3<E, A0, A1, A2>
     */
    public static <E extends ReusableEvent, A0, A1, A2> EventPublisherArg3<E, A0, A1, A2> newPublisher(
        @Nonnull RingBuffer<E> buffer, @Nonnull EventTranslatorThreeArg<E, A0, A1, A2> translator) {
        return newPublisher(buffer, translator, NOOP);
    }

    public static <E extends ReusableEvent, A0, A1, A2> EventPublisherArg3<E, A0, A1, A2> newPublisher(
        @Nonnull RingBuffer<E> buffer, @Nonnull EventTranslatorThreeArg<E, A0, A1, A2> translator,
        @Nonnull Runnable beforePublish) {
        return new EventPublisherArg3<>(buffer, translator, beforePublish);
    }


    /**
     * 事件发布者, 用于将输入<A>类型参数转换为<E>类型事件, 并调用RingBuffer对象的publishEvent函数
     * <p>
     * 并负责传递EventTranslator实现
     *
     * @param <E> Event type
     * @param <A> Arg
     * @author yellow013
     */
    public static final class EventPublisherArg1<E extends ReusableEvent, A> {

        private final RingBuffer<E> buffer;

        private final EventTranslatorOneArg<E, A> translator;
        private final Runnable beforePublish;

        private EventPublisherArg1(@Nonnull RingBuffer<E> buffer,
                                   @Nonnull EventTranslatorOneArg<E, A> translator,
                                   @Nonnull Runnable beforePublish) {
            this.buffer = requireNonNull(buffer, "buffer");
            this.translator = requireNonNull(translator, "translator");
            this.beforePublish = requireNonNull(beforePublish, "beforePublish");
        }

        public void publish(A arg) {
            beforePublish.run();
            buffer.publishEvent((event, sequence, value) -> {
                event.clear();
                translator.translateTo(event, sequence, value);
            }, arg);
        }

    }

    /**
     * 事件发布者, 用于将输入<A0>, <A1>类型参数转换为<E>类型事件, 并调用RingBuffer对象的publishEvent函数
     * <p>
     * 并负责传递EventTranslator实现
     *
     * @param <E> Event type
     * @param <A0> Arg0
     * @param <A1> Arg1
     * @author yellow013
     */
    public static final class EventPublisherArg2<E extends ReusableEvent, A0, A1> {

        private final RingBuffer<E> buffer;

        private final EventTranslatorTwoArg<E, A0, A1> translator;
        private final Runnable beforePublish;

        private EventPublisherArg2(@Nonnull RingBuffer<E> buffer,
                                   @Nonnull EventTranslatorTwoArg<E, A0, A1> translator,
                                   @Nonnull Runnable beforePublish) {
            this.buffer = requireNonNull(buffer, "buffer");
            this.translator = requireNonNull(translator, "translator");
            this.beforePublish = requireNonNull(beforePublish, "beforePublish");
        }

        public void publish(A0 arg0, A1 arg1) {
            beforePublish.run();
            buffer.publishEvent((event, sequence, value0, value1) -> {
                event.clear();
                translator.translateTo(event, sequence, value0, value1);
            }, arg0, arg1);
        }

    }

    /**
     * 事件发布者, 用于将输入<A0>, <A1>, <A1>类型参数转换为<E>类型事件, 并调用RingBuffer对象的publishEvent函数
     * <p>
     * 并负责传递EventTranslator实现
     *
     * @param <E> Event type
     * @param <A0> Arg0
     * @param <A1> Arg1
     * @param <A2> Arg2
     * @author yellow013
     */
    public static final class EventPublisherArg3<E extends ReusableEvent, A0, A1, A2> {

        private final RingBuffer<E> buffer;

        private final EventTranslatorThreeArg<E, A0, A1, A2> translator;
        private final Runnable beforePublish;

        private EventPublisherArg3(@Nonnull RingBuffer<E> buffer,
                                   @Nonnull EventTranslatorThreeArg<E, A0, A1, A2> translator,
                                   @Nonnull Runnable beforePublish) {
            this.buffer = requireNonNull(buffer, "buffer");
            this.translator = requireNonNull(translator, "translator");
            this.beforePublish = requireNonNull(beforePublish, "beforePublish");
        }

        public void publish(A0 arg0, A1 arg1, A2 arg2) {
            beforePublish.run();
            buffer.publishEvent((event, sequence, value0, value1, value2) -> {
                event.clear();
                translator.translateTo(event, sequence, value0, value1, value2);
            }, arg0, arg1, arg2);
        }

    }

}
