package io.flatf.common.concurrent.queue;

import io.flatf.common.collections.queue.Queue;
import io.flatf.common.functional.Processor;
import io.flatf.common.lang.Validator;
import io.flatf.common.thread.RunnableComponent;

/**
 * @param <E> Single Consumer Queue base implements
 * @author yellow013
 */
public abstract class SingleConsumerQueue<E> extends RunnableComponent implements Queue<E> {

    /**
     * Processor Function
     */
    protected final Processor<E> processor;

    protected SingleConsumerQueue(String name, Processor<E> processor) {
        super(name);
        Validator.nonNull(processor, "processor");
        this.processor = processor;
    }

    @Override
    public String getQueueName() {
        return name;
    }

}
