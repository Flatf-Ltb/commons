package com.lmax.disruptor.support;

import com.lmax.disruptor.EventHandler;

public class DummyEventHandler<T> implements EventHandler<T> {

    public int startCalls = 0;
    public int shutdownCalls = 0;
    public T lastEvent;
    public long lastSequence;

    @Override
    public void onStart() {
        startCalls++;
    }

    @Override
    public void onShutdown() {
        shutdownCalls++;
    }

    @Override
    public void onEvent(T event, long sequence, boolean endOfBatch) throws Exception {
        lastEvent = event;
        lastSequence = sequence;
    }

}
