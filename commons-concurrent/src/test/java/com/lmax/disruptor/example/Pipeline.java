package com.lmax.disruptor.example;

import com.lmax.disruptor.EventFactory;
import com.lmax.disruptor.EventHandler;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.util.DaemonThreadFactory;

public class Pipeline {

    public static void main(String[] args) {

        Disruptor<PipelineEvent> disruptor = new Disruptor<>(PipelineEvent.FACTORY, 1024,
                DaemonThreadFactory.INSTANCE);

        disruptor.handleEventsWith(new ParallelHandler(0, 3), new ParallelHandler(1, 3), new ParallelHandler(2, 3))
                .then(new JoiningHandler());

        RingBuffer<PipelineEvent> ringBuffer = disruptor.start();

        for (int i = 0; i < 1000; i++) {
            long next = ringBuffer.next();
            try {
                PipelineEvent pipelinerEvent = ringBuffer.get(next);
                pipelinerEvent.input = i;
            } finally {
                ringBuffer.publish(next);
            }
        }
    }

    private record ParallelHandler(
            int ordinal,
            int totalHandlers) implements EventHandler<PipelineEvent> {

        @Override
        public void onEvent(PipelineEvent event, long sequence, boolean endOfBatch) {
            if (sequence % totalHandlers == ordinal) {
                event.result = Long.toString(event.input);
            }
        }
    }

    private static class JoiningHandler implements EventHandler<PipelineEvent> {

        private long lastEvent = -1;

        @Override
        public void onEvent(PipelineEvent event, long sequence, boolean endOfBatch) {
            System.out.println(event);
            if (event.input != lastEvent + 1 || event.result == null) {
                System.out.println("Error: " + event);
            }
            lastEvent = event.input;
            event.result = null;
        }
    }

    private static class PipelineEvent {

        private long input;
        private Object result;

        private static final EventFactory<PipelineEvent> FACTORY = PipelineEvent::new;

        @Override
        public String toString() {
            return "PipelinerEvent{" + "input=" + input + ", result=" + result + '}';
        }
    }
}
