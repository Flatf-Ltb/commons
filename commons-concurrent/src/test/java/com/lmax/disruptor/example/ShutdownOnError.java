package com.lmax.disruptor.example;

import com.lmax.disruptor.EventFactory;
import com.lmax.disruptor.EventHandler;
import com.lmax.disruptor.ExceptionHandler;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.dsl.Disruptor;

import javax.annotation.Nonnull;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicBoolean;

public class ShutdownOnError {

    private static class Event {

        @SuppressWarnings("unused")
        public long value;

        public static final EventFactory<Event> FACTORY = Event::new;

    }

    private static class DefaultThreadFactory implements ThreadFactory {

        @Override
        public Thread newThread(@Nonnull Runnable r) {
            return new Thread(r);
        }

    }

    private static class Handler implements EventHandler<Event> {

        @Override
        public void onEvent(Event event, long sequence, boolean endOfBatch) throws Exception {
            // do work, if a failure occurs throw exception.
        }

    }

    private record ErrorHandler(
            AtomicBoolean running) implements ExceptionHandler<Event> {

        @Override
        public void handleEventException(Throwable ex, long sequence, Event event) {
            if (exceptionIsFatal(ex)) {
                throw new RuntimeException(ex);
            }
        }

        private boolean exceptionIsFatal(Throwable ex) {
            // Do what is appropriate here.
            return true;
        }

        @Override
        public void handleOnStartException(Throwable ex) {

        }

        @Override
        public void handleOnShutdownException(Throwable ex) {

        }

    }

    public static void main(String[] args) {

        Disruptor<Event> disruptor = new Disruptor<>(Event.FACTORY, 1024, new DefaultThreadFactory());

        AtomicBoolean running = new AtomicBoolean(true);

        ErrorHandler errorHandler = new ErrorHandler(running);

        final Handler handler = new Handler();
        disruptor.handleEventsWith(handler);
        disruptor.handleExceptionsFor(handler).with(errorHandler);

        simplePublish(disruptor, running);

    }

    private static void simplePublish(Disruptor<Event> disruptor, AtomicBoolean running) {
        while (running.get()) {
            disruptor.publishEvent((event, sequence) -> {
                event.value = sequence;
            });
        }
    }

    @SuppressWarnings("unused")
    private static void smarterPublish(Disruptor<Event> disruptor, AtomicBoolean running) {

        final RingBuffer<Event> ringBuffer = disruptor.getRingBuffer();

        boolean publishOk;

        do {
            publishOk = ringBuffer.tryPublishEvent((event, sequence) -> {
                event.value = sequence;
            });
        } while (publishOk && running.get());

    }
}
