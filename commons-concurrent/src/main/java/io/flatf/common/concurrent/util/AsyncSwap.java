package io.flatf.common.concurrent.util;

import io.flatf.common.log4j2.Log4j2LoggerFactory;
import io.flatf.common.thread.RunnableComponent;
import org.slf4j.Logger;

import java.util.concurrent.SynchronousQueue;
import java.util.function.Consumer;

import static io.flatf.common.datetime.DateTimeUtil.datetimeOfMillisecond;

public final class AsyncSwap<E> extends RunnableComponent implements Runnable {

    private static final Logger log = Log4j2LoggerFactory.getLogger(AsyncSwap.class);

    private final SynchronousQueue<E> swap = new SynchronousQueue<>();

    private final Consumer<E> consumer;

    public AsyncSwap(Consumer<E> consumer) {
        this("swap-" + datetimeOfMillisecond(), consumer);
    }

    public AsyncSwap(String name, Consumer<E> consumer) {
        super(name);
        this.consumer = consumer;
    }

    public boolean put(E e) {
        log.debug("Swap {} -> put obj : {}", name, e);
        return swap.offer(e);
    }

    @Override
    public void run() {
        try {
            E e = swap.take();
            log.debug("Swap {} -> handle obj : {}", name, e);
            consumer.accept(e);
        } catch (InterruptedException e) {
            log.error("Swap {} -> Throws InterruptedException", name, e);
        }
    }

    @Override
    protected void start0() {
        log.info("Swap {} -> Starting", name);
        // TODO
        new Thread(this, name).start();
    }

    @Override
    protected void stop0() {

    }

}
