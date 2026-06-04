package io.flatf.common.concurrent.disruptor.base;

import java.util.function.BiConsumer;

/**
 * 此接口实现需要负责输入参数到缓冲区事件的转换
 *
 * @param <E> 从RingBuffer中取得的事件
 * @param <I> 输入的参数
 * @author yellow013
 */
@FunctionalInterface
public interface RingEventPublisher<E, I> extends BiConsumer<E, I> {
}
